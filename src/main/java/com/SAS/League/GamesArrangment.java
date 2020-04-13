package com.SAS.League;

import java.util.LinkedList;
import com.SAS.game.Game;
public class GamesArrangment {
    private League leagueName;
    private Season season;
    private LinkedList<Game> gameslist;

    /**
     * GamesArrangment constructor with attributes
     * @param league: the name of the league
     * @param season: the year when the season started
     * @param gameslist: the list of the games
     */
    public GamesArrangment(League league, Season season, LinkedList<Game> gameslist) {
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

    /**
     *
     * @return the game list of this object
     */
    public LinkedList <Game> getGameslist() {
        return gameslist;
    }

    /**
     *
     * @param game the game you want to add to the game list
     */
    public void addGame(Game game) {
        this.gameslist.add(game);
    }
}
