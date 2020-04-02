package network.meikai.mc.uuzselfplaybot.network.Events;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;

/**
 * 当服务端发送此数据包是为了确定玩家位置  客户端必须回应 ClientPlayerPositionRotationPacket 带有相同坐标的数据包
 * 否则服务器会忽略玩家移动包直到 客户端回来正确的 PlayerPositionRotationPacket 数据包
 */
public class ServerPlayerPositionRotationPacketHandler {

    public void handle(PacketReceivedEvent evt, ServerPlayerPositionRotationPacket packet) {
        evt.getSession().send(new ClientPlayerPositionRotationPacket(
                true,
                packet.getX(),
                packet.getY(),
                packet.getZ(),
                packet.getYaw(),
                packet.getPitch()
        ));

        EventHandler.EVENT_LOGGER.info(
        "Received Packet [S]PlayerPositionRotation. Acknowledge position! (" +
                packet.getX() + "," +
                packet.getY() + "," +
                packet.getZ() + "," +
                packet.getYaw() + "," +
                packet.getPitch() + ")"
        );
    }

}
