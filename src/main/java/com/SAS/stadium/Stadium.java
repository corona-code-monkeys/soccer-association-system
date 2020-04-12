package com.SAS.stadium;

import java.util.LinkedList;

/**
 * The class represent a soccer stadium of a team
 */
public class Stadium {

    private String location;
    private List<Game> gamesList;
    private Team homeTeam;
    private facilityType facilityType;

    /**
     * Empty constructor
     */
    public Stadium() {
        gamesList = new LinkedList<Game>();
    }

    /**
     * The method adds a game to the stadium games list
     * @param location
     * @param gamesList
     * @param homeTeam
     */
    public Stadium(String location, List<Game> gamesList, Team homeTeam, facilityType facilityType) {
        this.location = location;
        this.gamesList = gamesList;
        this.homeTeam = homeTeam;
        this.facilityType = facilityType;
    }

    public boolean addGame(Game newGame){
        if(newGame == null){
            return false;
        }

        gamesList.add(newGame);
        return true;
    }

    /**
     * The function returns the location of the stadium
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * The function sets the location of the stadium
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The function returns list of the games in the stadium
     * @return
     */
    public List<Game> getGamesList() {
        return gamesList;
    }

    /**
     * The function sets the game list of the stadium
     * @param gamesList
     */
    public void setGamesList(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    /**
     * The function returns the team that owns the stadium
     * @return
     */
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * The function sets a team that owns the stadium
     * @param homeTeam
     */
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
}
