package network.meikai.mc.yuyukobot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Arrays;

public class LaunchWrapper {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, URISyntaxException {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{
                new File("YuyukoBotCore.jar").toURI().toURL(),
        });

        Class<?> configClass = classLoader.loadClass("network.meikai.mc.uuzselfplaybot.config.ConfigHandler");
        String gameVersion = (String) configClass.getMethod("getSpecificConfig", String.class, String.class).invoke(null,"Bot","version");

        classLoader = new URLClassLoader(new URL[]{
                new File("YuyukoBotCore.jar").toURI().toURL(),
                new File("MCProtocolLib/" + gameVersion + ".jar").toURI().toURL(),
        });

        Class<?> main = classLoader.loadClass("network.meikai.mc.uuzselfplaybot.YuyukoSelfPlayBot");

        System.out.println("加载库 [YuyukoBotCore.jar] ...");
        System.out.println("加载库 [MCProtocolLib/" + gameVersion + ".jar] ...");

        main.getMethod("main", String[].class).invoke(null,(Object) args);
    }

}
