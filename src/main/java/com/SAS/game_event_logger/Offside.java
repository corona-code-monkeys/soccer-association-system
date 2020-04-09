package com.SAS.game_event_logger;

import java.time.LocalDate;

class Team {
}//to be removed

public class Offside extends GameEvent {
    private Team teamInFavor;
    private Player playerInOffside;

    public Offside(String gameID, LocalDate gameDate, Integer gameMinute, Team teamInFavor, Player playerInOffside) {
        super(gameID, gameDate, gameMinute);
        this.teamInFavor = teamInFavor;
        this.playerInOffside = playerInOffside;
    }

    public Team getTeamInFavor() {
        return teamInFavor;
    }

    public void setTeamInFavor(Team teamInFavor) {
        this.teamInFavor = teamInFavor;
    }

    public Player getPlayerInOffside() {
        return playerInOffside;
    }

    public void setPlayerInOffside(Player playerInOffside) {
        this.playerInOffside = playerInOffside;
    }
}
