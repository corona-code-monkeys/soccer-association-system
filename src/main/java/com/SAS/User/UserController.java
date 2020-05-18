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
     * @param email
     * @param userType
     */
    public User createUser(String userName, String password, String fullName, String email, String userType, boolean approval){
        UserType type = null;
        if (userType != null) {
            type= Role.convertStringToUserType(userType);
        }
        User newUser = null;
        //check user parameters
         if (!validateDetails(userName, password, fullName, email, type)) {
             System.out.println("The parameters aren't valid. Pleas enter the details again.");
             return newUser;
         }

         //checks if the userName already exists
         if (CRUD.isUserNameExist(userName)) {
             System.out.println("The userName already exist. Please enter your details again.");
             return newUser;
         }

         newUser = new Registered(userName, password, fullName, email);
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

             try {
                 UsersCRUD.postUser(userName, password, fullName, email, userType);
                 logger.logEvent("User: " + ((Role)newUser).getUserName() + ". New " + type + " Created.");
             }
             catch (Exception e){
             }


             return newUser;
         }

         else {
             logger.logError("Fault: unable to get: unable to validate user");
             return null;
         }

    }

    /**
     * The method receives userName, password and full name as string and checks that they are valid
     *  @param userName
     * @param password
     * @param fullName
     * @param email
     */
    private boolean validateDetails(String userName, String password, String fullName, String email, UserType type) {
        return  !(empty(userName) || empty(password)|| empty(fullName) || empty(email) || type == null);
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
     * @param userType
     * @return
     */
    public User addRoleToUser(User user, String userType, boolean approval) {
        if (userType == null)
            return null;
        UserType type = Role.convertStringToUserType(userType);
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
            //save in DB
            UsersCRUD.addRoleToUser(userName, type.toString());
            logger.logEvent("User: " + ((Role)user).getUserName() + ". New " + type.toString() + " role has been added.");
            return user;
            //keep in database

        }

        else {
            logger.logError("Fault: unable to get: unable to validate user");
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
            if (representatives.size()==0) {
                logger.logError("Fault: unable to send: there are no representatives to send notifications");
                return false;
            }
            for (AssociationRepresentative rep : representatives) {
                rep.getNotification("The new team: " + aNewTeam.getName() + " is waiting to be registered");
            }
            return true;
        }
        logger.logError("Fault: unable to send: team does not exist");
        return false;
    }

    /**
     * This function deletes a user
     * @param username
     * @return true if the user was deleted, otherwise false
     */
    public boolean deleteUSer(String username){
        if (username==null || username.trim().isEmpty())
            return false;
        return UsersCRUD.deleteUser(username);
    }

    /**
     * This function deletes a user role
     * @param username
     * @return true if the user was deleted, otherwise false
     */
    public boolean deleteUserRole(String username, String role){
        if (username==null || username.trim().isEmpty())
            return false;
        return UsersCRUD.deleteRole(username, role);
    }




    /**
     * This function checks if the user exist in the DB
     * @param username
     * @param password
     * @return true if the user exists, otherwise false
     */
    public boolean isUserExist(String username, String password){
        if (validParam(username) && validParam(password)) {
            return UsersCRUD.isUserValid(username, password);
        }
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
     * This function edits the user details
     *
     * @param username
     * @param details
     * @param type
     * @return true if were edited, otherwise false
     */
    public boolean editUserDetails(String username, List<String> details, String type) {
        int userID = UsersCRUD.getUserIdByUserName(username);
        if (userID == -1)
            return false;
        switch (type) {
                case "PLAYER":
                  return editPlayerDetails(userID, details);
                case "TEAM_OWNER":
                    return editTeamOwnerDetails(userID, details);
                case "COACH":
                    return editCoachDetails(userID, details);
                case "REFEREE":
                    return editRefereeDetails(userID, details);
                case "TEAM_MANAGER":
                    return editTeamManagerDetails(userID, details);
                default:
                    return false;
            }
    }

    /**
     * This function sends the coach details to the DB
     * @param userID
     * @param details
     * @return
     */
    private boolean editCoachDetails(int userID, List<String> details) {
       //level, fieldRole, team
        if (details.size()==3 && validParam(details.get(0)) && validParam(details.get(1))&& validParam(details.get(2))){
            return UsersCRUD.setCoachDetails(userID, details.get(0), details.get(1), details.get(2));
        }
        else
            return false;
    }

    /**
     * This function sends the team manager details to the DB
     * @param userID
     * @param details
     * @return
     */
    private boolean editTeamManagerDetails(int userID, List<String> details) {
        //team, nominatedBy
        if (details.size()==2 && validParam(details.get(0)) && validParam(details.get(1))){
            return UsersCRUD.setTeamOwnerOrManagerDetails(userID, details.get(0), details.get(1), "team_manager");
        }
        else
            return false;
        }

    /**
     * This function sends the referee details to the DB
     * @param userID
     * @param details
     * @return
     */
    private boolean editRefereeDetails(int userID, List<String> details) {
        //level
        if (details.size()==1 && validParam(details.get(0))){
            return UsersCRUD.setRefereeDetails(userID, details.get(0));
        }
        else
            return false;
    }



    /**
     * This function send the team owner details to the DB
     * @param userID
     * @param details
     * @return
     */
    private boolean editTeamOwnerDetails(int userID, List<String> details) {
        //just team
        if (details.size()==1 && validParam(details.get(0))){
            return UsersCRUD.setTeamOwnerDetails(userID, details.get(0));
        }
        //team, nominatedBy
        else if (details.size()==2 && validParam(details.get(0)) && validParam(details.get(1))){
            return UsersCRUD.setTeamOwnerOrManagerDetails(userID, details.get(0), details.get(1), "team_owner");
        }
        else
            return false;
    }

    /**
     * This function edits the player details
     * @param userID
     * @param details
     * @return true or false
     */
    private boolean editPlayerDetails(int userID, List<String> details) {
        if (details.size() == 3 && validParam(details.get(0)) && validParam(details.get(1)) && validParam(details.get(2))) {
            //first is dateOfBirth, second is fieldRole
            return UsersCRUD.setPlayerDetails(userID, details.get(0), details.get(1), details.get(2));
        }
        return false;
    }
}
