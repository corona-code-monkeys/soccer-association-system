package com.SAS.soccer_association_system.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GameReport extends Report {

    private LocalDate gameDate;
    //TODO: need to reconsider the use of time
    private LocalDateTime time;

    public GameReport() {
    }

    public GameReport(LocalDate gameDate, LocalDateTime time) {
        this.gameDate = gameDate;
        this.time = time;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
