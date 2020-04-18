package com.SAS.User;

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
     * To finish implementation!
     * @param userName
     * @param password
     * @param fullName
     */
    public void createUser(String userName, String password, String fullName, UserType type){
        User newUser = new Registered(userName, password, fullName);

        switch (type) {
            case FAN:
                newUser = new Fan(newUser);
                setPrivilegesforUser(newUser, "Fan", true);
                break;

            case PLAYER:
                if (verifyInDB(fullName) || verifyByAssociationRep(fullName)) {
                    newUser = new Player(newUser);
                    setPrivilegesforUser(newUser, "Player", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case COACH:
                if (verifyByAssociationRep(fullName)) {
                    newUser = new Coach(newUser);
                    setPrivilegesforUser(newUser, "Coach", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case REFEREE:
                if (verifyByAssociationRep(fullName)) {
                    newUser = new Referee(newUser);
                    setPrivilegesforUser(newUser, "Referee", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case TEAM_OWNER:
                if (verifyInDB(fullName) || verifyByAssociationRep(fullName)) {
                    newUser = new TeamOwner(newUser);
                    setPrivilegesforUser(newUser, "TeamOwner", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case TEAM_MANAGER:
                if (verifyByAssociationRep(fullName)) {
                    newUser = new TeamManager(newUser);
                    setPrivilegesforUser(newUser, "TeamManager", getTeamOwnerApproval(userName));
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case SYSTEM_ADMIN:
                if (verifyByAssociationRep(fullName)) {
                    newUser = new SystemAdmin(newUser);
                    setPrivilegesforUser(newUser, "SystemAdmin", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

            case ASSOCIATION_REPRESENTATIVE:
                if (verifyBySystemAdmin(fullName)) {
                    newUser = new AssociationRepresentative(newUser);
                    setPrivilegesforUser(newUser, "AssociationRepresentative", true);
                    break;
                }

                else {
                    sendMessageUnautorized();
                }

                break;

        }

        //keep in database

    }

    //TO-DO
    private boolean getTeamOwnerApproval(String usenName) {
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
    private void sendMessageUnautorized() {
    }

    //TO-DO
    private boolean verifyInDB(String fullName) {
        return true;
    }

    //did not call this yet!
    public void setPrivilegesforUser(User user, String role, boolean  approval) {
        user.setPrivileges(globalPrivileges.getPrivileges(role, approval));
    }
}
