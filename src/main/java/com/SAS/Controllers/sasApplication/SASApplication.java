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

    /**
     * This function logs in the user
     * @param username
     * @param password
     * @return true if the user exists in the system, thus was logged in, otherwise false
     */
    public boolean login(String username, String password){
        if (usersCRUD.isUserValid(username, password)){
            //TODO: switch to home page with correct privileges
            return true;
        }
        else{
            //TODO: show message that the user does not exist
            return false;
        }
    }

    /**
     * This function calls the creation of a user using the userController
     * @param userName
     * @param password
     * @param fullName
     * @param type
     * @param approval
     * @param newUser
     * @return the created user
     */
    public User createUser(String userName, String password, String fullName, UserType type, boolean approval, User newUser){
        return userController.createUser(userName,password,fullName,type, approval, newUser);
    }
}
