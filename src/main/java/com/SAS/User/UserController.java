package com.SAS.User;

import java.util.HashSet;

public class UserController {

    /**
     * singleton Privileges instance
     */
    private Privileges globalPrivileges;

    /**
     * Constructor
     */
    public UserController() {
        this.globalPrivileges = Privileges.getInstance();
    }

    /**
     * The function receives userName, password, fullName and user type and creates a user
     * @param userName
     * @param password
     * @param fullName
     */
    public User createUser(String userName, String password, String fullName, UserType type, boolean approval){
        User newUser = new Registered(userName, password, fullName);
         if (validateUser(fullName, type)) {

             switch (type) {
                 case FAN:
                     newUser = new Fan(newUser, fullName);
                     break;

                 case PLAYER:
                         newUser = new Player(newUser, fullName);
                         break;

                 case COACH:
                         newUser = new Coach(newUser, fullName);
                         break;

                 case REFEREE:
                         newUser = new Referee(newUser, fullName);
                         break;

                 case TEAM_OWNER:
                         newUser = new TeamOwner(newUser, fullName);
                         break;

                 case TEAM_MANAGER:
                         newUser = new TeamManager(newUser, fullName);
                         break;

                 case SYSTEM_ADMIN:
                         newUser = new SystemAdmin(newUser, fullName);
                         break;

                 case ASSOCIATION_REPRESENTATIVE:
                         newUser = new AssociationRepresentative(newUser, fullName);
                         break;

             }

             setPrivilegesforUser(newUser, ((Role) newUser).getRole(), approval);
             return newUser;
         }

         else {
             sendMessageUnauthorized();
             return null;
         }

        //keep in database
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
        if (user == null) {
            return null;
        }

        String fullName = ((Role)user).getFullName();

        if (validateUser(fullName, type)) {

            switch (type) {

                case PLAYER:
                        user = new Player(user, fullName);
                        break;

                case COACH:
                        user = new Coach(user, fullName);
                        break;

                case TEAM_OWNER:
                        user = new TeamOwner(user, fullName);
                        break;

                case TEAM_MANAGER:
                        user = new TeamManager(user, fullName);
                        break;

            }

            setPrivilegesforUser(user, ((Role)user).getRole(), approval);
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
    private boolean verifyInDB(String fullName) {
        return true;
    }

    /**
     * The function sets the privileges of the user
     * @param user
     * @param role
     * @param approval
     */
    public void setPrivilegesforUser(User user, String role, boolean  approval) {
        user.setPrivileges(globalPrivileges.getPrivileges(role, approval));
    }
}
