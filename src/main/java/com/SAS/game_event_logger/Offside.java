package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.team.Team;

import java.time.LocalDate;

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
