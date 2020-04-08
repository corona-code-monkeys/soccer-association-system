package com.SAS.soccer_association_system.stadium;

import java.util.LinkedList;

/**
 * The class represent a football stadium of a team
 */
public class Stadium {

    private String location;
    private List<Game> gamesList;
    private Team homeTeam;

    public Stadium() {
        gamesList = new LinkedList<Game>();
    }

    /**
     * The method adds a game to the stadium games list
     * @param location
     * @param gamesList
     * @param homeTeam
     */
    public Stadium(String location, List<Game> gamesList, Team homeTeam) {
        this.location = location;
        this.gamesList = gamesList;
        this.homeTeam = homeTeam;
    }

    public boolean addGame(Game newGame){
        if(newGame == null){
            return false;
        }

        gamesList.add(newGame);
        return true;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Game> getGamesList() {
        return gamesList;
    }

    public void setGamesList(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
}
