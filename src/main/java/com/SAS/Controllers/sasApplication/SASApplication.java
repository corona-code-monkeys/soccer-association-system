/**
 * This class represents the external controller of the system
 */
package com.SAS.Controllers.sasApplication;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.LeagueManagement.LeagueManagementController;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamManagement;

public class SASApplication {

    private UserController userController;
    private LeagueManagementController leaugeManagement;
    private TeamManagement teamManagement;
    private UsersCRUD usersCRUD;
    private SystemController systemController; //check


    /**
     * Empty Constructor, initializes the other controllers
     */
    public SASApplication() {
        userController= new UserController();
        leaugeManagement= new LeagueManagementController();
        teamManagement= new TeamManagement(userController);
        usersCRUD = new UsersCRUD();
    }

    //TODO: in UI : if return true switch to home page with correct privileges, else show alert that user doesn't exist
    /**
     * This function logs in the user
     * @param username
     * @param password
     * @return true if the user exists in the system, thus was logged in, otherwise false
     */

    public boolean login(String username, String password){
       return usersCRUD.isUserValid(username, password);
    }

    //TODO: In UI: if true- show alert that the user was created and switch to home page so he would log in, wlse show error message
    /**
     * This function calls the creation of a user using the userController
     * @param userName
     * @param password
     * @param fullName
     * @param type
     * @param approval
     * @param newUser
     * @return true if was created, otherwise false
     */
    public boolean createUser(String userName, String password, String fullName, UserType type, boolean approval, User newUser){
        if(userController.createUser(userName,password,fullName,type, approval, newUser)!=null)
            return true;
        return false;
    }

    //TODO: UI- if true, show alert that the request was sent to the association
    /**
     * This function registers the team
     * @param teamOwner
     * @param teamName
     */
    public boolean registerTeam(User teamOwner, String teamName){
        return userController.sendNotificationToRepresentative(teamManagement.createANewTeam(teamOwner, teamName));
    }


    //TODO: UI- if true, show alert that a notification about the team registration was sent to its owner
    /**
     * This function applies the confirmation/denial of the team
     * @param teamName
     * @param representative
     * @param confirm
     */
    public boolean confirmTeam(String teamName, User representative, boolean confirm){
        return teamManagement.commitConfirmationOfTeam(teamName, representative, confirm);
    }






}
