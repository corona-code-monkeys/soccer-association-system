package com.SAS.soccer_association_system.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The class represents the game report that made by the referee when a game ends
 */
public class GameReport {

    private LocalDate gameDate;
    //maybe will be events object
    private String description;

    /**
     * Empty constructor
     */
    public GameReport() {
    }

    /**
     * Params constructor
     * @param gameDate
     */
    public GameReport(LocalDate gameDate, String description) {
        this.gameDate = gameDate;
        this.description = description;
    }


    /**
     * The function returns the date the game took place
     * @return
     */
    public LocalDate getGameDate() {
        return gameDate;
    }

    //TODO: implement edit report method
    public void editReport(){
    }





}

