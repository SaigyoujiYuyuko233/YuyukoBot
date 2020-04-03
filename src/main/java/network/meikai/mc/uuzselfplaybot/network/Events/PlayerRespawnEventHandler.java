package network.meikai.mc.uuzselfplaybot.network.Events;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;

public class PlayerRespawnEventHandler {

    public void handle(PacketReceivedEvent evt, ServerPlayerHealthPacket packet) {
        EventHandler.EVENT_LOGGER.info("Bot died! Respawn...");
        evt.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
    }

}
