package com.SAS.game_event_logger;

import com.SAS.User.Player;

import java.time.LocalDate;

public class Offence extends GameEvent {
    private Player committed;
    private Player against;//NULLABLE
    private String description;

    public Offence(String gameID, LocalDate gameDate, Integer gameMinute, Player committed, Player against, String description) {
        super(gameID, gameDate, gameMinute);
        this.committed = committed;
        this.against = against;
        this.description = description;
    }

    public Player getCommitted() {
        return committed;
    }

    public void setCommitted(Player committed) {
        this.committed = committed;
    }

    public Player getAgainst() {
        return against;
    }

    public void setAgainst(Player against) {
        this.against = against;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
