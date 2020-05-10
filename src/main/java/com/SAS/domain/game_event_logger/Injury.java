package com.SAS.domain.game_event_logger;

import com.SAS.domain.User.Player;

import java.time.LocalDate;

public class Injury extends GameEvent {

    private Player injuredPlayer;
    private String description;

    public Injury(String gameID, LocalDate gameDate, Integer gameMinute, Player injuredPlayer, String description) {
        super(gameID, gameDate, gameMinute);
        this.injuredPlayer = injuredPlayer;
        this.description = description;
    }

    public Player getInjuredPlayer() {
        return injuredPlayer;
    }

    public void setInjuredPlayer(Player injuredPlayer) {
        this.injuredPlayer = injuredPlayer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
