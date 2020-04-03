package network.meikai.mc.uuzselfplaybot.behavior;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;

public class BotMoving {

    public void moveToPosition(final Double dstX, Double dstY, final Double dstZ) {

        Thread movingX = new Thread(new Runnable() {
            @Override
            public void run() {
                double changingX = Math.abs(Math.abs(dstX) - Math.abs(GlobalVars.BotX));

                // 算出需要执行多少次
                int stepsX = (int) Math.round(changingX / 0.23);

                for ( int i = 0; i < stepsX; i++ ) {
                    if (dstX > GlobalVars.BotX) GlobalVars.BotX += 0.23;
                    if (dstX < GlobalVars.BotX) GlobalVars.BotX -= 0.23;

                    // yaw
                    GlobalVars.BotYaw = (float) Math.atan2(
                            GlobalVars.BotX - dstX,
                            GlobalVars.BotZ - dstZ
                    ) + 10;

                    EventHandler.EVENT_LOGGER.debug("Yaw: " + GlobalVars.BotYaw);

                    GlobalVars.CLIENT.getSession().send(new ClientPlayerPositionRotationPacket(
                        true,
                        GlobalVars.BotX,
                        GlobalVars.BotY,
                        GlobalVars.BotZ,
                        GlobalVars.BotYaw,
                        GlobalVars.BotPitch
                    ));

                    try {
                        Thread.sleep(60L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread movingZ = new Thread(new Runnable() {
            @Override
            public void run() {

                double changingZ = Math.abs(Math.abs(dstZ) - Math.abs(GlobalVars.BotZ));

                // 算出需要执行多少次
                int stepsZ = (int) Math.round(changingZ / 0.23);

                for ( int i = 0; i < stepsZ; i++ ) {
                    if (dstZ > GlobalVars.BotZ) GlobalVars.BotZ += 0.23;
                    if (dstZ < GlobalVars.BotZ) GlobalVars.BotZ -= 0.23;

                    GlobalVars.CLIENT.getSession().send(new ClientPlayerPositionPacket(
                            true,
                            GlobalVars.BotX,
                            GlobalVars.BotY,
                            GlobalVars.BotZ
                    ));

                    try {
                        Thread.sleep(60L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        if ( GlobalVars.canMove ) {
            movingX.setName("PlayerMoving-X");
            movingX.setDaemon(true);
            movingX.start();
        }

        if ( GlobalVars.canMove ) {
            movingZ.setName("PlayerMoving-Z");
            movingZ.setDaemon(true);
            movingZ.start();
        }

    }


}
