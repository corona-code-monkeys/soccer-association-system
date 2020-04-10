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
    public void createUser(String userName, String password, String fullName){
        User newUser = new Registered(userName, password, fullName);
        //keep in database
        //set privileges???
    }

    //did not call this yet!
    public void setPrivilegesforUser(User user, String privilege, boolean  approval) {
        user.setPrivileges(globalPrivileges.getPrivileges(privilege, approval));
    }
}
