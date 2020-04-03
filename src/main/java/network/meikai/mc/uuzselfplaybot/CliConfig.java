package network.meikai.mc.uuzselfplaybot;

import picocli.CommandLine;

import java.util.concurrent.Callable;

public class CliConfig implements Callable<Integer> {

    // config
    @CommandLine.Option(names = {"-h", "--host"}, required = true, description = "服务器IP")
    String host;

    @CommandLine.Option(names = {"-p", "--port"}, description = "服务器端口")
    int port = 25565;

    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "机器人名字")
    String botname;

    @CommandLine.Option(names = {"-t", "--token"}, required = true, description = "机器人的凭据(用于执行命令)")
    String token;


    @Override
    public Integer call() throws Exception {
        GlobalVars.HOST = host;
        GlobalVars.PORT = port;
        GlobalVars.BOTNAME = botname;
        GlobalVars.Token = token;

        return 0;
    }
}
