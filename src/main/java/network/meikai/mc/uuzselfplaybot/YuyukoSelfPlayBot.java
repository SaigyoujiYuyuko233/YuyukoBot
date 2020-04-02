package network.meikai.mc.uuzselfplaybot;

import com.github.tomaslanger.chalk.Chalk;

public class YuyukoSelfPlayBot {
//    private static final String HOST = "218.93.206.47";
//    private static final int PORT = 2705;

    public static void main(String[] arg) {
        Chalk.setColorEnabled(true);

        System.out.println(Chalk.on("__   __                 _         ____   ___ _____\n" +
                "\\ \\ / /   _ _   _ _   _| | _____ | __ ) / _ \\_   _|\n" +
                " \\ V / | | | | | | | | | |/ / _ \\|  _ \\| | | || |\n" +
                "  | || |_| | |_| | |_| |   < (_) | |_) | |_| || |\n" +
                "  |_| \\__,_|\\__, |\\__,_|_|\\_\\___/|____/ \\___/ |_|\n" +
                "            |___/\n").green());

        System.out.println("[+] Version: " + GlobalVars.VERSION + "(" + GlobalVars.GIT_BRANCH + ") Git-" + GlobalVars.GIT_REVERSION + " Build: " + GlobalVars.BUILD);

    }

}