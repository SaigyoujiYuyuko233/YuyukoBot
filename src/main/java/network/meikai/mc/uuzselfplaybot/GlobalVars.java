package network.meikai.mc.uuzselfplaybot;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalVars {

    // version info
    public static String VERSION = "";
    public static String GIT_BRANCH = "";
    public static String GIT_REVERSION = "";
    public static String BUILD = "";

    // server connect
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 25565;
    public static final String BOTNAME = "DreamyYuyuko233";

    // Classes
    public static final Logger MAIN_LOGGER = LogManager.getLogger("YuyukoBot(" + BOTNAME + ")");

    // bot
    public static final MinecraftProtocol PROTOCOL = new MinecraftProtocol(BOTNAME);
    public static final TcpSessionFactory TCP_SESSION_FACTORY = new TcpSessionFactory();
    public static final Client CLIENT = new Client(HOST, PORT, PROTOCOL, TCP_SESSION_FACTORY);

}
