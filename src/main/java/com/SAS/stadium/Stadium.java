package com.SAS.stadium;

import com.SAS.game.Game;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;

import java.util.LinkedList;
import java.util.List;

/**
 * The class represent a soccer stadium of a team
 */
public class Stadium implements TeamAsset {

    private String name;
    private String location;
    private List<Game> gamesList;
    private Team homeTeam;
    private FacilityType facilityType;

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
    public Stadium(String name, String location, List<Game> gamesList, Team homeTeam, FacilityType facilityType) {
        this.name = name;
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

    /**
     * This function removes the facility from being in the team's facilities
     */
    @Override
    public void removeAssetFromTeam() {
        this.homeTeam.removeFacility(this);
        this.homeTeam = null;
    }

    /**
     * This function sets the FacilityType
     * @param facilityType
     */
    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    /**
     * This function returns the FacilityType
     * @return FacilityType
     */
    public FacilityType getFacilityType() {
        return facilityType;
    }

    /**
     * This functions edits the stadiums details
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     */
    @Override
    public boolean editDetails(List<String> details) {
        //first is name, second is location, third is type
        FacilityType facilityType = convertStringToFacilityType(details.get(2));
        if (facilityType == null)
            return false;
        else{
            setName(details.get(0));
            setLocation(details.get(1));
            setFacilityType(facilityType);
            return true;
        }
    }

    /**
     * This function converts string to FacilityType
     * @param facilityType
     * @return facilityType
     */
    private FacilityType convertStringToFacilityType(String facilityType){
        switch(facilityType){
           case "Stadium":
               return FacilityType.STADIUM;
            case "Training":
                return FacilityType.TRAINING;
            default:
                System.out.println("Error, no such type");
                return null;
        }
    }
}
