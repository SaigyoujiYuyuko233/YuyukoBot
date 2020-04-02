package network.meikai.mc.uuzselfplaybot.network;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventHandler extends SessionAdapter {
    public static final Logger EVENT_LOGGER = LogManager.getLogger("NetworkEventHandler");

    @Override
    public void packetReceived(PacketReceivedEvent evt) {

        // ServerJoinGamePacket - Handler
        if ( evt.getPacket() instanceof ServerJoinGamePacket ) {
            GlobalVars.serverJoinGamePacketHandler.handle(evt, (ServerJoinGamePacket) evt.getPacket());
        }

        // ServerPlayerPositionRotationPacket - Handle
        if ( evt.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            GlobalVars.serverPlayerPositionRotationPacketHandler.handle(evt, (ServerPlayerPositionRotationPacket) evt.getPacket());
        }

    }

}
