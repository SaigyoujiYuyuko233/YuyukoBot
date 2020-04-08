package network.meikai.mc.uuzselfplaybot;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import network.meikai.mc.uuzselfplaybot.behavior.BotMoving;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;
import network.meikai.mc.uuzselfplaybot.units.PlayerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;

public class GlobalVars {

    // version info
    public static String VERSION = "";
    public static String GIT_BRANCH = "";
    public static String GIT_REVERSION = "";
    public static String BUILD = "";
    public static final String RELEASE_URL = "https://gitlab.uuzdream.cn/api/v4/projects/4/releases";

    /*
     * Configuration
     */
    public static String[] arg;

    // connection
    public static String HOST;
    public static int PORT;
    public static int connectTimeout;
    public static int readTimeout;
    public static int writeTimeout;
    public static int keepaliveTimeout;

    // bot
    public static String BOTNAME;
    public static String Token;
    public static String logLevel;

    // online mode
    public static Boolean isOnline;
    public static String onlineUsername;
    public static String onlinePassword;


    // Classes
    public static final Logger MAIN_LOGGER = LoggerFactory.getLogger("YuyukoBot");

    // bot
    public static MinecraftProtocol PROTOCOL;
    public static TcpSessionFactory TCP_SESSION_FACTORY;
    public static Client CLIENT;

    // Handlers
    public static final EventHandler eventHandler = new EventHandler();

    // Bot behavior
    public static final BotMoving BotMoving = new BotMoving();

    // Bot position
    public static boolean canMove = false;
    public static Double BotX = 0D;
    public static Double BotY = 0D;
    public static Double BotZ = 0D;
    public static Float BotYaw = 0F;
    public static Float BotPitch = 0F;

    // Server INFOs
    public static HashMap<UUID, PlayerInfo> onlinePlayers = new HashMap<>();

}
