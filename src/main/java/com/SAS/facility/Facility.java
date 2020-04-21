package com.SAS.facility;

import com.SAS.game.Game;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;

import java.util.LinkedList;
import java.util.List;

/**
 * The class represent a soccer stadium of a team
 */
public class Facility implements TeamAsset{

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

    public boolean addGame(Game newGame){
        if(newGame == null){
            return false;
        }

        gamesList.add(newGame);
        return true;
    }

    /**
     * This function returns the name of the stadium
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * This function sets the name of the stadium
     */
    public void setName(String name) {
        this.name = name;
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
    public Team getTeam() {
        return homeTeam;
    }

    /**
     * The function sets a team that owns the stadium
     * @param homeTeam
     */
    public void setTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
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
     * @param facilityT
     */
    public void setFacilityType(facilityType facilityT) {
        this.facilityT = facilityT;
    }

       /**
     * This functions edits the stadiums details
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     */
    @Override
    public boolean editDetails(List<String> details) {
        //first is name, second is location, third is type
        facilityType ft = convertStringToFacilityType(details.get(2));
        if (ft == null)
            return false;
        else{
            setName(details.get(0));
            setLocation(details.get(1));
            setFacilityType(ft);
            return true;
        }
    }

    /**
     * This function converts string to facilityType
     * @param ft
     * @return
     */
    private facilityType convertStringToFacilityType(String ft){
        switch(ft){
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
