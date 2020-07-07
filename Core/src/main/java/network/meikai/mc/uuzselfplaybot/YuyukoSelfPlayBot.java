package network.meikai.mc.uuzselfplaybot;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import network.meikai.mc.uuzselfplaybot.command.CommandHandler;
import network.meikai.mc.uuzselfplaybot.config.ConfigHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import network.meikai.mc.uuzselfplaybot.update.CheckUpdates;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class YuyukoSelfPlayBot {

    public static void main(String[] arg) throws IOException {

        // sys config
        System.setProperty("file.encoding", "UTF-8");

        // read version
        JarFile mainJar = new JarFile(new File(YuyukoSelfPlayBot.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
        Manifest mf = mainJar.getManifest();
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
        System.out.println(logPrefix + Ansi.ansi().fgBright(Ansi.Color.CYAN).a("Author: 幽幽子 | QQ: 3558168775 | Github: SaigyoujiYuyuko233").reset());
        System.out.println(logPrefix + Ansi.ansi().fgBright(Ansi.Color.CYAN).a("给幽幽子投食: https://afdian.net/@SaigyoujiYuyuko").reset());

        // 检查更新
        new CheckUpdates().check();
        System.out.println("");

        // 加载配置
        GlobalVars.MAIN_LOGGER.info("加载配置文件...");
        GlobalVars.arg = arg;
        new ConfigHandler().handle();

        // set log level
        Configurator.setRootLevel(Level.getLevel(GlobalVars.logLevel));

        GlobalVars.MAIN_LOGGER.info("使用机器人名字 @" + GlobalVars.BOTNAME);
        GlobalVars.MAIN_LOGGER.info("目标服务器 - " + GlobalVars.HOST + ":" + GlobalVars.PORT);

        GlobalVars.MAIN_LOGGER.info("初始化机器人...");

        // if online mode is enable
        if ( GlobalVars.isOnline ) {
            try {
                GlobalVars.MAIN_LOGGER.info("正版验证启用! 尝试登陆...");
                GlobalVars.PROTOCOL = new MinecraftProtocol(GlobalVars.onlineUsername, GlobalVars.onlinePassword);
            } catch (RequestException e) {
                GlobalVars.MAIN_LOGGER.error(e.getMessage());
                e.printStackTrace();
                System.exit(-4);
            }
        } else {
            GlobalVars.PROTOCOL = new MinecraftProtocol(GlobalVars.BOTNAME);
        }

        GlobalVars.TCP_SESSION_FACTORY = new TcpSessionFactory();
        GlobalVars.CLIENT = new Client(GlobalVars.HOST, GlobalVars.PORT, GlobalVars.PROTOCOL, GlobalVars.TCP_SESSION_FACTORY);

        // setting timeout
        GlobalVars.CLIENT.getSession().setConnectTimeout(GlobalVars.connectTimeout);
        GlobalVars.CLIENT.getSession().setReadTimeout(GlobalVars.readTimeout);
        GlobalVars.CLIENT.getSession().setWriteTimeout(GlobalVars.writeTimeout);

        // Network handler
        GlobalVars.CLIENT.getSession().addListener(GlobalVars.eventHandler);

        // connect
        GlobalVars.MAIN_LOGGER.info("尝试连接到服务器...");
        GlobalVars.CLIENT.getSession().connect();

        if ( !GlobalVars.CLIENT.getSession().isConnected() ) {
            GlobalVars.MAIN_LOGGER.error("连接到服务器时出错!");
            GlobalVars.MAIN_LOGGER.error("请截图上方部分给他人以寻求帮助！");
            System.exit(-1);
        }

        // input handler
        Thread commandHandler = new Thread(new CommandHandler());
        commandHandler.setDaemon(false);
        commandHandler.setName("CommandHandler");
        commandHandler.start();

    }

}