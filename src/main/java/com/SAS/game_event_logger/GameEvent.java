package com.SAS.game_event_logger;

import java.time.LocalDate;

public abstract class GameEvent {
    private String gameID;
    private LocalDate gameDate;
    private Integer gameMinute;

    public GameEvent(String gameID, LocalDate gameDate, Integer gameMinute) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.gameMinute = gameMinute;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getGameMinute() {
        return gameMinute;
    }

    public void setGameMinute(Integer gameMinute) {
        this.gameMinute = gameMinute;
    }
}
