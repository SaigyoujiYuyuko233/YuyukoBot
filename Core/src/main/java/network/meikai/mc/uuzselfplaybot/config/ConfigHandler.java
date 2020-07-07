package network.meikai.mc.uuzselfplaybot.config;

import network.meikai.mc.uuzselfplaybot.GlobalVars;
import network.meikai.mc.uuzselfplaybot.YuyukoSelfPlayBot;
import network.meikai.mc.uuzselfplaybot.command.CliConfig;
import org.ini4j.Ini;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigHandler {

    private void initConfigFile(File configFile) throws IOException {
        if ( !configFile.isFile() ) {
            GlobalVars.MAIN_LOGGER.info("生成配置文件...");
            Files.copy(YuyukoSelfPlayBot.class.getResourceAsStream("/src/main/resources/config.ini"), configFile.toPath());
        }
    }

    public boolean handle() {
        try {

            /*
             * 判断配置文件是否存在
             */
            File configFile = new File("src/main/resources/config.ini");
            this.initConfigFile(configFile);

            /*
             * 加载配置文件
             */
            Ini config = new Ini();
            config.load(configFile);

            // Bot
            GlobalVars.GameVersion = config.get("Bot", "version") == null ? "1.15" : config.get("Bot", "version", String.class);
            GlobalVars.BOTNAME = config.get("Bot", "name") == null ? "YuyukoBot" : config.get("Bot", "name", String.class);
            GlobalVars.logLevel = config.get("Bot", "log") == null ? "INFO" : config.get("Bot", "log", String.class);

            // Online
            GlobalVars.isOnline = config.get("Online", "enable") == null ? false : config.get("Online", "enable", Boolean.class);
            GlobalVars.onlineUsername = config.get("Online", "username") == null ? "uuz@example.org" : config.get("Online", "username", String.class);
            GlobalVars.onlinePassword = config.get("Online", "password") == null ? "pass" : config.get("Online", "password", String.class);

            // Connection
            GlobalVars.HOST = config.get("Connection", "address") == null ? "127.0.0.1" : config.get("Connection", "address", String.class);
            GlobalVars.PORT = config.get("Connection", "port") == null ? 25565 : config.get("Connection", "port", Integer.class);
            GlobalVars.connectTimeout = config.get("Connection", "connect_timeout") == null ? 20 : config.get("Connection", "connect_timeout", Integer.class);
            GlobalVars.readTimeout = config.get("Connection", "read_timeout") == null ? 8 : config.get("Connection", "read_timeout", Integer.class);
            GlobalVars.writeTimeout = config.get("Connection", "write_timeout") == null ? 8 : config.get("Connection", "write_timeout", Integer.class);
            GlobalVars.keepaliveTimeout = config.get("Connection", "keepalive_timeout") == null ? 4000 : config.get("Connection", "keepalive_timeout", Integer.class);

            /*
             * 加载cli参数  可覆盖配置文件
             */
            CommandLine commandLine = new CommandLine(new CliConfig());
            int ret = commandLine.execute(GlobalVars.arg);

            if (ret != 0) {
                System.exit(ret);
            }

        } catch ( IOException e) {
            GlobalVars.MAIN_LOGGER.error(e.getMessage());
            e.printStackTrace();
            System.exit(-3);
        }
        return true;
    }

    public static Object getSpecificConfig(String sectionName, String Key) throws IOException {
        File configFile = new File("src/main/resources/config.ini");

        if ( !configFile.isFile() ) {
            Files.copy(YuyukoSelfPlayBot.class.getResourceAsStream("/src/main/resources/config.ini"), configFile.toPath());
        }

        Ini config = new Ini();
        config.load(configFile);

        return config.get(sectionName, Key);
    }

}
