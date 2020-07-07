package network.meikai.mc.uuzselfplaybot.network.Events;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import network.meikai.mc.uuzselfplaybot.GlobalVars;

public class ServerDisconnectPacketHandler {

    public void handle(PacketReceivedEvent evt, ServerDisconnectPacket packet) {
        GlobalVars.MAIN_LOGGER.info("Server Disconnect: " + packet.getReason().getFullText());
        GlobalVars.MAIN_LOGGER.info("Shutting down the bot...");
        GlobalVars.MAIN_LOGGER.info("Bye~");

        System.exit(0);
    }

}
