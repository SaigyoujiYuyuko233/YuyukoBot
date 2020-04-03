package network.meikai.mc.uuzselfplaybot.behavior;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;

import java.util.Scanner;

public class Chatting implements Runnable{

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message;
            
            message = scanner.nextLine();

            GlobalVars.CLIENT.getSession().send(new ClientChatPacket(message));
        }
    }

}
