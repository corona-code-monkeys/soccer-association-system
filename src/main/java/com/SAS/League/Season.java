package com.SAS.League;

import java.util.ArrayList;

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
    private ArrayList<GamesList> gamesList;
    private ArrayList<Budget> budgets;
    ArrayList<Team> teamsList;
    ArrayList<League> leaguesList;

    /**
     * empty constructor to replace the default one
     */
    public Season() {
        this.gamesList = new ArrayList<>();
        this.leaguesList = new ArrayList<>();
        this.teamsList = new ArrayList<Team>();
        this.budgets=new ArrayList<>();
    }


    /**
     * param constructor
     *
     * @param year:in     what year the season started (if the season is 2019-2020 it will called 2019)
     * @param teamsList
     * @param leaguesList
     */
    public Season(int year, ArrayList<Team> teamsList, ArrayList<League> leaguesList, ArrayList<Budget> budgets) {
        this.year = year;
        this.gamesList = new ArrayList<>();
        this.teamsList = teamsList;
        this.leaguesList = leaguesList;
        this.budgets=budgets;
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
        return this.gamesList;

    }

    /**
     * @param league the league that the games will be take place in
     * @param games  the list of the games
     */
    public void addGamesList(String league, ArrayList<Game> games) {
        this.gamesList.add(new GamesList(league, this.year, games));
        for (int i = 0; i < this.leaguesList.size(); i++) {
            if (this.leaguesList.get(i).getName().equals(league)) {
                this.leaguesList.get(i).addGamesList(this.year, games);
            }
        }
    }

    /**
     * @return the budgets list for this season in specific league
     */
    public ArrayList<Budget> getBudgets() {
        return this.budgets;
    }

    /**
     * @param team   which team the budgets belong to
     * @param budget the budget of the input team for this season
     */
    public void addBudget(String team, int budget) {
        this.budgets.add(new Budget(team,this.year,budget));
        for (int i = 0; i < this.teamsList.size(); i++) {
            if (this.teamsList.get(i).getName.equals(team)) {
                this.teamsList.get(i).addBudget(this.year, budget);
            }
        }
    }
}
