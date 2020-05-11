/**
 * Thr class represents the management of the team
 */
package com.SAS.teamManagenemt;

import com.SAS.User.*;
import com.SAS.crudoperations.CRUD;
import com.SAS.crudoperations.TeamCRUD;
import com.SAS.facility.*;
import com.SAS.systemLoggers.LoggerFactory;
import com.SAS.team.Team;

import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;

import java.time.LocalDate;
import java.util.List;

import java.util.LinkedList;

public class TeamManagement {

    private LoggerFactory logger;
    private UserController userController;
    private Team team;

    /**
     * Constructor
     * @param userController
     */
    public TeamManagement(UserController userController) {
        this.userController = userController;
        logger = LoggerFactory.getInstance();
    }

    /**
     * The function receives a team name and enter to the team page if exists and returns true,
     * otherwise returns false
     * @param teamName
     * @return true or false
     */
    public boolean enterTeamPage(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            logger.logError("Fault: unable to get the team page - team name is empty");
            return false;
        }
        Team getTeam = TeamCRUD.getTeamByName(teamName);
        if (getTeam == null) {
            logger.logError("Fault: unable to get the team - team doesn't exist");
            return false;
        }

        team = getTeam;
        return true;
    }

    /**
     * This function allows the team owner to add an asset to the team. Assets are facility, player, coach.
     * @param assetType
     * @return the created asset
     */
    public TeamAsset AddAssetToTeam (String assetType, User user) {
        if (! canAddRemoveAsset(user)){
            logger.logError("Fault: unable to add: user not authorized to add an asset to this team");
            return null;
        }

        TeamAsset asset = null;
        //create the asset
        switch (assetType) {
            case "Player":
                asset = (Player) userController.createUser(null, null, null, UserType.PLAYER, true, null);
                team.addPlayerToTeam((Player)asset);
                logger.logEvent("User: " + ((Role)user).getUserName() + ". Added player to " + team.getName() + " team.");
                break;
            case "Facility":
                asset = new Facility();
                team.addFacility((Facility)asset);
                logger.logEvent("User: " + ((Role)user).getUserName() + ". Added facility to " + team.getName() + " team.");
                break;
            case "Coach":
                asset = (Coach) userController.createUser(null, null, null, UserType.COACH, true, null);
                team.setCoach((Coach)asset);
                logger.logEvent("User: " + ((Role)user).getUserName() + ". Added coach to " + team.getName() + " team.");
                break;
        }
        asset.setTeam(team);
        return asset;
    }

    /**
     * This function receives a list of details and a teamAsset and updates the asset's details
     * @param asset
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     * If return false should ask the user to enter the details again
     */
    public boolean editAssetDetails(TeamAsset asset, List<String> details)
    {
        return asset.editDetails(details);
    }

    /**
     * This function removes a team asset
     * @param asset
     * @param user
     */
    public boolean removeAsset(TeamAsset asset, User user){
        if (canAddRemoveAsset(user) && (ownsTeam(user) || managesTeam(user))) {
            asset.removeAssetFromTeam();
            asset = null;
            logger.logEvent("User: " + ((Role)user).getUserName() + ". Removed asset from " + team.getName() + " team.");
            return true;
        }
        else{
            logger.logError("Fault: unable to remove: the asset is not on te possession of the team");
            return false;
        }
    }

    /**
     * The function receives a user and a team and adds the user to the owners of the team
     * in case he's not already a team owner
     * @param newTeamOwner
     * @param nominatedBy
     */
    public User addAdditionalTeamOwner(User newTeamOwner, User nominatedBy) {
        //check if the user can owns the team and that the nominate is the owner of the team
        if (validateUserCanOwnTeam(newTeamOwner) && ownsTeam(nominatedBy)) {
            newTeamOwner = userController.addRoleToUser(newTeamOwner, UserType.TEAM_OWNER, true);
            team.addTeamOwner((TeamOwner)newTeamOwner);
            ((TeamOwner) newTeamOwner).setTeam(team);
            ((TeamOwner) newTeamOwner).setNominatedBy((TeamOwner)nominatedBy);
            logger.logEvent("User: " + ((Role)nominatedBy).getUserName() + ". Added team owner to " + team.getName() + " team.");
        }

        else {
            System.out.println("The user is unauthorized to own the team");
        }

        return newTeamOwner;
    }

    /**
     * The function receives a user and a team and adds the user as the team manager
     * TODO: UI will need to popup a window to get approval
     * @param newTeamManager
     * @param nominatedBy
     * @param approval - a boolean which determines if the manager got privileges from the team owner
     * @return newTeamManager - the team manager
     */
    public User addTeamManager(User newTeamManager, User nominatedBy, boolean approval){
        //check if the team already has a manager
        if(teamHasManager()){
            System.out.println("The team already has a manager, please remove it first to nominate a new manager");
            return newTeamManager;
        }
        //check if the user can manage this team and that the user that nominates him is the team owner
        if (validateTeamManager(newTeamManager) && ownsTeam(nominatedBy)){
            newTeamManager = userController.addRoleToUser(newTeamManager, UserType.TEAM_MANAGER,approval);
            team.setTeamManager((TeamManager)newTeamManager);
            ((TeamManager)newTeamManager).setTeam(team);
            ((TeamManager)newTeamManager).setNominatedBy((TeamOwner)nominatedBy);
            logger.logEvent("User: " + ((Role)nominatedBy).getUserName() + ". Added team manager to " + team.getName() + " team.");
        }
        else{
            logger.logError("Fault: unable to add: user not authorized to add an asset to this team");
        }
        return newTeamManager;
    }

    /**
     * This function checks if the team already has a manager, if so returns true, otherwise returns false
     * @return true or false
     */
    private boolean teamHasManager() {
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
     * @param teamManager
=     * @param removedBy
     * @return
     */
    public User removeTeamManager(User teamManager, User removedBy) {
        //checks if the user manages the team and that the removing user is the owner of the team and nominated him
        if (validateTeamManager(teamManager, removedBy) && ownsTeam(removedBy)) {
            team.removeTeamManager((TeamManager)teamManager);
            ((TeamManager)teamManager).removeTeam();
            //get the inner user (previous role)
            teamManager = ((TeamManager) teamManager).getUser();
            logger.logEvent("User: " + ((Role)removedBy).getUserName() + ". Removed team manager from " + team.getName() + " team.");
        }

        return teamManager;
    }

    /**
     * This function verify whether the user is the team manager of the team,
     * and that the team owner that nominated him is the same user trying to remove him
     * @param teamManager
     * @param removedBy
     * @return true or false
     */
    private boolean validateTeamManager(User teamManager, User removedBy) {
        return teamManager instanceof TeamManager && ((TeamManager)teamManager).getTeam() == team &&
                removedBy instanceof TeamOwner && ((TeamManager)teamManager).getNominatedBy() == removedBy;
    }

     /** The function receives a team owner, the team and the nominated user and removes the owner from the team owners
     * if the user authorized to do so
     * @param teamOwner
     * @param nominatedBy
     * @return user
     */
    public User removeTeamOwner(User teamOwner, User nominatedBy) {
        //check if the user is owns the team and that the nominate is the owner of the team and nominated this user
        if (validateTeamOwner(teamOwner, nominatedBy) && ownsTeam(nominatedBy)) {
            teamOwner = removeTeamOwnerAndNominees(teamOwner);
        }

        else {
            System.out.println("The user is unauthorized to remove the team owner");
        }

        return teamOwner;
    }

    /**
     * The function receives a team owner and a team and removes the team owner and all his nominees
     * @param teamOwner
     */
    private User removeTeamOwnerAndNominees(User teamOwner) {
        if (team == null || teamOwner == null)
            return null;

        List<TeamOwner> nominees = getUserNominees(teamOwner);
        team.removeTeamOwner((TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).removeTeam();
        teamOwner = ((TeamOwner) teamOwner).getUser();

        for (User nominee: nominees) {
            removeTeamOwnerAndNominees(nominee);
        }

        return teamOwner;

    }

    /**
     * The function receives a team owner and a team and returns the team owners that nominated by this team owner
     * @param teamOwner
     * @return
     */
    private List<TeamOwner> getUserNominees(User teamOwner) {
        List<TeamOwner> nominees = new LinkedList<>();
        if (team == null)
            return nominees;

        List<TeamOwner> teamOwners = team.getOwners();
        for (TeamOwner owner: teamOwners) {
            if (owner.getNominatedBy() != null && owner.getNominatedBy().getUserID() == teamOwner.getUserID()) {
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
    private boolean validateTeamOwner(User teamOwner, User nominatedBy) {
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
    private boolean validateUserCanOwnTeam(User user) {
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
    private boolean ownsTeam(User nominatedBy) {
        if (team != null && nominatedBy != null)
            return nominatedBy instanceof TeamOwner && ((TeamOwner) nominatedBy).getTeam()==team;
        return false;
    }

    /**
     * The function returns true if the user is the manages of the team, otherwise returns false
     * @param user
     * @return true - if the user is the team manager, otherwise - false
     */
    private boolean managesTeam(User user) {
        return user instanceof TeamManager && ((TeamManager) user).getTeam() == team;
    }

    /**
     * The fcuntion receives the parameters of new transaction and the team and add it if it's legal -
     * not exceed the budget of the team
     * @param transDetails
     * @return
     */
    public Transaction addTransactionToTeam(List<String> transDetails, User teamOwner) {
        if (ownsTeam(teamOwner)) {
            //first amount, second type, third date and last description
            double amount = Double.parseDouble(transDetails.get(0));
            TransactionType type = convertStringToTrasactionType(transDetails.get(1));
            LocalDate date = LocalDate.parse(transDetails.get(2));
            String description = transDetails.get(3);

            //create the transaction
            Transaction transaction = new Transaction(amount, type, date, team, description, (TeamOwner) teamOwner);

            boolean isTransAdded = team.addTransactionToTeam(transaction);
            if (!isTransAdded) {
                System.out.println("The transaction could not be added to the team, it exceeded the team's budget.");
            }

            logger.logEvent("User: " + ((Role)teamOwner).getUserName() + ". Added transaction tp " + team.getName() + " team.");
            return transaction;
        }

        else {
            logger.logError("Fault: unable to add: user not authorized to add a transaction to this team");
            return null;
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
     * @param teamOwner
     * @return true or false
     */
    public boolean closeTeam(User teamOwner) {
        if (ownsTeam(teamOwner)) {
            if (TeamCRUD.isTeamActive(team.getName())) {
                TeamCRUD.inactivateTeam(team.getName());
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
=     * @param teamOwner
     */
    public boolean openTeam(User teamOwner) {
        if (ownsTeam(teamOwner)) {
            if (!TeamCRUD.isTeamActive(team.getName())) {
                TeamCRUD.reactivateTeam(team.getName());
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
     * This function chacks if the received user is authorized to add /remove asset
     * @param user
     * @return true or false
     */
    public boolean canAddRemoveAsset(User user){
        return user.getMyPrivileges().contains("add/removeA");
    }

    /**
     * This function returns true if the user can edit the team page, otherwise false
     * @param user
     * @param team
     * @return true or false
     */
    public boolean enterEditingMode(User user, Team team){
        return (user instanceof TeamOwner && ownsTeam(user) || user instanceof TeamManager && managesTeam(user));

    }

    /**
     * The function returns a string of the optional nominees for team owner
     * @param user
     * @param team
     */
    public String showOptionalNomineesForTeamOwner(User user, Team team) {
        StringBuilder builder = new StringBuilder();
        if (user !=null && team != null) {
            List<User> optionalNominees = team.getOptionalNomineesForTeamOwner();
            for (User optionalUser : optionalNominees) {
                builder.append("User name: " + ((Role) optionalUser).getFullName() + "\n");
            }
        }
        return builder.toString();
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
     * @param team
     * @return user
     */
    public User getUserForTeamOwnerNominate(String fullName, Team team) {
        if (team != null)
            return team.getUserForTeamOwner(fullName);
        logger.logError("Fault: unable to get: the team does not exist");
        return null;
    }


    /**
     * The function returns a string of the owners of the team
     */
    public String showTeamOwners() {
        StringBuilder builder = new StringBuilder();
        if (team != null) {
            List<TeamOwner> owners = team.getOwners();
            for (TeamOwner owner : owners) {
                builder.append(owner.getFullName() + "\n");
            }
        }
        return builder.toString();
    }

    /**
     * The function receives a name of user and returns the team owner user if it exists,
     * otherwise return null
     * @param fullName
     * @return
     */
    public User getTeamOwnerUserByName(String fullName) {
        if (team != null && fullName != null)
            return team.getTeamOwnerByFullName(fullName);
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
    public StringBuilder getAllTeamAssets(){
        StringBuilder assets = new StringBuilder();
        if (team != null) {
            for (TeamAsset asset : team.getAllAssets())
                assets.append(asset);
        }
        return assets;
    }

    /**
     * This function returns the team asset by type and name
     * @param type
     * @param name
     * @return
     */
    public TeamAsset getAssetByNameAndType(String type, String name) {
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
    public String showOptionalNomineesForTeamManager() {
        StringBuilder builder = new StringBuilder();
        if (team !=null) {
            List<User> optionalNominees = team.getOptionalNomineesForTeamManager();
            for (User optionalUser : optionalNominees) {
                builder.append("User name: " + ((Role) optionalUser).getFullName() + "\n");
            }
        }
        return builder.toString();
    }

    /**
     * This function return a user with the full name 'name' from the team 'team'
     * @param name
     * @return
     */
    public User getUserForTeamManagerNominees(String name) {
        if (team != null)
            return team.getUserForTeamManager(name);
        logger.logError("Fault: unable to get: the team does not exist");
        return null;
    }

    /**
     * This function enables the team owner to create a new team
     * @Return the new team
     */
    public Team createANewTeam(User teamOwner, String teamName){
        if (teamOwner != null && teamOwner instanceof TeamOwner){
            Team team = new Team(teamName, (TeamOwner)teamOwner);
            return team;
        }
        logger.logError("Fault: unable to create: the team owner does not exist or not a team owner");
        return null;
    }

    /**
     * This function applies the confirmation or rejection of new team
     * @param teamName
     * @param representative
     * @param confirmed
     */
    public boolean commitConfirmationOfTeam(String teamName, User representative, boolean confirmed) {
        if (teamName != null && !teamName.trim().isEmpty()) {
            Team team = CRUD.getTeamByName(teamName);
            if (representative != null && representative instanceof AssociationRepresentative) {
                if (!confirmed) {
                    for (TeamOwner owner : team.getOwners())
                        owner.getNotification("Your team registration request for " + teamName + " was rejected");
                    CRUD.removeTeam(team);
                } else {
                    team.registerTeam();
                    for (TeamOwner owner : team.getOwners())
                        owner.getNotification("Your team registration request for " + teamName + " was approved");

                }
            }
            return true;
        }
        logger.logError("Fault: unable to commit: the team name is empty");
        return false;
    }
}

