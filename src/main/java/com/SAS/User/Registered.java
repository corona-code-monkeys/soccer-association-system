/**
 * The class represents a registered user
 */

package com.SAS.User;

public class Registered extends User {

    private String userName;
    private String password;
    private String fullName;


    /**
     * Constructor
     * @param userName
     * @param password
     * @param fullName
     */
    public Registered(String userName, String password, String fullName) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    /**
     * The function returns the user name
     * @return userName - String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The function returns the full name of the user
     * @return fullName - String
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * The function sets the password of the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The function sets the full name of the user
     * @param fullName - String
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
