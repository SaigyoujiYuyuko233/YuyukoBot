package network.meikai.mc.uuzselfplaybot.network.Events;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import network.meikai.mc.uuzselfplaybot.CliConfig;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class ServerChatPacketHandler {

    public void handle(PacketReceivedEvent evt, ServerChatPacket packet) {
        Logger logger = LogManager.getLogger("ServerChat");
        String message = packet.getMessage().getFullText();

        logger.info(message);

        String sender = "";
        String humanSenderPattern = "^(<)[A-Za-z0-9\\-_.]+(>\\s)[A-Za-z0-9\\-_.\\s!?]+";

        // 判断 message sender
        if ( Pattern.matches(humanSenderPattern, message) ) {
            sender = message.split(" ")[0];
            sender = sender.replace("<", "");
            sender = sender.replace(">", "");
        } else {
            sender = "Server";
        }

        // 获取 去除玩家名的消息
        String rawMessage = message.replaceAll("^(<)[A-Za-z0-9\\-_.]+(>\\s)", "");

        /*
         * 发送指令
         */
        if (Pattern.matches("^(" + GlobalVars.BOTNAME + ")(\\s)(exec)(\\s)[A-Za-z0-9\\-/_.\\s]+", rawMessage) ||
            Pattern.matches("^(\\[)[A-Za-z0-9\\-_.]+(\\s\\->\\sme\\]\\sexec\\s)[A-Za-z0-9\\-/_.\\s\\u4E00-\\u9FA5]+", rawMessage)) {

            // 获取指令
            String command = rawMessage.replaceAll("^(\\[)[A-Za-z0-9\\-_.]+(\\s\\->\\sme\\]\\sexec\\s)", "");

            // 执行
            evt.getSession().send(new ClientChatPacket(command));

            // log
            EventHandler.EVENT_LOGGER.info("Bot Command Execute: " + command + " by " + sender);
        }

        /*
         * 移动指令
         */
        if (Pattern.matches("^(move)(\\s)[\\-0-9]+(\\s)[\\-0-9]+(\\s)[\\-0-9]+", rawMessage)) {

            // 获取具体坐标                               这里有一个空格
            String[] movePositions = rawMessage.replace("move ", "").split(" ");
            Double fX = Double.valueOf(movePositions[0]); // 从 2 开始 | 忽略 move
            Double fY = Double.valueOf(movePositions[1]);
            Double fZ = Double.valueOf(movePositions[2]);

            GlobalVars.BotMoving.moveToPosition(
                fX,
                fY,
                fZ
            );

            // log
            EventHandler.EVENT_LOGGER.info("Move to (" +
                fX + "," +
                fY + "," +
                fZ + ")" +
                " By " + sender
            );
        }

    }

}
