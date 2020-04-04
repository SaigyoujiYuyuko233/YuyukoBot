package network.meikai.mc.uuzselfplaybot.command;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;

public class CommandHandler implements Runnable{

    public static Terminal terminal;
    public static LineReader lineReader;

    @Override
    public void run() {

        terminal = TerminalConsoleAppender.getTerminal();

        if (terminal != null) {
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } else {
            lineReader = LineReaderBuilder.builder().terminal((Terminal) System.in).build();
        }

        lineReader.setOpt(LineReader.Option.DISABLE_EVENT_EXPANSION);
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        TerminalConsoleAppender.setReader(lineReader);

        while (true) {
            String command;
            try {
                command = lineReader.readLine("聊天/命令/Bot命令 >> ");
            } catch (EndOfFileException var9) {
                continue;
            }

            // 如果为空 跳过此次循环
            if ( command.equals("") ) {
                continue;
            }

            /*
             * 机器人命令
             */

            if ( command.length() >= 3 && command.substring(0, 3).equals("///") ) {
                GlobalVars.MAIN_LOGGER.debug("BotCommand: true");

                // 机器人命令
                String botCommand = command.replace("///", "");
                GlobalVars.MAIN_LOGGER.debug("BotCommand: " + botCommand);

                /*
                 * 开始判断
                 */

                // 退出
                if ( botCommand.matches("^(quit)") ) {
                    GlobalVars.MAIN_LOGGER.info("Bye~");
                    GlobalVars.CLIENT.getSession().disconnect("qaq");
                    System.exit(0);
                }

                // 查询在线人数
                if ( botCommand.matches("^(list)") ) {
                    GlobalVars.MAIN_LOGGER.debug("Executor: list");

                    MinecraftProtocol protocol = new MinecraftProtocol(SubProtocol.STATUS);
                    final Client client = new Client(GlobalVars.HOST, GlobalVars.PORT, protocol, new TcpSessionFactory());

                    client.getSession().setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, new ServerInfoHandler() {
                        @Override
                        public void handle(Session session, ServerStatusInfo info) {
                            String onlinePlayers = "";
                            for ( int i = 0; i < info.getPlayerInfo().getPlayers().length; i++ ) {
                                if ( i == 0 ) {
                                    onlinePlayers = "<" + info.getPlayerInfo().getPlayers()[i].getName() + ">";
                                } else {
                                    onlinePlayers = onlinePlayers + " <" + info.getPlayerInfo().getPlayers()[i].getName() + ">";
                                }
                            }

                            GlobalVars.MAIN_LOGGER.info("在线人数: " + info.getPlayerInfo().getOnlinePlayers() + " / " + info.getPlayerInfo().getMaxPlayers());
                            GlobalVars.MAIN_LOGGER.info("在线的小可爱: " + onlinePlayers);
                        }
                    });

                    client.getSession().setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, new ServerPingTimeHandler() {
                        @Override
                        public void handle(Session session, long pingTime) {
                            GlobalVars.MAIN_LOGGER.info("服务器延迟: " + pingTime + "ms");
                            client.getSession().disconnect("qaq");
                        }
                    });

                    client.getSession().connect();

                    continue;
                }

            }
            // end of 机器人命令

            /*
             * 玩家发送到服务器的
             */
            GlobalVars.CLIENT.getSession().send(new ClientChatPacket(command));

        }
    }
}
