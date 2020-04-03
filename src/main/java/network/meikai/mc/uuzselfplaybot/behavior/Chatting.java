package network.meikai.mc.uuzselfplaybot.behavior;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Log;

import java.io.IOException;

public class Chatting implements Runnable{

    @Override
    public void run() {
        while (true) {
            try {

                Terminal terminal = TerminalBuilder
                        .builder()
                        .dumb(true)
                        .system(true)
                        .jansi(true)
                        .build();
                LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

                Log.debug(terminal);

                String message = lineReader.readLine("聊天/命令 >> ");
                GlobalVars.CLIENT.getSession().send(new ClientChatPacket(message));

            } catch (IOException e) { GlobalVars.MAIN_LOGGER.error(e.getStackTrace()); }
        }
    }

}
