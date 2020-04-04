package network.meikai.mc.uuzselfplaybot.network;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerKeepAlivePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import network.meikai.mc.uuzselfplaybot.network.Events.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventHandler extends SessionAdapter {
    public static final ServerJoinGamePacketHandler serverJoinGamePacketHandler = new ServerJoinGamePacketHandler();
    public static final ServerChatPacketHandler serverChatPacketHandler = new ServerChatPacketHandler();
    public static final PlayerRespawnEventHandler serverRespawnPacketHandler = new PlayerRespawnEventHandler();
    public static final ServerDisconnectPacketHandler serverDisconnectPacketHandler = new ServerDisconnectPacketHandler();
    public static final ServerPlayerPositionRotationPacketHandler serverPlayerPositionRotationPacketHandler = new ServerPlayerPositionRotationPacketHandler();

    public static final Logger EVENT_LOGGER = LogManager.getLogger("NetworkEventHandler");

    @Override
    public void packetReceived(PacketReceivedEvent evt) {

        // ServerJoinGamePacket - Handler
        if ( evt.getPacket() instanceof ServerJoinGamePacket ) {
            serverJoinGamePacketHandler.handle(evt, (ServerJoinGamePacket) evt.getPacket());
        }

        // ServerPlayerPositionRotationPacket - Handle
        if ( evt.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            serverPlayerPositionRotationPacketHandler.handle(evt, (ServerPlayerPositionRotationPacket) evt.getPacket());
        }

        // ServerChatPacketHandler - Handle
        if ( evt.getPacket() instanceof ServerChatPacket) {
            serverChatPacketHandler.handle(evt, (ServerChatPacket) evt.getPacket());
        }

        // ServerDisconnectPacketHandler - Handle
        if ( evt.getPacket() instanceof ServerDisconnectPacket) {
            serverDisconnectPacketHandler.handle(evt, (ServerDisconnectPacket) evt.getPacket());
        }

        // ServerKeepAlivePacket - Handle
        if ( evt.getPacket() instanceof ServerKeepAlivePacket) {
            EVENT_LOGGER.debug("KeepAlive" + evt.getPacket());
        }


        // ServerRespawnPacket - Handle
        if ( evt.getPacket() instanceof ServerPlayerHealthPacket) {
            ServerPlayerHealthPacket packet = evt.getPacket();

            // Player respawn
            if ( packet.getHealth() <= 0 ) {
                serverRespawnPacketHandler.handle(evt, packet);
            }

        }


    }

}
