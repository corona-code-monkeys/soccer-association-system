package com.SAS.User;

import com.SAS.crudoperations.CRUD;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.team.Team;
import java.util.List;
import com.SAS.systemLoggers.LoggerFactory;

public class UserController {

    /**
     * singleton Privileges instance
     */
    private LoggerFactory logger;
    private Privileges globalPrivileges;

    /**
     * Constructor
     */
    public UserController() {
        this.globalPrivileges = Privileges.getInstance();
        this.logger = LoggerFactory.getInstance();

    }

    /**
     * The function returns new guest
     * @return
     */
    public User createGuest() {
        return new Guest();
    }

    /**
     * The function receives userName, password, fullName and user type and creates a user
     * @param userName
     * @param password
     * @param fullName
     */
    public User createUser(String userName, String password, String fullName, UserType type, boolean approval, User newUser){
         //check user parameters
         if (!validateDetails(userName, password, fullName, type)) {
             System.out.println("The parameters aren't valid. Pleas enter the details again.");
             return newUser;
         }

         //checks if the userName already exists
         if (CRUD.isUserNameExist(userName)) {
             System.out.println("The userName already exist. Please enter your details again.");
             return newUser;
         }

         newUser = new Registered(userName, password, fullName);
         if (validateUser(fullName, type)) {

             switch (type) {
                 case FAN:
                     newUser = new Fan(newUser, userName);
                     break;

                 case PLAYER:
                         newUser = new Player(newUser, userName);
                         break;

                 case COACH:
                         newUser = new Coach(newUser, userName);
                         break;

                 case REFEREE:
                         newUser = new Referee(newUser, userName);
                         break;

                 case TEAM_OWNER:
                         newUser = new TeamOwner(newUser, userName);
                         break;

                 case TEAM_MANAGER:
                         newUser = new TeamManager(newUser, userName);
                         break;

                 case SYSTEM_ADMIN:
                         newUser = new SystemAdmin(newUser, userName);
                         break;

                 case ASSOCIATION_REPRESENTATIVE:
                         newUser = new AssociationRepresentative(newUser, userName);
                         break;

             }

             setPrivilegesforUser(newUser, ((Role) newUser).getRole(), approval);
             //keep in database
             UsersCRUD.postUser(userName, password);
             try {
                 logger.logEvent("User: " + ((Role)newUser).getUserName() + ". New " + type + " Created.");
             }
             catch (Exception e){
                 System.out.println(e.getCause());
             }
             return newUser;
         }

         else {
             sendMessageUnauthorized();
             return null;
         }

    }

    /**
     * The method receives userName, password and full name as string and checks that they are valid
     *
     * @param userName
     * @param password
     * @param fullName
     */
    private boolean validateDetails(String userName, String password, String fullName, UserType type) {
        return  !(empty(userName) || empty(password)|| empty(fullName) || type == null);
    }


    /**
     * The function returns true if the param is null or empty, otherwise returns false
     * @param param
     * @return true or false
     */
    private boolean empty(String param) {
        return param == null || param.trim().isEmpty();
    }

    /**
     * The function returns true if the user is authorize, otherwise - returns false
     * @param fullName
     * @param type
     * @return
     */
    private boolean validateUser(String fullName, UserType type) {
        switch (type) {
            case FAN:
                return true;
            case PLAYER:
            case TEAM_OWNER:
                return verifyInDB(fullName) || verifyByAssociationRep(fullName);
            case COACH:
            case REFEREE:
            case TEAM_MANAGER:
            case SYSTEM_ADMIN:
                return verifyByAssociationRep(fullName);
            case ASSOCIATION_REPRESENTATIVE:
                return verifyBySystemAdmin(fullName);

        }

        return false;

    }

    /**
     * The function sets the privileges of the user
     * @param user
     * @param type
     * @return
     */
    public User addRoleToUser(User user, UserType type, boolean approval) {
        if (user == null || type == null) {
            return null;
        }

        String fullName = ((Role)user).getFullName();
        String userName = ((Role)user).getUserName();

        if (validateUser(fullName, type)) {

            switch (type) {

                case PLAYER:
                        user = new Player(user, userName);
                        break;

                case COACH:
                        user = new Coach(user, userName);
                        break;

                case TEAM_OWNER:
                        user = new TeamOwner(user, userName);
                        break;

                case TEAM_MANAGER:
                        user = new TeamManager(user, userName);
                        break;

            }

            setPrivilegesforUser(user, ((Role)user).getRole(), approval);
            logger.logEvent("User: " + ((Role)user).getUserName() + ". New " + type + " role has been added.");
            return user;
            //keep in database

        }

        else {
            sendMessageUnauthorized();
            return null;
        }

    }

    //TO-DO
    private boolean getTeamOwnerApproval(String userName) {
        return true;
    }

    //TO-DO
    private boolean verifyBySystemAdmin(String fullName) {
        return true;
    }

    //TO-DO
    private boolean verifyByAssociationRep(String fullName) {
        return true;
    }

    //TO-DO
    private void sendMessageUnauthorized() {

    }

    //TO-DO
    private boolean verifyInDB(String userName) {
        return true;
    }

    /**
     * The function sets the privileges of the user
     * @param user
     * @param role
     * @param approval
     */
    public void setPrivilegesforUser(User user, String role, boolean  approval) {
        if (user != null && !empty(role)) {
            user.setPrivileges(globalPrivileges.getPrivileges(role, approval));
        }
    }

    /**
     * This function send the notification to all representatives
     * @param aNewTeam
     * @Return true if a notification was sent, otherwise false
     */
    public boolean sendNotificationToRepresentative(Team aNewTeam) {
        if (aNewTeam != null) {
            List<AssociationRepresentative> representatives = CRUD.getAssociationRepresentatives();
            if (representatives.size()==0)
                return false;
            for (AssociationRepresentative rep : representatives) {
                rep.getNotification("The new team: " + aNewTeam.getName() + " is waiting to be registered");
            }
            return true;
        }
        return false;
    }
}
