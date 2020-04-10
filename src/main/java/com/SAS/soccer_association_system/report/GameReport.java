package com.SAS.soccer_association_system.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The class represents the game report that made by the referee when a game ends
 */
public class GameReport {

    private LocalDate gameDate;
    private LocalDateTime time;

    /**
     * Empty constructor
     */
    public GameReport() {
    }

    /**
     * Params constructor
     * @param gameDate
     * @param time
     */
    public GameReport(LocalDate gameDate, LocalDateTime time) {
        this.gameDate = gameDate;
        this.time = time;
    }


    /**
     * The function returns the date the game took place
     * @return
     */
    public LocalDate getGameDate() {
        return gameDate;
    }

    /**
     * The function sets the date the game took place
     */
    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    /**
     * The function returns the time the game ended
     * @return
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * The function sets the time the game ended
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

