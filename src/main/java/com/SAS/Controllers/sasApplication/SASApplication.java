/**
 * This class represents the external controller of the system
 */
package com.SAS.Controllers.sasApplication;

import com.SAS.Controllers.systemController.ApplicationController;
import com.SAS.LeagueManagement.LeagueManagementController;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.teamManagenemt.TeamManagement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SASApplication {

    @Autowired
    private UserController userController;
    private LeagueManagementController leagueManagement;
    private TeamManagement teamManagement;
    private ApplicationController applicationControllerController; //check


    /**
     * Empty Constructor, initializes the other controllers
     */
    public SASApplication() {
        userController= new UserController();
        leagueManagement= new LeagueManagementController(userController);
        teamManagement= new TeamManagement(userController);
    }

    //TODO: in UI : if returns the role switch to home page with correct privileges, else show alert that user doesn't exist
    /**
     * This function logs in the user
     * @param username
     * @param password
     * @return user role if the user exists in the system, thus was logged in, otherwise ""
     */
    public String login(String username, String password, String clientURL){
        return userController.isUserExist(username, password, clientURL);
    }

    /**
     * This function removes the user from logged in users
     * @param username
     * @return true of false
     */
    public boolean exit(String username){
        return userController.exit(username);
    }

    //TODO: In UI: if true- show alert that the user was created and switch to home page so he would log in, wlse show error message
    /**
     * This function calls the creation of a user using the userController
     * @param userName
     * @param password
     * @param fullName
     * @param type
     * @param approval
     * @param email
     * @return true if was created, otherwise false
     */
    public boolean createUser(String userName, String password, String fullName, String email, String type, boolean approval){
        if(userController.createUser(userName, password, fullName, email, type, approval) != null)
            return true;
        return false;
    }

    //TODO: UI- if true, show alert that the request was sent to the association
    /**
     * This function registers the team
     * @param teamOwner
     * @param teamName
     */
    public boolean registerTeam(String teamOwner, String teamName){
        return userController.sendNotificationToRepresentative(teamManagement.createANewTeam(teamOwner, teamName));
    }


    //TODO: UI- if true, show alert that a notification about the team registration was sent to its owner
    /**
     * This function applies the confirmation/denial of the team
     * @param teamName
     * @param representative
     * @param confirm
     */
    public boolean confirmTeam(String teamName, String representative, boolean confirm){
        return teamManagement.commitConfirmationOfTeam(teamName, representative, confirm);
    }

    /**
     * This function deletes the user associated with the given user name
     * @param username
     * @return true or false
     */
    public boolean deleteUser(String username){
        return userController.deleteUSer(username);
    }

    /**
     * This function deletes the role associated with the given user name from role table
     * @param username
     * @param role
     * @return true or false
     */
    public boolean deleteUserRole(String username, String role){
        return userController.deleteUserRole(username, role);
    }


    /**
     * This function calls the edit of user details
     * @param details
     * @param type
     * @return true if the details were edited
     */
    public boolean editUserDetails(String username, JSONObject details, String type){
        if (details != null && type != null){
            return userController.editUserDetails(username, details, type);
        }
        return false;
    }

    /**
     * This function closes a team by team owner
     * @param teamName
     * @param owner
     * @return
     */
    public boolean closeTeam(String teamName, String owner){
        return teamManagement.closeTeam(teamName, owner);
    }

    /**
     * This function reopens a team by team owner
     * @param teamName
     * @param owner
     * @return
     */
    public boolean openTeam(String teamName, String owner){
        return teamManagement.openTeam(teamName, owner);
    }


    /**
     * The function receives a team name and returns the team page
     * @param teamName
     * @return
     */
    public JSONObject getTeamPage(String teamName) {
        return teamManagement.enterTeamPage(teamName);
    }


    /**
     * The function receives team name, new team owner username and nominated by username
     * and returns true if nominated, otherwise returns false
     * @param teamName
     * @param newTeamOwner
     * @param nominatedBy
     * @return
     */
    public boolean addTeamOwner(String teamName, String newTeamOwner, String nominatedBy) {
        return teamManagement.addAdditionalTeamOwner(teamName, newTeamOwner, nominatedBy);
    }

    /**
     * The function receives team name, team owner username to remove and nominated by username
     * and returns true if removes, otherwise returns false
     * @param teamName
     * @param removeTeamOwner
     * @param nominatedBy
     * @return
     */
    public boolean removeTeamOwner(String teamName, String removeTeamOwner, String nominatedBy) {
        return teamManagement.removeTeamOwner(teamName, removeTeamOwner, nominatedBy);
    }

    /**
     * The function receives team name, team owner username to remove and nominated by username
     * and returns true if removes, otherwise returns false
     * @param teamName
     * @param newTeamManager
     * @param nominatedBy
     * @param approval
     * @return
     */
    public boolean addTeamManager(String teamName, String newTeamManager, String nominatedBy, boolean approval) {
        return teamManagement.addTeamManager(teamName, newTeamManager, nominatedBy, approval);
    }

    /**
     * The function receives team name, team owner username to remove and nominated by username
     * and returns true if removes, otherwise returns false
     * @param teamName
     * @param removeTeamManager
     * @param nominatedBy
     * @return
     */
    public boolean removeTeamManager(String teamName, String removeTeamManager, String nominatedBy) {
        return teamManagement.removeTeamManager(teamName, removeTeamManager, nominatedBy);
    }

    /**
     * The function receives the team name, team owner and transaction details to add
     * and returns true if it added, otherwise returns false
     * @return boolean - true or false
     */
    public boolean addTeamTransaction(String teamName, String teamOwner, JSONObject transactionDetails) {
        return teamManagement.addTransactionToTeam(teamName, transactionDetails, teamOwner);
    }

    /**
     * The function returns the teams
     * @return array of names
     */
    public JSONArray getTeams() {
        return teamManagement.getTeams();
    }


    /**
     * The function receives asset details, team name and team owner and returns true if the asset added,
     * otherwise returns false
     * @param teamName
     * @param teamOwner
     * @param assetType
     * @param assetDetails
     * @return
     */
    public boolean addTeamAsset(String teamName, String teamOwner, String assetType, JSONObject assetDetails) {
        if (assetType.equals("Facility")){
            return teamManagement.addAssetToTeam(teamName, assetType, teamOwner, assetDetails);
        }

        else {
            String userName = assetDetails.get("userName").toString();
            String password = assetDetails.get("password").toString();
            String fullName = assetDetails.get("fullName").toString();
            String email = assetDetails.get("email").toString();
            return teamManagement.addAssetToTeam(teamName, assetType, teamOwner, userName, password, fullName, email, assetDetails);
        }
    }

    /**
     * The function edits the asset details and returns true if succeeded, otherwise return false
     * @param teamName
     * @param assetType
     * @param assetDetails
     * @return true or false
     */
    public boolean editAssetDetails(String teamName, String assetType, JSONObject assetDetails) {
        if (assetType.equals("Facility")) {
            return teamManagement.editAssetDetails(teamName, assetType, assetDetails);
        }

        else {
            return teamManagement.editAssetDetails(teamName, assetType, assetDetails.get("assetName").toString(), assetDetails);
        }
    }

    /**
     * The function receives team name, asset type and name and removes the asset from the team
     * it returns true if succeeded, otherwise - returns false
     * @param teamName
     * @param assetType
     * @param assetName
     * @param teamOwner
     * @return true or false
     */
    public boolean removeTeamAsset(String teamName, String assetType, String assetName, String teamOwner) {
        return teamManagement.removeAsset(teamName, assetType, assetName, teamOwner);
    }

    /**
     * The function returns all the optional nominees for the team owner
     * @param teamName
     * @return list of usernames
     */
    public JSONArray getOptionalNomineesForTeamOwner(String teamName) {
        return teamManagement.showOptionalNomineesForTeamOwner(teamName);
    }

    /**
     * The function returns all the optional nominees for the team manager
     * @param teamName
     * @return list of usernames
     */
    public JSONArray getOptionalNomineesForTeamManager(String teamName) {
        return teamManagement.showOptionalNomineesForTeamManager(teamName);
    }

    /**
     * The function returns all the assets of the team
     * @param teamName
     * @return list of assets
     */
    public JSONObject getAssetsForTeam(String teamName) {
        return teamManagement.getAllTeamAssets(teamName);
    }

    /**
     * This function gets the user details from DB
     * @param username
     * @param role
     * @return
     */
    public JSONObject getUserDetails(String username, String role){
        return userController.getUserDetails(username, role);
    }

    /**
     * This function updates the user details in the DB
     * @param details
     * @return
     */
    public boolean setDetails(JSONObject details) {
        return userController.setDetails(details);
    }

    /**
     * This function returns the registration status of the team
     * @param teamName
     * @return
     */
    public boolean getTeamStatus(String teamName) {
        return teamManagement.getTeamRegistrationStatus(teamName);
    }

    /**
     * create a new league from the name and the user that creates it
     * @param leagueName
     * @param userCreated
     * @return
     */
    public boolean createLeague(String leagueName, String userCreated) {
        return leagueManagement.addNewLeague(leagueName,userCreated);
    }

    /**
     * gets the league name and season year and attach league to season
     * @param leagueName
     * @param seasonYear
     * @return
     */
    public boolean addSeasonToLeague(String leagueName, int seasonYear) {
        return leagueManagement.addSeasonToALeague(seasonYear,leagueName);

    }

    /**
     * assign a referee to a league in season
     * @param leagueName - the league's name
     * @param seasonYear - the year of the season
     * @param username - name of the referee
     * @return
     */
    public boolean assignRefereeToLeague(String leagueName, String seasonYear, String username) {
        return leagueManagement.addNewRefereeToLeague(seasonYear,leagueName,username);
    }

    /**
     * sets policies to league in specific season
     * @param leagueName
     * @param seasonYear
     * @param rankPolicy
     * @param pointsPolicy
     * @param gamePolicy
     * @param username
     * @return
     */
    public boolean setPolicies(String leagueName, int seasonYear, String rankPolicy, String pointsPolicy, String gamePolicy, String username) {
        return leagueManagement.addPolicies(leagueName,seasonYear,rankPolicy,pointsPolicy,gamePolicy, username);
    }

    /**
     *
     * @return json array with all the leagues names
     */
    public JSONArray getLeagues() {
        return leagueManagement.getLeagues();
    }
}
