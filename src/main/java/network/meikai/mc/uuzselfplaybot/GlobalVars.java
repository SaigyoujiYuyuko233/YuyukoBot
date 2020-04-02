package network.meikai.mc.uuzselfplaybot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalVars {

    // app info
    public static String VERSION = "";
    public static String GIT_BRANCH = "";
    public static String GIT_REVERSION = "";
    public static String BUILD = "";

    // server connect
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 25565;
    public static final String BOTNAME = "DreamyYuyuko233";

    // Classes
    public static final Logger LOGGER = LogManager.getLogger("YuyukoBot #" + BOTNAME + "");

}
