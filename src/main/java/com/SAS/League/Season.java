package com.SAS.League;

import java.util.ArrayList;
import java.util.HashMap;

class Team {

    public String getName;

    public void addBudget(int year, int budget) {
    }
}//to be removed

class Game {
}//to be removed

/**
 * class season that define the object season and attributes
 */
public class Season {
    private int year;
    private HashMap<League, GamesList> gamesList;
    private HashMap<Team, Budget> budgets;
    private HashMap<League, Table> tables;
    ArrayList<Team> teamsList;
    ArrayList<League> leaguesList;
    private HashMap<League, PointsPolicy> pointsPolicy;
    private HashMap<League, GamesPolicy> gamesPolicy;
    private HashMap<League, LeagueRankPolicy> rankPolicy;


    /**
     * param constructor
     *
     * @param year:in     what year the season started (if the season is 2019-2020 it will called 2019)
     * @param teamsList
     * @param leaguesList
     */
    public Season(int year, ArrayList<Team> teamsList, ArrayList<League> leaguesList) {
        this.year = year;
        this.gamesList = new HashMap<>();
        this.teamsList = teamsList;
        this.leaguesList = leaguesList;
        this.budgets = new HashMap<>();
        this.tables = new HashMap<>();
        this.pointsPolicy = new HashMap<>();
        this.gamesPolicy = new HashMap<>();
        this.rankPolicy = new HashMap<>();
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
     * @param league the league that you want to get her table
     * @return the games list for this season in specific league
     */
    public GamesList getGamesList(League league) {
        return this.gamesList.get(league);

    }

    /**
     * @param team the team that you want to get her budget
     * @return the budgets list for this season in specific league
     */
    public Budget getBudgets(Team team) {
        return this.budgets.get(team);
    }

    /**
     * @param league the league that you want to get her table
     * @return the table for this season in specific League
     */
    public Table getTable(League league) {
        return this.tables.get(league);
    }

    /**
     * @return the points policy for this season for the specific league
     */
    public PointsPolicy getPointsPolicy(League league) {
        return this.pointsPolicy.get(league);
    }

    /**
     * @return the game policy for this season for the specific league
     */
    public GamesPolicy getGamesPolicy(League league) {
        return this.gamesPolicy.get(league);
    }

    /**
     * @return the league ranking policy for this season for the specific league
     */
    public LeagueRankPolicy getRankPolicy(League league) {
        return this.rankPolicy.get(league);
    }

    /**
     * @param league the league that the games will be take place in
     * @param games  the list of the games
     */
    public void addGamesList(League league, ArrayList<Game> games) {
        if (this.gamesList.get(league) == null) {
            boolean wasAdded = false;
            GamesList game = new GamesList(league, this, games);
            this.gamesList.put(league, game);
            for (int i = 0; i < this.leaguesList.size(); i++) {
                if (this.leaguesList.get(i).getName().equals(league)) {
                    this.leaguesList.get(i).addGamesList(this, games);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.gamesList.remove(game); //if the league doesn't exist
            }
        }
    }


    /**
     * @param team   which team the budgets belong to
     * @param budget the budget of the input team for this season
     */
    public void addBudget(Team team, int budget) {
        if (this.budgets.get(team) == null) {
            boolean wasAdded = false;
            Budget budgetToAdd = new Budget(team, this, budget);
            this.budgets.put(team, budgetToAdd);
            for (int i = 0; i < this.teamsList.size(); i++) {
                if (this.teamsList.get(i).getName.equals(team)) {
                    this.teamsList.get(i).addBudget(this.year, budget);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.budgets.remove(budgetToAdd); //if the team doesn't exist
            }
        }
    }

    /**
     * @param league the league that the table is relevant to
     * @param table: the league table for this the league
     */
    public void addTable(League league, Table table) {
        if (this.tables.get(league) == null) {
            boolean wasAdded = false;
            this.tables.put(league, table);
            for (int i = 0; i < this.leaguesList.size(); i++) {
                if (this.leaguesList.get(i).getName().equals(league)) {
                    this.leaguesList.get(i).addTable(this, table);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.tables.remove(table); //if the league doesn't exist
            }
        }
    }

    /**
     * @param league      the league that the games policy is relevant to
     * @param gamesPolicy the game policy you want to add
     */
    public void addGamePolicy(League league, GamesPolicy gamesPolicy) {
        if (this.gamesPolicy.get(league) == null) {
            boolean wasAdded = false;
            this.gamesPolicy.put(league, gamesPolicy);
            for (int i = 0; i < this.leaguesList.size(); i++) {
                if (this.leaguesList.get(i).getName().equals(league)) {
                    this.leaguesList.get(i).addGamePolicy(this, gamesPolicy);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.gamesPolicy.remove(gamesPolicy); //if the league doesn't exist
            }
        }
    }

    /**
     * @param league       the league that the points policy is relevant to
     * @param pointsPolicy the points policy you want to add
     */
    public void addPointsPolicy(League league, PointsPolicy pointsPolicy) {
        if (this.pointsPolicy.get(league) == null) {
            boolean wasAdded = false;
            this.pointsPolicy.put(league, pointsPolicy);
            for (int i = 0; i < this.leaguesList.size(); i++) {
                if (this.leaguesList.get(i).getName().equals(league)) {
                    this.leaguesList.get(i).addPointsPolicy(this, pointsPolicy);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.pointsPolicy.remove(pointsPolicy); //if the league doesn't exist
            }
        }
    }

    /**
     * @param league     the league that the points policy is relevant to
     * @param rankPolicy the rank policy you want to add
     */
    public void addRankPolicy(League league, LeagueRankPolicy rankPolicy) {
        if (this.rankPolicy.get(league) == null) {
            boolean wasAdded = false;
            this.rankPolicy.put(league, rankPolicy);
            for (int i = 0; i < this.leaguesList.size(); i++) {
                if (this.leaguesList.get(i).getName().equals(league)) {
                    this.leaguesList.get(i).addRankPolicy(this, rankPolicy);
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                this.rankPolicy.remove(rankPolicy); //if the league doesn't exist
            }
        }
    }
}
