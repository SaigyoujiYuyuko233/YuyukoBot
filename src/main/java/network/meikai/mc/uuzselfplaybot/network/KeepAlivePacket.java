package network.meikai.mc.uuzselfplaybot.network;

import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.apache.logging.log4j.LogManager;

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
                Thread.sleep(GlobalVars.keepaliveTimeout);
            } catch (InterruptedException e) {
                LogManager.getLogger("Keepalive").error(e.getMessage());
                LogManager.getLogger("Keepalive").error(e.getStackTrace());
            }
        }
    }

}
