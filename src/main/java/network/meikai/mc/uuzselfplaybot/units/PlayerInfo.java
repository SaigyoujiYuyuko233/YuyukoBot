package network.meikai.mc.uuzselfplaybot.units;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;

import java.util.UUID;

public class PlayerInfo {

    private UUID uuid;
    private String playername;
    private GameMode gameMode;
    private int ping;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }
}
