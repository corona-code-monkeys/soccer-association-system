package com.SAS.League;

import java.util.ArrayList;

public class GamesArrangment {
    private League leagueName;
    private Season season;
    ArrayList<Game> gameslist;

    /**
     * GamesArrangment constructor with attributes
     * @param league: the name of the league
     * @param season: the year when the season started
     * @param gameslist: the list of the games
     */
    public GamesArrangment(League league, Season season, ArrayList<Game> gameslist) {
        this.leagueName = league;
        this.season = season;
        this.gameslist = gameslist;
    }

    /**
     * getter for the league name
     * @return: league name
     */
    public League getLeagueName() {
        return leagueName;
    }

    /**
     * getter for the year
     * @return: the year the league started
     */
    public Season getSeason() {
        return season;
    }

}
