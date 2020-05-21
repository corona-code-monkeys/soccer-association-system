/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.crudoperations.CRUD;
import com.SAS.crudoperations.TeamCRUD;
import com.SAS.crudoperations.TransactionCRUD;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.facility.*;
import com.SAS.systemLoggers.LoggerFactory;
import com.SAS.team.Team;

import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

import java.util.LinkedList;

public class TeamManagement {

    private LoggerFactory logger;
    private UserController userController;

    /**
     * Constructor
     * @param userController
     */
    public TeamManagement(UserController userController) {
        this.userController = userController;
        logger = LoggerFactory.getInstance();
    }

    /**
     * The function receives a team name and enters the team page if exists.
     * It return a JSON representing the team.
     * @param teamName
     * @return JSONObject if the team was restored properly from the DB, else null
     */
    public JSONObject enterTeamPage(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            logger.logError("Fault: unable to get the team page - team name is empty");
            JSONObject error = new JSONObject();
            error.put("Error", "Unable to get the team page - team name is empty");
            return error;
        }
        Team getTeam = retrieveTeam(teamName);
        if (getTeam!=null)
            return getTeam.getTeamJSON();
        JSONObject error = new JSONObject();
        error.put("Error", "Could not retrieve team page");
        return error;
    }

    /**
     * This function retrieves the team from the DB by team name
     * @param teamName
     * @return Team
     */
    private Team retrieveTeam (String teamName){
        Team team = TeamCRUD.getTeamByName(teamName);
        if (team  == null) {
            logger.logError("Fault: unable to get the team - team doesn't exist");
            return null;
        }
        return team;
    }

    /**
     * This function retrieves the user from the DB
     * @param userName
     * @return
     */
    private User retrieveUser(String userName){
        return UsersCRUD.restoreRoleForUser(UsersCRUD.getUserIdByUserName(userName));
    }

    /**
     * This function allows the team owner to add an asset to the team.
     * @param assetType - is player or coach
     * @return true or false
     */
    public boolean addAssetToTeam(String teamName, String assetType, String ownerUsername, String username, String password, String fullname, String email, JSONObject details) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        User user = retrieveUser(ownerUsername);

        if (!canAddRemoveAsset(user)){
            logger.logError("Fault: unable to add: user not authorized to add an asset to this team");
            return false;
        }
        TeamAsset asset = null;
        //create the asset
        switch (assetType) {
            case "Player":
                asset = (Player) userController.createUser(username, password, fullname, email, "PLAYER", true);
                team.addPlayerToTeam((Player)asset);
                TeamCRUD.addPlayerToTeam(team.getName(), username);
                editAssetDetails(teamName, assetType, username, details);
                logger.logEvent("User: " + ((Role)user).getUserName() + ". Added player to " + team.getName() + " team.");
                break;
            case "Coach":
                asset = (Coach) userController.createUser(username, password, fullname, email,"COACH", true);
                team.setCoach((Coach)asset);
                TeamCRUD.setCoachToTeam(username, team.getName());
                editAssetDetails(teamName, assetType, username, details);
                logger.logEvent("User: " + ((Role)user).getUserName() + ". Added coach to " + team.getName() + " team.");
                break;
             default:
                return false;
        }
        asset.setTeam(team);
        return true;
    }

    /**
     * This function allows the team owner to add a facility to the team.
     * @param assetType is Facility
     * @return true or false
     */
    public boolean addAssetToTeam(String teamName, String assetType, String username, JSONObject details) {
        Team team = retrieveTeam(teamName);
        User user = retrieveUser(username);

        if (!canAddRemoveAsset(user) || team ==null){
            logger.logError("Fault: unable to add: user not authorized to add an asset to this team");
            return false;
        }
        if (assetType.equals("Facility")) {
            TeamAsset asset = null;
            //create the facility
            asset = new Facility();
            team.addFacility((Facility) asset);
            editAssetDetails(teamName, assetType, details);
            logger.logEvent("User: " + ((Role) user).getUserName() + ". Added facility to " + team.getName() + " team.");
            asset.setTeam(team);
            return true;
        }else{
            return false;
        }
    }

    /**
     * This function receives a list of details and a teamAsset and updates the asset's details
     * @param assetType
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     * If return false should ask the user to enter the details again
     */
    public boolean editAssetDetails(String teamName, String assetType, JSONObject details)
    {
        Team team = retrieveTeam(teamName);

        if (team!=null && validParam(assetType) && details !=null) {
            TeamAsset asset = team.getAssetByNameAndType(assetType, details.get("name").toString());
            boolean result = false;
            if (asset != null && details != null) {
                result = asset.editDetails(details);
                //update db
                if (assetType.equals("Facility")) {
                    Facility facility = (Facility) asset;
                    TeamCRUD.addOrEditFacilityToTeam(team.getName(), facility.getName(), facility.getFacilityType().toString(), facility.getLocation());
                } else {
                    userController.editUserDetails(((Role) asset).getUserName(), details, assetType.toUpperCase());
                }
                return result;
            }
        }
        logger.logError("Invalid details");
        return false;
    }

    /**
     * This function receives a list of details and a teamAsset and updates the asset's details
     * @param assetType
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     * If return false should ask the user to enter the details again
     */
    public boolean editAssetDetails(String teamName, String assetType, String assetName, JSONObject details){
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        if (validParam(assetType) && validParam(assetName) && details !=null) {
            TeamAsset asset = team.getAssetByNameAndType(assetType, assetName);
            if (asset == null) {
                logger.logError("Asset not found");
                return false;
            } else
                return editAssetDetails(teamName, assetType, assetName, details);
        }
        else {
            logger.logError("Invalid details");
            return false;
        }
    }


    /**
     * This function removes a team asset
     * @param assetName
     * @param assetType
     * @param username
     */
    public boolean removeAsset(String teamName, String assetType, String assetName, String username){
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        User user = retrieveUser(username);

        if (canAddRemoveAsset(user) && (ownsTeam(teamName, user) || managesTeam(teamName, user))) {
            TeamAsset asset = team.getAssetByNameAndType(assetType, assetName);

            asset.removeAssetFromTeam();
            //remove from db
            if (!removeAssetFromDB(teamName, asset)) {
                logger.logError("Error in deleting asset");
                return false;
            }
            else {
                logger.logEvent("User: " + ((Role) user).getUserName() + ". Removed asset from " + team.getName() + " team.");
                return true;
            }
        }
        else{
            logger.logError("Fault: unable to remove: the asset is not on te possession of the team");
            return false;
        }
    }

    /**
     * This function removes the asset from the db
     * @param asset
     * @return
     */
    private boolean removeAssetFromDB(String teamName, TeamAsset asset) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        switch(asset.type()){
            case "Player":
                String usernameP = ((Player) asset).getUserName();
                return TeamCRUD.removePlayerFromTeam(usernameP,teamName) && UsersCRUD.deleteRole(usernameP, "player");
            case "Coach":
                String usernameC = ((Coach) asset).getUserName();
                return TeamCRUD.removeCoachFromTeam(usernameC, teamName) && UsersCRUD.deleteRole(usernameC, "coach");
            case "Facility":
                return TeamCRUD.removeFacilityFromTeam(teamName,((Facility)asset).getName());
            default:
                return false;
        }
    }

    /**
     * The function receives a user and a team and adds the user to the owners of the team
     * in case he's not already a team owner
     * @param newTeamOwnerName
     * @param nominatedByName
     */
    public boolean addAdditionalTeamOwner(String teamName, String newTeamOwnerName, String nominatedByName) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        User newTeamOwner = retrieveUser(newTeamOwnerName);
        User nominatedBy = retrieveUser(nominatedByName);

        //check if the user can owns the team and that the nominate is the owner of the team
        if (validateUserCanOwnTeam(teamName, newTeamOwner) && ownsTeam(teamName, nominatedBy)) {
            newTeamOwner = userController.addRoleToUser(newTeamOwner, "TEAM_OWNER", true);
            team.addTeamOwner((TeamOwner)newTeamOwner);
            ((TeamOwner) newTeamOwner).setTeam(team);
            ((TeamOwner) newTeamOwner).setNominatedBy((TeamOwner)nominatedBy);
            //add to db
            TeamCRUD.addOwnerToTeam(((TeamOwner) newTeamOwner).getUserName(), team.getName());
            JSONObject details = new JSONObject();
            details.put("team",teamName);
            details.put("nominatedBy",nominatedByName);
            String username = ((TeamOwner)newTeamOwner).getUserName();
            userController.editUserDetails(username, details, "TEAM_OWNER");
            logger.logEvent("User: " + ((Role)nominatedBy).getUserName() + ". Added team owner to " + team.getName() + " team.");
        }
        else {
            System.out.println("The user is unauthorized to own the team");
            return false;
        }
        return true;
    }

    /**
     * The function receives a user and a team and adds the user as the team manager
     * TODO: UI will need to popup a window to get approval
     * @param newTeamManagerName
     * @param nominatedByName
     * @param approval - a boolean which determines if the manager got privileges from the team owner
     * @return newTeamManager - the team manager
     */
    public boolean addTeamManager(String teamName, String newTeamManagerName, String nominatedByName, boolean approval){
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        User newTeamManager = retrieveUser(newTeamManagerName);
        User nominatedBy = retrieveUser(nominatedByName);

        //check if the team already has a manager
        if(teamHasManager(teamName)){
            System.out.println("The team already has a manager, please remove it first to nominate a new manager");
            return false;
        }
        //check if the user can manage this team and that the user that nominates him is the team owner
        if (validateTeamManager(newTeamManager) && ownsTeam(teamName, nominatedBy)){
            newTeamManager = userController.addRoleToUser(newTeamManager, "TEAM_MANAGER" ,approval);
            team.setTeamManager((TeamManager)newTeamManager);
            ((TeamManager)newTeamManager).setTeam(team);
            ((TeamManager)newTeamManager).setNominatedBy((TeamOwner)nominatedBy);
            //update in db
            JSONObject details = new JSONObject();
            details.put("team",teamName );
            details.put("nominatedBy",nominatedByName);
            String username = ((TeamManager)newTeamManager).getUserName();
            userController.editUserDetails(username, details, "TEAM_MANAGER");
            TeamCRUD.setManagerToTeam(username, team.getName());

            logger.logEvent("User: " + ((Role)nominatedBy).getUserName() + ". Added team manager to " + team.getName() + " team.");
            return true;
        }
        else{
            logger.logError("Fault: unable to add: user not authorized to add an asset to this team");
            return false;
        }
    }

    /**
     * This function checks if the team already has a manager, if so returns true, otherwise returns false
     * @return true or false
     */
    private boolean teamHasManager(String teamName) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        return team.getManager() != null;
    }

    /**
     * This function checks if the user is not already a team manager or owner
     * @param newTeamManager
     * @return true or false
     */
    private boolean validateTeamManager(User newTeamManager) {
        if ((newTeamManager instanceof TeamManager || newTeamManager instanceof TeamOwner)){
            logger.logError("Fault: unable to validate team manager");
            return false;
        }
        return true;
    }

    /**
     * This function removes a user from being a team manager by the team owner
     * @param teamManagerName
=     * @param removedByName
     * @return true or false
     */
    public boolean removeTeamManager(String teamName, String teamManagerName, String removedByName) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        User teamManager = retrieveUser(teamManagerName);
        User removedBy = retrieveUser(removedByName);
        //checks if the user manages the team and that the removing user is the owner of the team and nominated him
        if (validateTeamManager(teamName, teamManager, removedBy) && ownsTeam(teamName, removedBy)) {
            team.removeTeamManager((TeamManager)teamManager);
            ((TeamManager)teamManager).removeTeam();
            //get the inner user (previous role)
            teamManager = ((TeamManager) teamManager).getUser();

            //remove from db
            String username = ((TeamManager)teamManager).getUserName();
            UsersCRUD.deleteRole(username, "team_manager");
            TeamCRUD.removeManagerFromTeam(username, team.getName());

            logger.logEvent("User: " + ((Role)removedBy).getUserName() + ". Removed team manager from " + team.getName() + " team.");
        }
        return true;
    }

    /**
     * This function verify whether the user is the team manager of the team,
     * and that the team owner that nominated him is the same user trying to remove him
     * @param teamManager
     * @param removedBy
     * @return true or false
     */
    private boolean validateTeamManager(String teamName, User teamManager, User removedBy) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        return teamManager instanceof TeamManager && ((TeamManager)teamManager).getTeam() == team &&
                removedBy instanceof TeamOwner && ((TeamManager)teamManager).getNominatedBy() == removedBy;
    }

     /** The function receives a team owner, the team and the nominated user and removes the owner from the team owners
     * if the user authorized to do so
     * @param teamOwnerName
     * @param nominatedByName
     * @return true or false
     */
    public boolean removeTeamOwner(String teamName, String teamOwnerName, String nominatedByName) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        User teamOwner = retrieveUser(teamOwnerName);
        User nominatedBy = retrieveUser(nominatedByName);

        //check if the user is owns the team and that the nominate is the owner of the team and nominated this user
        if (validateTeamOwner(teamName, teamOwner, nominatedBy) && ownsTeam(teamName, nominatedBy)) {
            teamOwner = removeTeamOwnerAndNominees(teamName, teamOwner);
            //remove from db
            String username = ((TeamOwner)teamOwner).getUserName();
            UsersCRUD.deleteRole(username, "team_owner");
            TeamCRUD.removeOwnerFromTeam(username, team.getName());
            return true;
        }

        else {
            System.out.println("The user is unauthorized to remove the team owner");
            return false;
        }
    }

    /**
     * The function receives a team owner and a team and removes the team owner and all his nominees
     * @param teamOwner
     */
    private User removeTeamOwnerAndNominees(String teamName, User teamOwner) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return null;

        List<TeamOwner> nominees = getUserNominees(teamName, teamOwner);
        team.removeTeamOwner((TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).removeTeam();
        teamOwner = ((TeamOwner) teamOwner).getUser();

        for (User nominee: nominees) {
            removeTeamOwnerAndNominees(teamName, nominee);
        }
        return teamOwner;
    }

    /**
     * The function receives a team owner and a team and returns the team owners that nominated by this team owner
     * @param teamOwner
     * @return
     */
    private List<TeamOwner> getUserNominees(String teamName, User teamOwner) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return null;

        List<TeamOwner> nominees = new LinkedList<>();
        if (team == null)
            return nominees;

        List<TeamOwner> teamOwners = team.getOwners();
        for (TeamOwner owner: teamOwners) {
            String nominatedName = ((TeamOwner)owner.getNominatedBy()).getUserName();
            String ownerName = ((TeamOwner)teamOwner).getUserName();
            if (owner.getNominatedBy() != null && UsersCRUD.getUserIdByUserName(nominatedName) == UsersCRUD.getUserIdByUserName(ownerName)) {
                nominees.add(owner);
            }
        }
        return nominees;
    }

    /**
     * The function returns true if the user is the owner of the team and if the nominated is the user
     * that nominates him to be the team's owner
     * @param teamOwner
     * @param nominatedBy
     * @return true or false
     */
    private boolean validateTeamOwner(String teamName, User teamOwner, User nominatedBy) {
        Team team = retrieveTeam(teamName);
        if (teamOwner == null || team == null || nominatedBy == null)
            return false;

        return teamOwner instanceof TeamOwner && ((TeamOwner)teamOwner).getTeam() == team &&
                ((TeamOwner)teamOwner).getNominatedBy() == nominatedBy;
    }

    /**
     * The function returns true if the user can be the team owner (he's a player / coach / manager of the team)
     * @param user
     * @return
     */
    private boolean validateUserCanOwnTeam(String teamName, User user) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;

        boolean valid = false;

        // checks that the user doesn't owns other teams
        if (!(user instanceof TeamOwner)) {
            if (user instanceof Player && ((Player)user).getTeam() == team) {
                valid = true;
            }

            else if (user instanceof Coach && ((Coach)user).getTeam() == team) {
                valid = true;
            }

            else if (user instanceof TeamManager && ((TeamManager)user).getTeam() == team) {
                valid = true;
            }

        }

        return valid;
    }

    /**
     * The function returns true if the user is the owner of the team, otherwise returns false
     * @param nominatedBy
     * @return true - if the user is the team owner, otherwise - false
     */
    private boolean ownsTeam(String teamName, User nominatedBy) {

        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        if (nominatedBy != null)
            return nominatedBy instanceof TeamOwner && ((TeamOwner) nominatedBy).getTeam()==team;
        return false;
    }

    /**
     * The function returns true if the user is the manages of the team, otherwise returns false
     * @param user
     * @return true - if the user is the team manager, otherwise - false
     */
    private boolean managesTeam(String teamName, User user) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        return user instanceof TeamManager && ((TeamManager) user).getTeam() == team;
    }

    /**
     * The function receives the parameters of new transaction and the team and add it if it's legal -
     * not exceed the budget of the team
     * @param transDetails
     * @return true or false
     */
    public boolean addTransactionToTeam(String teamName, JSONObject transDetails, String teamOwnerName) {
        Team team = retrieveTeam(teamName);
        if (team==null)
            return false;
        User teamOwner = retrieveUser(teamOwnerName);

        if (ownsTeam(teamName, teamOwner)) {
            //first amount, second type, third date and last description
            double amount = Double.parseDouble(transDetails.get("amount").toString());
            TransactionType type = convertStringToTrasactionType(transDetails.get("type").toString());
            LocalDate date = LocalDate.parse(transDetails.get("date").toString());
            String description = transDetails.get("description").toString();

            //create the transaction
            Transaction transaction = new Transaction(amount, type, date, team, description, (TeamOwner) teamOwner);

            boolean isTransAdded = team.addTransactionToTeam(transaction);
            if (!isTransAdded) {
                System.out.println("The transaction could not be added to the team, it exceeded the team's budget.");
            }else{
                logger.logEvent("User: " + ((Role)teamOwner).getUserName() + ". Added transaction tp " + team.getName() + " team.");
                TransactionCRUD.addTransactionToDB(teamName, amount, transDetails.get("type").toString(), teamOwnerName, transDetails.get("date").toString(), transDetails.get("description").toString());
            }

            return true;
        }
        else {
            logger.logError("Fault: unable to add: user not authorized to add a transaction to this team");
            return false;
        }
    }

    /**
     * The function converts string to transaction type
     * @param type
     * @return
     */
    private TransactionType convertStringToTrasactionType(String type){
        switch (type) {
            case "Expense":
                return TransactionType.EXPENSE;
            case "Income":
                return TransactionType.INCOME;
            default:
                logger.logError("Fault: unable to convert: transaction type does not exist");
                return null;
        }
    }

    /**
     * The function receives a team and a team owner and closes this team and returns true if it succeeded,
     * otherwise returns false
     * @param teamOwnerName
     * @return true or false
     */
    public boolean closeTeam(String teamName, String teamOwnerName) {
        User teamOwner =retrieveUser(teamOwnerName);
        Team team = retrieveTeam(teamName);
        if (teamOwner == null || team == null){
            return false;
        }
        if (ownsTeam(teamName, teamOwner)) {
            if (TeamCRUD.isTeamActive(team.getName())) {
                TeamCRUD.setTeamActivity(team.getName(), false);
                sendNotificationClose(team.getName(), "closed");
                logger.logEvent("User: " + ((Role)teamOwner).getUserName() + ". Closed " + team.getName() + " team.");
                return true;
            }
            logger.logError("Fault: unable to close: the team is already closed");
            return false;
        }

        else {
            logger.logError("Fault: unable to close: user not authorized to close this team");
            return false;
        }
    }

    /**
     * The function receives a team and a team owner and opens this team and returns true if it succeeded,
     * otherwise returns false
=    * @param teamOwnerName
     * @param teamName
     */
    public boolean openTeam(String teamName, String teamOwnerName) {
        User teamOwner =retrieveUser(teamOwnerName);
        Team team = retrieveTeam(teamName);
        if (teamOwner == null || team == null){
            return false;
        }
        if (ownsTeam(teamName, teamOwner)) {
            if (!TeamCRUD.isTeamActive(team.getName())) {
                TeamCRUD.setTeamActivity(team.getName(), true);
                sendNotificationClose(team.getName(), "opened");
                logger.logEvent("User: " + ((Role)teamOwner).getUserName() + ".Opened " + team.getName() + " team.");
                return true;
            }
            else {
                logger.logError("Fault: unable to open: the team is already open");
                return false;
            }
        }
        else {
            logger.logError("Fault: unable to open: user not authorized to open this team");
            return false;
        }
    }

    /**
     * The function receives a team that has been closed and send the message to all the team management
     * and the system admins
     * @param teamName
     */
    //TODO: add system admins from DB
    private void sendNotificationClose(String teamName, String message) {
        String close = "The team " + teamName + " has been " + message + ".";

        //get all the management of the team
        List<User> management = new LinkedList<>();
//        management.addAll(team.getOwners());
//        User manager = team.getManager();
//        if (manager != null) {
//            management.add(manager);
//        }
//
//        //add the system admins from DB
//
//        for (User user: management) {
//            ((Role)user).getNotification(close);
//        }
        //TODO ask yaar if to keep this

    }

    /**
     * This function checks if the received user is authorized to add /remove asset
     * @param user
     * @return true or false
     */
    private boolean canAddRemoveAsset(User user){
        return user.getMyPrivileges().contains("add/removeA");
    }

    /**
     * This function returns true if the user can edit the team page, otherwise false
     * @param username
     * @param teamName
     * @return true or false
     */
    public boolean enterEditingMode(String teamName, String username){
        User user =retrieveUser(username);
        Team team = retrieveTeam(teamName);
        if (user == null || team == null){
            return false;
        }
        return (user instanceof TeamOwner && ownsTeam(teamName, user) || user instanceof TeamManager && managesTeam(teamName, user));

    }

    /**
     * The function returns a string of the optional nominees for team owner
     * @param teamName
     */
    public JSONArray showOptionalNomineesForTeamOwner(String teamName) {
        JSONArray optionalNominees = new JSONArray();
        Team team = retrieveTeam(teamName);
        if (team == null){
            return optionalNominees;
        }
        List<User> nominees = team.getOptionalNomineesForTeamOwner();
        for (User nominee : nominees) {
            optionalNominees.put(((Role) nominee).getFullName());
        }
        return optionalNominees;
    }

    /**
     * The function returns true of the user can add and remove team owner
     * @param user
     * @return true or false
     */
    public boolean canAddRemoveTeamOwner(User user) {
        return user.getMyPrivileges().contains("add/removeTO");
    }

    /**
     * The function returns true of the user can add and remove team manager
     * @param user
     * @return true or false
     */
    public boolean canAddRemoveTeamManager(User user) {
        return user.getMyPrivileges().contains("add/removeTM");
    }

    /**
     * The function receives a name of user and returns the user if it's an optional team owner
     * @param fullName
     * @param teamName
     * @return user
     */
    public User getUserForTeamOwnerNominate(String teamName, String fullName) {
        Team team = retrieveTeam(teamName);
        if (team != null)
            return team.getUserForTeamOwner(fullName);
        logger.logError("Fault: unable to get: the team does not exist");
        return null;
    }


    /**
     * The function returns a string of the owners of the team
     */
    public JSONArray showTeamOwners(String teamName) {
        JSONArray owners = new JSONArray();
        Team team = retrieveTeam(teamName);
        if (team == null)
            return owners;
        List<TeamOwner> teamOwners = team.getOwners();
        for (TeamOwner owner : teamOwners) {
            owners.put(owner.getFullName());
        }
        return owners;
    }

    /**
     * The function receives a name of user and returns the team owner user if it exists,
     * otherwise return null
     * @param fullName
     * @return
     */
    public User getTeamOwnerUserByName(String teamName, String fullName) {
        if (teamName != null && fullName != null) {
            Team team = retrieveTeam(teamName);
            User user= team.getTeamOwnerByFullName(fullName);
            if (user!=null)
                return user;
        }
        logger.logError("Fault: unable to get: the team or owner does not exist");
        return null;
    }

    /**
     * The function returns true if the user can open and close the team, otherwise returns false
     * @param user
     * @return
     */
    public boolean canCloseOpenTeam(User user) {
        return user.getMyPrivileges().contains("closeTNP");
    }

     /**
     * This function returns all the team assets
     * @return
     */
    public JSONObject getAllTeamAssets(String teamName){
        Team team = retrieveTeam(teamName);
        if (team ==null)
            return new JSONObject();
        return team.getAllAssets();
    }

    /**
     * This function returns the team asset by type and name
     * @param type
     * @param name
     * @return
     */
    public TeamAsset getAssetByNameAndType(String teamName, String type, String name) {
        Team team = retrieveTeam(teamName);
        if (team!=null)
            return team.getAssetByNameAndType(type, name);
        logger.logError("Fault: unable to get: the team does not exist");
        return null;
    }

    /**
     * The function returns true if the user can add new transaction, otherwise returns false
     * @param user
     * @return
     */
    public boolean canAddTransaction(User user) {
        return user.getMyPrivileges().contains("addTrans");
    }


    /**
     * The function returns a string of the optional nominees for team manager
     */
    public JSONArray showOptionalNomineesForTeamManager(String teamName) {
        JSONArray optionalNominees= new JSONArray();
        Team team = retrieveTeam(teamName);
        if (team==null)
            return optionalNominees;
        List<User> nominees = team.getOptionalNomineesForTeamManager();
        for (User optionalUser : nominees) {
            optionalNominees.put(((Role) optionalUser).getFullName());
        }
        return optionalNominees;
    }

    /**
     * This function return a user with the full name 'name' from the team 'team'
     * @param name
     * @param teamName
     * @return
     */
    public User getUserForTeamManagerNominees(String teamName, String name) {
        Team team = retrieveTeam(teamName);
        if (team != null)
            return team.getUserForTeamManager(name);
        logger.logError("Fault: unable to get: the team does not exist");
        return null;
    }

    /**
     * This function enables the team owner to create a new team
     * @Return the new team
     */
    public Team createANewTeam(String teamOwnerName, String teamName){
        User teamOwner = retrieveUser(teamOwnerName);
        if (teamOwner != null && teamOwner instanceof TeamOwner){
            Team team = new Team(teamName, (TeamOwner)teamOwner);
            TeamCRUD.postTeam(teamName);
            return team;
        }
        logger.logError("Fault: unable to create: the team owner does not exist or not a team owner");
        return null;
    }

    /**
     * This function applies the confirmation or rejection of new team
     * @param teamName
     * @param representativeName
     * @param confirmed
     */
    public boolean commitConfirmationOfTeam(String teamName, String representativeName, boolean confirmed) {
        User representative = retrieveUser(representativeName);
        if (teamName != null && !teamName.trim().isEmpty()) {
            Team team = retrieveTeam(teamName);
            if (team!=null && representative != null && representative instanceof AssociationRepresentative) {
                if (!confirmed) {
                    for (TeamOwner owner : team.getOwners())
                        owner.getNotification("Your team registration request for " + teamName + " was rejected");
                    TeamCRUD.removeTeam(team.getName());
                } else {
                    team.registerTeam();
                    TeamCRUD.setTeamActivity(team.getName(), true);
                    TeamCRUD.setTeamRegistration(team.getName(), true);
                    for (TeamOwner owner : team.getOwners())
                        owner.getNotification("Your team registration request for " + teamName + " was approved");
                }
            }
            return true;
        }
        logger.logError("Fault: unable to commit: the team name is empty");
        return false;
    }

    /**
     * This function validates a single param
     * @param param
     * @return
     */
    private boolean validParam(String param){
        return (param!=null || (param.trim().isEmpty()));
    }

    /**
     * The function returns the teams
     * @return
     */
    public JSONArray getTeams() {
        List<String> teams = TeamCRUD.getTeams();
        return new JSONArray(teams);
    }
}

