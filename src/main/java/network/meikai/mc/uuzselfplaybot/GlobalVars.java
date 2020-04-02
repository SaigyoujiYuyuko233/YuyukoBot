package network.meikai.mc.uuzselfplaybot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class GlobalVars {

    // app info
    public static final Manifest MANIFEST = new Manifest();
    public static final Attributes ATTRIBUTES = MANIFEST.getMainAttributes();

    public static String VERSION =  ATTRIBUTES.getValue("implementation-version");;
    public static String GIT_BRANCH = ATTRIBUTES.getValue("implementation-git-branch");
    public static String GIT_REVERSION = ATTRIBUTES.getValue("implementation-git-sha");
    public static String BUILD = ATTRIBUTES.getValue("implementation-build");

    // server connect
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 25565;
    public static final String BOTNAME = "DreamyYuyuko233";

    // Classes
    public static final Logger LOGGER = LogManager.getLogger("YuyukoBot #" + BOTNAME + "");

}
