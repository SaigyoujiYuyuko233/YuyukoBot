package network.meikai.mc.uuzselfplaybot.command;

import network.meikai.mc.uuzselfplaybot.GlobalVars;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class CliConfig implements Callable<Integer> {

    // Bot
    @CommandLine.Option(names = {"-n", "--name"}, description = "机器人名字")
    private String botname;

    @CommandLine.Option(names = {"-l", "--log-level"}, description = "日志等级 INFO | WARN | ERROR")
    private String logLevel;

    // Online
    @CommandLine.Option(names = {"--online-enable"}, description = "启用正版验证")
    private Boolean isOnline;

    @CommandLine.Option(names = {"--online-username"}, description = "正版验证账号")
    private String onlineUsername;

    @CommandLine.Option(names = {"--online-password"}, description = "正版验证密码")
    private String onlinePassword;

    // Connection
    @CommandLine.Option(names = {"-h", "--host"}, description = "服务器IP")
    private String host;

    @CommandLine.Option(names = {"-p", "--port"}, description = "服务器端口")
    private int port;

    @CommandLine.Option(names = {"-t", "--connect-timeout"}, description = "连接超时时间(s)")
    private int connectTimeout;

    @CommandLine.Option(names = {"-tr", "--read-timeout"}, description = "读超时时间(s)")
    private int readTimeout;

    @CommandLine.Option(names = {"-tw", "--write-timeout"}, description = "写超时时间(s)")
    private int writeTimeout;

    @CommandLine.Option(names = {"-ta", "--keepalive-timeout"}, description = "检查超时间隔(ms)")
    private int keepaliveTimeout;



    @Override
    public Integer call() throws Exception {

        // 已经被赋值过  所以说如果不一样 则代表cli参数不一样

        // Bot
        GlobalVars.BOTNAME = botname != null && !botname.equals(GlobalVars.BOTNAME) ? botname : GlobalVars.BOTNAME;
        GlobalVars.logLevel =  logLevel != null && !logLevel.equals(GlobalVars.logLevel) ? logLevel : GlobalVars.logLevel;

        // Online
        GlobalVars.isOnline = isOnline != null && !isOnline.equals(GlobalVars.isOnline) ? isOnline : GlobalVars.isOnline;
        GlobalVars.onlineUsername = onlineUsername != null && !onlineUsername.equals(GlobalVars.onlineUsername) ? onlineUsername : GlobalVars.onlineUsername;
        GlobalVars.onlinePassword = onlinePassword != null && !onlinePassword.equals(GlobalVars.onlinePassword) ? onlinePassword : GlobalVars.onlinePassword;

        // Connection
        GlobalVars.HOST = host != null && !host.equals(GlobalVars.HOST) ? host : GlobalVars.HOST;
        GlobalVars.PORT = port != 0 && port != GlobalVars.PORT ? port : GlobalVars.PORT;
        GlobalVars.connectTimeout = connectTimeout != 0 && connectTimeout != GlobalVars.connectTimeout ? connectTimeout : GlobalVars.connectTimeout;
        GlobalVars.readTimeout = readTimeout != 0 && readTimeout != GlobalVars.readTimeout ? readTimeout : GlobalVars.readTimeout;
        GlobalVars.writeTimeout = writeTimeout != 0 && writeTimeout != GlobalVars.writeTimeout ? writeTimeout : GlobalVars.writeTimeout;
        GlobalVars.keepaliveTimeout = keepaliveTimeout != 0 && keepaliveTimeout != GlobalVars.keepaliveTimeout ? keepaliveTimeout : GlobalVars.keepaliveTimeout;

//        GlobalVars.MAIN_LOGGER.info(GlobalVars.BOTNAME);
//        GlobalVars.MAIN_LOGGER.info(GlobalVars.logLevel);
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.isOnline) );
//        GlobalVars.MAIN_LOGGER.info(GlobalVars.onlineUsername);
//        GlobalVars.MAIN_LOGGER.info(GlobalVars.onlinePassword);
//        GlobalVars.MAIN_LOGGER.info(GlobalVars.HOST);
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.PORT) );
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.connectTimeout) );
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.readTimeout)) ;
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.writeTimeout)) ;
//        GlobalVars.MAIN_LOGGER.info( String.valueOf(GlobalVars.keepaliveTimeout) );

        return 0;
    }
}
