package network.meikai.mc.uuzselfplaybot.behavior;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;

public class BotMoving {

    public void moveToPosition(final Double dstX, Double dstY, final Double dstZ) {
        Thread moving = new Thread(new Runnable() {
            @Override
            public void run() {

                // 计算2点之间的距离
                double changingX = Math.abs(Math.abs(dstX) - Math.abs(GlobalVars.BotX));
                double changingZ = Math.abs(Math.abs(dstZ) - Math.abs(GlobalVars.BotZ));

                double distance = Math.sqrt( Math.pow(changingX, 2) + Math.pow(changingZ, 2) );

                // 算出需要执行多少次
                int steps = (int) Math.round(distance / 0.215);

                for ( int i = 0; i < steps; i++ ) {
                    GlobalVars.BotX += 0.215;
                    GlobalVars.BotZ += 0.215;

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
            moving.setName("PlayerMoving");
            moving.setDaemon(true);
            moving.start();
        }

    }


}
