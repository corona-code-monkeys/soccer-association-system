package com.SAS.League;

import java.util.ArrayList;

public class Budget {
    private String team;
    private int season;
    private int budget;
    /**
     * replace default constructor
     */
    public Budget() {
        this.team="";
        this.season=-1;
        this.budget=-1;
    }

    /**
     * GamesList constructor with attributes
     * @param team: the name of the league
     * @param season: the year when the season started
     * @param budget: the list of the games
     */
    public Budget(String team, int season, int budget) {
        this.team = team;
        this.season = season;
        this.budget = budget;
    }

    /**
     *
     * @return the budget's team name
     */
    public String getTeam() {
        return team;
    }

    /**
     *
     * @param team: the budget's team name
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     *
     * @return in which season the budget is valid
     */
    public int getSeason() {
        return season;
    }

    /**
     *
     * @param season: in which season the budget is valid
     */
    public void setSeason(int season) {
        this.season = season;
    }

    /**
     *
     * @return the size of the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     *
     * @param budget: set the size of the budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }
}
