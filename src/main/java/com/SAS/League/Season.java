package com.SAS.League;

import java.util.ArrayList;

class Team {
}//to be removed
class Game {
}//to be removed

/**
 * class season that define the object season and attributes
 */
public class Season {
    private int year;
    private ArrayList<GamesList> gamesList;
    ArrayList<Team> teamsList;
    ArrayList<League> leaguesList;

    /**
     * empty constructor to replace the default one
     */
    public Season() {
        this.gamesList = new ArrayList<>();
        this.leaguesList = new ArrayList<>();
        this.teamsList = new ArrayList<Team>();
    }

    
    /**
     * param constructor
     *
     * @param year:in     what year the season started (if the season is 2019-2020 it will called 2019)
     * @param teamsList
     * @param leaguesList
     */
    public Season(int year, ArrayList<Team> teamsList, ArrayList<League> leaguesList) {
        this.year = year;
        this.gamesList = new ArrayList<>();
        this.teamsList = teamsList;
        this.leaguesList = leaguesList;
    }

    /**
     * setter for the year that the season started
     *
     * @param year: the year for the current season
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * setter for the year that the season started
     *
     * @return the year for the current season
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the games list for this season in specific league
     */
    public ArrayList<GamesList> getGamesList() {
        return gamesList;

    }

    /**
     * @param league
     * @param games
     */
    public void addGamesList(String league, ArrayList<Game> games) {
        gamesList.add(new GamesList(league, this.year, games));
        for (int i = 0; i < leaguesList.size(); i++) {
            if (leaguesList.get(i).getName().equals(league)) {
                leaguesList.get(i).addGamesList(this.year, games);
            }
        }
    }
}
