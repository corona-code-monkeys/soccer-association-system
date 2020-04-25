package com.SAS.League;
import com.SAS.team.Team;
import com.SAS.game.Game;
import com.SAS.User.Referee;

import com.SAS.game.Game;
import com.SAS.team.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;



/**
 * class season that define the object season and attributes
 */
public class Season {
    private int year;
    private HashMap<League, GamesArrangement> gamesList;
    private HashMap<Team, Budget> budgets;
    private HashMap<League, Table> tables;
    private HashSet<Team> teamsList;
    private HashSet<League> leaguesList;
    private HashMap<League, HashSet<Referee>> referees;
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
    public Season(int year, HashSet<Team> teamsList, HashSet<League> leaguesList) {
        this.year = year;
        this.gamesList = new HashMap<>();
        this.teamsList = teamsList;
        this.leaguesList = leaguesList;
        this.budgets = new HashMap<>();
        this.referees= new HashMap<>();
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
     *
     * @return getter for the team set in this season
     */
    public HashSet<Team> getTeamsList() {
        return teamsList;
    }

    /**
     *
     * @param team the team you want to add the the team list of this season
     */
    public void addTeam(Team team) {
        this.teamsList.add(team);
    }

    /**
     *
     * @return set of the league that participate in this season
     */
    public HashSet<League> getLeaguesList() {
        return leaguesList;
    }

    /**
     *
     * @param league the league you want to add the the league list of this season
     */
    public void addLeague(League league) {
        this.leaguesList.add(league);
        this.referees.put(league,new HashSet<>());
    }

    /**
     * @param league the league that you want to get her table
     * @return the games list for this season in specific league
     */
    public GamesArrangement getGamesList(League league) {
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
    public void addGamesList(League league, LinkedList<Game> games) {
        if (this.leaguesList.contains(league)&&!this.gamesList.containsKey(league)) {
            GamesArrangement gamesArrangment = new GamesArrangement(league, this, games);
            this.gamesList.put(league, gamesArrangment);
            league.addGamesList(this, games);
        }
    }


    /**
     * @param team   which team the budgets belong to
     * @param budget the budget of the input team for this season
     */
    public void addBudget(Team team, double budget) {
        if (this.teamsList.contains(team)&&!this.budgets.containsKey(team)) {
            teamsList.add(team);
            Budget budgetToAdd = new Budget(team, this, budget);
            this.budgets.put(team, budgetToAdd);
        }
    }

    /**
     * @param league the league that the table is relevant to
     * @param table: the league table for this the league
     */
    public void addTable(League league, Table table) {
        if (this.leaguesList.contains(league)&&!this.tables.containsKey(league)) {
            this.tables.put(league, table);
            league.addTable(this, table);
        }
    }

    /**
     * @param league      the league that the games policy is relevant to
     * @param gamesPolicy the game policy you want to add
     */
    public void addGamePolicy(League league, GamesPolicy gamesPolicy) {
        if (this.leaguesList.contains(league)&&!this.gamesPolicy.containsKey(league)) {
            this.gamesPolicy.put(league, gamesPolicy);
            league.addGamePolicy(this, gamesPolicy);
        }
    }

    /**
     * @param league       the league that the points policy is relevant to
     * @param pointsPolicy the points policy you want to add
     */
    public void addPointsPolicy(League league, PointsPolicy pointsPolicy) {
        {
            if (this.leaguesList.contains(league)&&!this.pointsPolicy.containsKey(league)) {
                this.pointsPolicy.put(league, pointsPolicy);
                league.addPointsPolicy(this, pointsPolicy);
            }
        }
    }

    /**
     * @param league     the league that the rank policy is relevant to
     * @param rankPolicy the rank policy you want to add
     */
    public void addRankPolicy(League league, LeagueRankPolicy rankPolicy) {
        if (this.leaguesList.contains(league)&&!this.rankPolicy.containsKey(league)) {
            this.rankPolicy.put(league, rankPolicy);
            league.addRankPolicy(this, rankPolicy);
        }
    }

    /**
     *
     * @return the hashmap of the referees for this season for each league
     */
    public HashMap<League,HashSet<Referee>> getReferees() {
        return referees;
    }


    /**
     * @param league     the league that the  referee is relevant to
     * @param ref the referee you want to add to the list of referees
     */
    public void addReferee(League league, Referee ref) {
        if (this.leaguesList.contains(league)) {
            this.referees.get(league).add(ref);
            league.addReferee(this, ref);
        }
    }
}
