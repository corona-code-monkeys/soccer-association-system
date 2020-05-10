package com.SAS.domain.report;

import java.time.LocalDate;

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
    public boolean editReport(String description){
        if (description!=null){
            this.description = description;
            return true;
        }
        return false;
    }





}

