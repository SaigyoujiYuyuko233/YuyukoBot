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
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class CommandHandler implements Runnable{

    public static Terminal terminal;
    public static LineReader lineReader;

    @Override
    public void run() {
        System.setProperty("jline.internal.Log.debug", "true");

        // init terminal
        try {
           terminal = TerminalBuilder
                   .builder()
                   .dumb(true)
                   .system(true)
                   .name("command")
                   .jansi(true)
                   .build();

           lineReader = LineReaderBuilder.builder().terminal(terminal).option(LineReader.Option.AUTO_FRESH_LINE, true).build();

           new Thread(new Runnable() {
               @Override
               public void run() {
                   while (true) {
                       if (lineReader.isReading()) {
                           lineReader.callWidget(LineReader.REDISPLAY);
                       }
                       try {
                           Thread.sleep(200);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
           }).start();
        } catch (IOException e) { GlobalVars.MAIN_LOGGER.error(e.getMessage()); e.printStackTrace(); }

        while (true) {
            String command = lineReader.readLine("聊天/命令/Bot命令 >> ");
            GlobalVars.MAIN_LOGGER.debug("Input: " + command);

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
                if ( botCommand.matches("^(quit)[\\s\\S]+") ) {
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
                            System.out.print("\n");

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
                            System.out.print("\n");

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
