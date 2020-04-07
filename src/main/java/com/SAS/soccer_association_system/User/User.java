/**
 * The class represents a user in the system
 */

package com.SAS.soccer_association_system.User;

public abstract class User {

    public static int counter = 0;

    private int userID;

    /**
     * constructor
     */
    public User() {
        this.userID = counter++;
    }

    /**
     * The function returns the uderID
     * @return userID - int
     */
    public int getUserID() {
        return userID;
    }
}
