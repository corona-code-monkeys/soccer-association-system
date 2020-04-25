/**
 * The class represents a user in the system
 */

package com.SAS.User;

import java.util.HashSet;

public abstract class User {

    public static int counter = 0;
    protected HashSet<String> myPrivileges;
    private int userID;

    /**
     * constructor
     */
    public User() {
        this.userID = counter++;
        this.myPrivileges = new HashSet<>();
    }

    /**
     * The function returns the userID
     * @return userID - int
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The function edits the privileges of the user
     * @param privileges- privileges to add to myPrivileges
     */
    public void setPrivileges(HashSet<String> privileges) {
        if (privileges != null) {
            this.myPrivileges.addAll(privileges);
        }
    }

    /**
     * The function returns all the privileges of the user
     * @return
     */
    public HashSet<String> getMyPrivileges() {
        return myPrivileges;
    }

}
