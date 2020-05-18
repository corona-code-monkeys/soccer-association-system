/**
 * The class represents a registered user
 */

package com.SAS.User;

public class Registered extends User {

    private String userName;
    private String password;
    private String fullName;
    private String email;


    /**
     * Constructor
     * @param userName
     * @param password
     * @param fullName
     */
    public Registered(String userName, String password, String fullName, String email) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
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
     * @return true if success, otherwise false
     */
    public boolean setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.password = password;
            return true;
        }

        return false;
    }

    /**
     * The function sets the full name of the user
     * @param fullName - String
     */
    public void setFullName(String fullName) {
        if (fullName != null && !fullName.trim().isEmpty()) {
            this.fullName = fullName;
        }
    }

    /**
     * This function return the user email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * This function sets the user email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

