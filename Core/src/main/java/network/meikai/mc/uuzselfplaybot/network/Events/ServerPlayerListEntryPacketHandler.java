package network.meikai.mc.uuzselfplaybot.network.Events;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import network.meikai.mc.uuzselfplaybot.GlobalVars;
import network.meikai.mc.uuzselfplaybot.network.EventHandler;
import network.meikai.mc.uuzselfplaybot.units.PlayerInfo;

public class ServerPlayerListEntryPacketHandler {

    public void handle(PacketReceivedEvent evt, ServerPlayerListEntryPacket packet) {
        GameProfile gameProfile = packet.getEntries()[0].getProfile();

        // add player
        if ( packet.getAction() == PlayerListEntryAction.ADD_PLAYER ) {
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setUuid(gameProfile.getId());
            playerInfo.setGameMode(packet.getEntries()[0].getGameMode());
            playerInfo.setPing(packet.getEntries()[0].getPing());
            playerInfo.setPlayername(gameProfile.getName());

            GlobalVars.onlinePlayers.put(gameProfile.getId(), playerInfo);
        }

        // delete player
        if ( packet.getAction() == PlayerListEntryAction.REMOVE_PLAYER ) {
            GlobalVars.onlinePlayers.remove(gameProfile.getId());
        }

        // update ping
        if ( packet.getAction() == PlayerListEntryAction.UPDATE_LATENCY ) {
            int newLatency = packet.getEntries()[0].getPing();
            PlayerInfo playerInfo = GlobalVars.onlinePlayers.get(gameProfile.getId());
            playerInfo.setPing(newLatency);

            GlobalVars.onlinePlayers.put(gameProfile.getId(), playerInfo);
        }

        EventHandler.EVENT_LOGGER.debug(GlobalVars.onlinePlayers);

    }

}
