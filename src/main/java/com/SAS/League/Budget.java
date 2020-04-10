package com.SAS.League;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Budget {
    private Team team;
    private  Season season;
    private int budget;



    /**
     * GamesList constructor with attributes
     * @param team: the team the budget belong
     * @param season: the budget's season
     * @param budget: the list of the games
     */
    public Budget(Team team, Season season, int budget) {
        this.team = team;
        this.season = season;
        this.budget = budget;

    }

    /**
     *
     * @return the budget's team name
     */
    public Team getTeam() {
        return team;
    }


    /**
     *
     * @return in which season the budget is valid
     */
    public Season getSeason() {
        return season;
    }


    /**
     *
     * @return the size of the budget
     */
    public int getBudget() {
        return budget;
    }


}
