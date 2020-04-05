package network.meikai.mc.uuzselfplaybot;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import network.meikai.mc.uuzselfplaybot.command.CommandHandler;
import network.meikai.mc.uuzselfplaybot.network.KeepAlivePacket;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class YuyukoSelfPlayBot {

    public static void main(String[] arg) throws IOException {

        // read version
        Manifest mf = new Manifest();
        mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        Attributes mfAttributes = mf.getMainAttributes();

        GlobalVars.VERSION = mfAttributes.getValue("implementation-version");
        GlobalVars.GIT_BRANCH = mfAttributes.getValue("implementation-git-branch");
        GlobalVars.GIT_REVERSION = mfAttributes.getValue("implementation-git-sha");
        GlobalVars.BUILD = mfAttributes.getValue("implementation-build");

        AnsiConsole.systemInstall();

        System.out.println(Ansi.ansi().fgBright(Ansi.Color.GREEN).a("__   __                 _         ____   ___ _____\n" +
                "\\ \\ / /   _ _   _ _   _| | _____ | __ ) / _ \\_   _|\n" +
                " \\ V / | | | | | | | | | |/ / _ \\|  _ \\| | | || |\n" +
                "  | || |_| | |_| | |_| |   < (_) | |_) | |_| || |\n" +
                "  |_| \\__,_|\\__, |\\__,_|_|\\_\\___/|____/ \\___/ |_|\n" +
                "            |___/\n").reset());

        String logPrefix = "[" + Ansi.ansi().fgBright(Ansi.Color.GREEN).a("+").reset() + "] ";

        System.out.println(logPrefix + Ansi.ansi().fgBright(Ansi.Color.CYAN).a(
            "Version: " + GlobalVars.VERSION + "(" + GlobalVars.GIT_BRANCH + ") Git-" + GlobalVars.GIT_REVERSION + " Build: " + GlobalVars.BUILD
            ).reset()
        );
        System.out.println(logPrefix + Ansi.ansi().fgBright(Ansi.Color.CYAN).a("Author: 幽幽子 | QQ: 3558168775 | Github: SaigyoujiYuyuko233 \n").reset());

        // 加载cli配置
        CommandLine commandLine = new CommandLine(new CliConfig());
        int ret = commandLine.execute(arg);

        if (ret != 0) {
            System.exit(ret);
        }

        GlobalVars.MAIN_LOGGER.info("Using BotName @" + GlobalVars.BOTNAME);
        GlobalVars.MAIN_LOGGER.info("Dst Server - " + GlobalVars.HOST + ":" + GlobalVars.PORT);

        GlobalVars.MAIN_LOGGER.info("Initiate the bot...");
        GlobalVars.PROTOCOL = new MinecraftProtocol(GlobalVars.BOTNAME);
        GlobalVars.TCP_SESSION_FACTORY = new TcpSessionFactory();
        GlobalVars.CLIENT = new Client(GlobalVars.HOST, GlobalVars.PORT, GlobalVars.PROTOCOL, GlobalVars.TCP_SESSION_FACTORY);

        GlobalVars.MAIN_LOGGER.info("Try to connect to server...");
        GlobalVars.CLIENT.getSession().connect();

        GlobalVars.CLIENT.getSession().setConnectTimeout(20);
        GlobalVars.CLIENT.getSession().setReadTimeout(4);
        GlobalVars.CLIENT.getSession().setWriteTimeout(4);

        // Network handler
        GlobalVars.CLIENT.getSession().addListener(GlobalVars.eventHandler);

        if ( !GlobalVars.CLIENT.getSession().isConnected() ) {
            GlobalVars.MAIN_LOGGER.error("Connect timeout!");
            System.exit(-2);
        }

        // KeepAlive
        Thread keepAlive = new Thread(new KeepAlivePacket());
        keepAlive.setDaemon(true);
        keepAlive.setName("KeepAlive");
        keepAlive.start();

        // input handler
        Thread commandHandler = new Thread(new CommandHandler());
        commandHandler.setDaemon(false);
        commandHandler.setName("CommandHandler");
        commandHandler.start();


    }

}