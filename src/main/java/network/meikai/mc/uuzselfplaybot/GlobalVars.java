package network.meikai.mc.uuzselfplaybot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalVars {

    // app info
    public static final String VERSION = "0.1-SNAPSHOT";
    public static final String GIT_BRANCH = "${CI_COMMIT_REF_NAME}";
    public static final String GIT_REVERSION = "${CI_COMMIT_SHORT_SHA}";
    public static final String BUILD = "${CI_PIPELINE_ID}";

    // server connect
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 25565;
    public static final String BOTNAME = "DreamyYuyuko233";

    // Classes
    public static final Logger LOGGER = LogManager.getLogger("YuyukoBot #" + BOTNAME + "");

}
