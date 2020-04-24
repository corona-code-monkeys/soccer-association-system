package com.SAS.facility;

import com.SAS.game.Game;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;

import com.SAS.game.Game;
import com.SAS.team.Team;


import java.util.LinkedList;
import java.util.List;

/**
 * The class represent a soccer stadium of a team
 */
public class Facility implements TeamAsset {

    private String name;
    private String location;
    private List<Game> gamesList;
    private Team homeTeam;
    private facilityType facilityT;

    /**
     * Empty constructor
     */
    public Facility() {
        gamesList = new LinkedList<Game>();
    }

    /**
     * The method adds a game to the stadium games list
     *
     * @param name
     * @param location
     * @param gamesList
     * @param homeTeam
     */
    public Facility(String name, String location, List<Game> gamesList, Team homeTeam, facilityType facilityT) {
        this.name = name;
        this.location = location;
        this.gamesList = gamesList;
        this.homeTeam = homeTeam;
        this.facilityT = facilityT;
    }

    public boolean addGame(Game newGame) {
        if (newGame == null) {
            return false;
        }

        gamesList.add(newGame);
        return true;
    }

    /**
     * <<<<<<< HEAD
     * This function returns the name of the stadium
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * This function sets the name of the stadium
     */
    public boolean setName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * =======
     * >>>>>>> SAS-65 #initiate jdbc in the system
     * The function returns the location of the stadium
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * The function sets the location of the stadium
     *
     * @param location
     */
    public boolean setLocation(String location) {
        if (location == null || location.length() == 0) {
            return false;
        }
        this.location = location;
        return true;
    }

    /**
     * The function returns list of the games in the stadium
     *
     * @return
     */
    public List<Game> getGamesList() {
        return gamesList;
    }

    /**
     * The function sets the game list of the stadium
     *
     * @param gamesList
     */
    public void setGamesList(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    /**
     * The function returns the team that owns the stadium
     *
     * @return
     */
    public Team getTeam() {
        return homeTeam;
    }

    /**
     * The function sets a team that owns the stadium
     *
     * @param homeTeam
     */
    public void setTeam(Team homeTeam) {
        if (homeTeam != null) {
            this.homeTeam = homeTeam;
        }
    }

    public facilityType getFacilityType() {
        return facilityT;
    }

    /**
     * This function removes the facility from being in the team's facilities
     */
    @Override
    public void removeAssetFromTeam() {
        this.homeTeam.removeFacility(this);
        this.homeTeam = null;
    }

    /**
     * This function sets the facilityType
     *
     * @param facilityT
     */
    public void setFacilityType(facilityType facilityT) {
        this.facilityT = facilityT;
    }

    /**
     * This functions edits the stadiums details
     *
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     */
    @Override
    public boolean editDetails(List<String> details) {
        if (details == null) {
            return false;
        }

        for (String value : details) {
            if (value == null || value.length() == 0) {
                return false;
            }
        }

        //first is name, second is location, third is type
        facilityType ft = convertStringToFacilityType(details.get(2));
        if (ft == null)
            return false;
        else {
            setName(details.get(0));
            setLocation(details.get(1));
            setFacilityType(ft);
            return true;
        }
    }

    /**
     * This function converts string to facilityType
     *
     * @param ft
     * @return
     */
    private facilityType convertStringToFacilityType(String ft) {
        switch (ft) {
            case "Stadium":
                return facilityType.STADIUM;
            case "Training":
                return facilityType.TRAINING;
            default:
                System.out.println("Error, no such type");
                return null;
        }
    }

}
