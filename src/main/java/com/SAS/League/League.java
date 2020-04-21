package com.SAS.League;

import com.SAS.User.Referee;
import com.SAS.game.Game;


import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * class league that create a league according to the features in the black class diagram
 */
public class League {
    private String name;
    private HashSet<Season> seasonList;
    private HashMap<Season, HashSet<Referee>> referees;
    private HashMap<Season, GamesArrangement> gamesList;
    private HashMap<Season, Table> tables;
    private HashMap<Season, PointsPolicy> pointsPolicy;
    private HashMap<Season, GamesPolicy> gamesPolicy;
    private HashMap<Season, LeagueRankPolicy> rankPolicy;


    /**
     * league constructor with attributes
     *
     * @param name - the name of the league
     */
    public League(String name) {
        this.name = name;
        this.seasonList = new HashSet<>();
        this.gamesList = new HashMap<Season, GamesArrangement>();
        this.tables = new HashMap<Season, Table>();
        this.referees= new HashMap<>();
        this.pointsPolicy = new HashMap<Season, PointsPolicy>();
        this.gamesPolicy = new HashMap<Season, GamesPolicy>();
        this.rankPolicy = new HashMap<Season, LeagueRankPolicy>();
    }

    /**
     * @return the name of the league
     */
    public String getName() {
        return name;
    }

    /**
     * @param season the season we want to add to the league
     */
    public void addSeason(Season season) {
        this.seasonList.add(season);
        this.referees.put(season,new HashSet<>());
    }

    /**
     *
     * @return the seasons set
     */
    public HashSet<Season> getSeasonList() {
        return seasonList;
    }

    /**
     * @param season the season you want to get her table
     * @return the tables for this league
     */
    public Table getTables(Season season) {
        return tables.get(season);
    }

    /**
     * @param season the season you want to get her game list
     * @return the games list for this league in specific season
     */
    public GamesArrangement getGamesList(Season season) {
        return gamesList.get(season);
    }

    /**
     * function that adds a games list for a specific season to this league
     *
     * @param season: the season relevant to this game list
     * @param games:  the list of games in this league for this specific season
     */
    public void addGamesList(Season season, LinkedList<Game> games) {
        if (this.seasonList.contains(season) && !this.gamesList.containsKey(season)) {
            GamesArrangement gamesArrangment = new GamesArrangement(this, season, games);
            this.gamesList.put(season, gamesArrangment);
            season.addGamesList(this, games);
        }
    }


    /**
     * @param season: the season relevant to this table
     * @param table:  the league table for this season
     */
    public void addTable(Season season, Table table) {
        if (this.seasonList.contains(season) && !this.tables.containsKey(season)) {
            this.tables.put(season, table);
            season.addTable(this, table);
        }
    }

    /**
     * @return the points policy for this league in the specific season
     */
    public PointsPolicy getPointsPolicy(Season season) {
        return this.pointsPolicy.get(season);
    }

    /**
     * @return the game policy for this league in the specific season
     */
    public GamesPolicy getGamesPolicy(Season season) {
        return this.gamesPolicy.get(season);
    }

    /**
     * @return the league ranking policy for this league in the specific season
     */
    public LeagueRankPolicy getRankPolicy(Season season) {
        return this.rankPolicy.get(season);
    }

    /**
     * @param season      the season that the games policy is relevant to
     * @param gamesPolicy the game policy you want to add
     */
    public void addGamePolicy(Season season, GamesPolicy gamesPolicy) {
        if (this.seasonList.contains(season) && !this.gamesPolicy.containsKey(season)) {
            this.gamesPolicy.put(season, gamesPolicy);
            season.addGamePolicy(this, gamesPolicy);
        }
    }


    /**
     * @param season       the season that the points policy is relevant to
     * @param pointsPolicy the points policy you want to add
     */
    public void addPointsPolicy(Season season, PointsPolicy pointsPolicy) {
        if (this.seasonList.contains(season) && !this.pointsPolicy.containsKey(season)) {
            this.pointsPolicy.put(season, pointsPolicy);
            season.addPointsPolicy(this, pointsPolicy);
        }
    }

    /**
     * @param season     the season that the points policy is relevant to
     * @param rankPolicy the rank policy you want to add
     */
    public void addRankPolicy(Season season, LeagueRankPolicy rankPolicy) {
        if (this.seasonList.contains(season) && !this.rankPolicy.containsKey(season)) {
            this.rankPolicy.put(season, rankPolicy);
            season.addRankPolicy(this, rankPolicy);
        }
    }

    /**
     *
     * @return the hashmap of the referees for this league for each season
     */
    public HashMap<Season,HashSet<Referee>> getReferees() {
        return referees;
    }

    /**
     * @param season     the season that the  referee is relevant to
     * @param ref the referee you want to add to the list of referees
     */
    public void addReferee(Season season, Referee ref) {
        if (this.seasonList.contains(season) && this.referees.containsKey(season)) {
            this.referees.get(season).add(ref);
            season.addReferee(this, ref);
        }
    }
}
