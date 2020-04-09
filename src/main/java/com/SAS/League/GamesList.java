package com.SAS.League;

import java.util.ArrayList;

public class GamesList {
    private String leagueName;
    private int season;
    ArrayList<Game> gameslist;
    /**
     * replace default constructor
     */
    public GamesList() {
    }

    /**
     * GamesList constructor with attributes
     * @param league: the name of the league
     * @param season: the year when the season started
     * @param gameslist: the list of the games
     */
    public GamesList(String league, int season, ArrayList<Game> gameslist) {
        this.leagueName = league;
        this.season = season;
        this.gameslist = gameslist;
    }

    /**
     * getter for the league name
     * @return: league name
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     * setter for the league name
     * @param league: the name of the league
     */
    public void setLeagueName(String league) {
        this.leagueName = league;
    }

    /**
     * getter for the year
     * @return: the year the league started
     */
    public int getSeason() {
        return season;
    }

    /**
     * setter for the year the league started
     * @param : the year the league started
     */
    public void setSeason(int season) {
        this.season = season;
    }
}
