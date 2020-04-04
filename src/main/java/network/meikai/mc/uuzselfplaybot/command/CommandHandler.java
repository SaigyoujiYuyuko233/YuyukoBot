package network.meikai.mc.uuzselfplaybot.command;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            // 如果为空 跳过此次循环
            if ( command.equals("") ) {
                continue;
            }

            /*
             * 机器人命令
             */

            if ( command.length() >= 3 && command.substring(0, 3).equals("///") ) {

                // 机器人命令
                String botCommand = command.replace("///", "");

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
