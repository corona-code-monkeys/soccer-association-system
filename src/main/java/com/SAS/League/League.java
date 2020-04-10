package com.SAS.League;

import java.util.ArrayList;
import java.util.HashMap;


class Table {
}//to be removed

/**
 * class league that create a league according to the features in the black class diagram
 */
public class League {
    private String name;
    ArrayList<Season> seasonList;
    private HashMap<Season, GamesList> gamesList;
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
        this.seasonList = new ArrayList<>();
        this.gamesList = new HashMap<Season, GamesList>();
        this.tables = new HashMap<Season, Table>();
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
    }

    /**
     * @param season the season you want to get her table
     * @return the tables for this league
     */
    public Table getTables(Season season) {
        return tables.get(season);
    }

    /**
     *
     * @param season the season you want to get her game list
     * @return the games list for this league in specific season
     */
    public GamesList getGamesList(Season season) {
        return gamesList.get(season);
    }

    /**
     * function that adds a games list for a specific season to this league
     *
     * @param season: the season relevant to this game list
     * @param games:  the list of games in this league for this specific season
     */
    public void addGamesList(Season season, ArrayList<Game> games) {
        boolean wasAdded=false;
        GamesList gamesList=new GamesList(this, season, games);
        this.gamesList.put(season,gamesList);
        for (int i = 0; i < seasonList.size(); i++) {
            if (seasonList.get(i).getYear() == season.getYear()) {
                seasonList.get(i).addGamesList(this, games);
                wasAdded=true;
            }
        }
        if(!wasAdded)
            this.gamesList.remove(gamesList);//if the season doesn't exist
    }

    /**
     * @param season: the season relevant to this table
     * @param table:  the league table for this season
     */
    public void addTable(Season season, Table table) {
        boolean wasAdded=false;
        this.tables.put(season,table);
        for (int i = 0; i < seasonList.size(); i++) {
            if (seasonList.get(i).getYear() == season.getYear()) {
                seasonList.get(i).addTable(this, table);
                wasAdded=true;

            }
            if(!wasAdded)
                this.gamesList.remove(gamesList);//if the season doesn't exist
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
     *
     * @param season the season that the games policy is relevant to
     * @param gamesPolicy the game policy you want to add
     */
    public void addGamePolicy(Season season, GamesPolicy gamesPolicy) {
        boolean wasAdded = false;
        this.gamesPolicy.put(season, gamesPolicy);
        for (int i = 0; i < this.seasonList.size(); i++) {
            if (this.seasonList.get(i).getYear() == season.getYear()) {
                if (this.seasonList.get(i).getYear() == season.getYear()) {
                    this.seasonList.get(i).addGamePolicy(this, gamesPolicy);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.gamesPolicy.remove(gamesPolicy); //if the season doesn't exist
            }
        }
    }

    /**
     *
     * @param season the season that the points policy is relevant to
     * @param pointsPolicy the points policy you want to add
     */
    public void addPointsPolicy(Season season, PointsPolicy pointsPolicy ) {
        boolean wasAdded = false;
        this.pointsPolicy.put(season,pointsPolicy);
        for (int i = 0; i < this.seasonList.size(); i++) {
            if (this.seasonList.get(i).getYear()==season.getYear()) {
                this.seasonList.get(i).addPointsPolicy(this, pointsPolicy);
                wasAdded = true;
            }
        }
        if (!wasAdded) {
            this.pointsPolicy.remove(pointsPolicy); //if the season doesn't exist
        }
    }
    /**
     *
     * @param season the season that the points policy is relevant to
     * @param rankPolicy the rank policy you want to add
     */
    public void addRankPolicy(Season season, LeagueRankPolicy rankPolicy  ) {
        boolean wasAdded = false;
        this.rankPolicy.put(season,rankPolicy);
        for (int i = 0; i < this.seasonList.size(); i++) {
            if (this.seasonList.get(i).getYear()==season.getYear()) {
                this.seasonList.get(i).addRankPolicy(this, rankPolicy);
                wasAdded = true;
            }
        }
        if (!wasAdded) {
            this.rankPolicy.remove(rankPolicy); //if the season doesn't exist
        }
    }
}
