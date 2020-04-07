package com.SAS.game_event_logger;

import java.time.LocalDate;

public class Goal extends GameEvent {

    private Team scoringTeam;
    private Player scoringPlayer;

    public Goal(String gameID, LocalDate gameDate, Integer gameMinute, Team scoringTeam, Player scoringPlayer) {
        super(gameID, gameDate, gameMinute);
        this.scoringTeam = scoringTeam;
        this.scoringPlayer = scoringPlayer;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public void setScoringTeam(Team scoringTeam) {
        this.scoringTeam = scoringTeam;
    }

    public Player getScoringPlayer() {
        return scoringPlayer;
    }

    public void setScoringPlayer(Player scoringPlayer) {
        this.scoringPlayer = scoringPlayer;
    }
}
