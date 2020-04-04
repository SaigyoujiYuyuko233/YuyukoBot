package network.meikai.mc.uuzselfplaybot.network;

import network.meikai.mc.uuzselfplaybot.GlobalVars;

public class KeepAlivePacket implements Runnable{


    @Override
    public void run() {
        while (true) {
//            GlobalVars.MAIN_LOGGER.debug("isConnected: " + GlobalVars.CLIENT.getSession().isConnected() );

            if ( !GlobalVars.CLIENT.getSession().isConnected() ) {
                GlobalVars.MAIN_LOGGER.error("Connect timeout!");
                System.exit(-2);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
