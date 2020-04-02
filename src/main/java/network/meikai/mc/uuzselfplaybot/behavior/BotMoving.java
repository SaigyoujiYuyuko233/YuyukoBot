package network.meikai.mc.uuzselfplaybot.behavior;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;

public class BotMoving {

    public void moveToPosition(final Double dstX, Double dstY, final Double dstZ) {
        Thread movingX = new Thread(new Runnable() {
            @Override
            public void run() {

                // 间距
                double changingX = Math.abs(Math.abs(dstX) - Math.abs(GlobalVars.BotX));

                // 算出需要执行多少次
                int stepsX = (int) Math.round(changingX / 0.215);

                for ( int i = 0; i < stepsX; i++ ) {
                    if (dstX > GlobalVars.BotX) GlobalVars.BotX += 0.215;
                    if (dstX < GlobalVars.BotX) GlobalVars.BotX -= 0.215;

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

        Thread movingZ = new Thread(new Runnable() {
            @Override
            public void run() {

                // 间距
                double changingZ = Math.abs(Math.abs(dstZ) - Math.abs(GlobalVars.BotZ));

                // 算出需要执行多少次
                int stepsZ = (int) Math.round(changingZ / 0.215);

                for ( int i = 0; i < stepsZ; i++ ) {
                    if (dstZ > GlobalVars.BotZ) GlobalVars.BotZ += 0.215;
                    if (dstZ < GlobalVars.BotZ) GlobalVars.BotZ -= 0.215;

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
