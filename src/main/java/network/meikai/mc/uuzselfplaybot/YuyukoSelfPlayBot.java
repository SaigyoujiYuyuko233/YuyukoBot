package network.meikai.mc.uuzselfplaybot;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerMovementPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

public class YuyukoSelfPlayBot {
    private static final boolean SPAWN_SERVER = true;
    private static final boolean VERIFY_USERS = false;
//    private static final String HOST = "218.93.206.47";
//    private static final int PORT = 2705;
    private static final String BUILD_NUMBER = "$CI_JOB_ID";

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 25565;

    public static void main(String[] arg) {
        System.out.println(BUILD_NUMBER);

        MinecraftProtocol protocol = new MinecraftProtocol("test");
        final Client client = new Client(HOST, PORT, protocol, new TcpSessionFactory());

        client.getSession().connect(true);

        client.getSession().addListener(new SessionAdapter() {
            private Double botX;
            private Double botY;
            private Double botZ;
            private Float botYaw;
            private Float botPitch;


            @Override
            public void packetReceived(PacketReceivedEvent event) {
                if ( event.getPacket() instanceof ServerPlayerPositionRotationPacket ) {
                    botX = ((ServerPlayerPositionRotationPacket) event.getPacket()).getX();
                    botY = ((ServerPlayerPositionRotationPacket) event.getPacket()).getY();
                    botZ = ((ServerPlayerPositionRotationPacket) event.getPacket()).getZ();
                    botYaw = ((ServerPlayerPositionRotationPacket) event.getPacket()).getYaw();
                    botPitch = ((ServerPlayerPositionRotationPacket) event.getPacket()).getPitch();

                    System.out.println("Position | " + botX + "," + botY  + "," + botZ + "," + botYaw + "," + botPitch);

                    ServerPlayerPositionRotationPacket packet = event.getPacket();
                    client.getSession().send(new ClientTeleportConfirmPacket(packet.getTeleportId()));
                    client.getSession().send( new ClientPlayerPositionRotationPacket( true, packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch() ) );
                }
            }
        });

    }

}