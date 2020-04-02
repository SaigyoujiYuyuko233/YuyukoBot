package network.meikai.mc.uuzselfplaybot;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class YuyukoSelfPlayBot {
//    private static final String HOST = "218.93.206.47";
//    private static final int PORT = 2705;

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
                "            |___/\n"));

        System.out.println("[+] Version: " + GlobalVars.VERSION + "(" + GlobalVars.GIT_BRANCH + ") Git-" + GlobalVars.GIT_REVERSION + " Build: " + GlobalVars.BUILD + "\n");

        GlobalVars.MAIN_LOGGER.info("Using BotName @" + GlobalVars.BOTNAME);
        GlobalVars.MAIN_LOGGER.info("Dst Server - " + GlobalVars.HOST + ":" + GlobalVars.PORT);
    }

}