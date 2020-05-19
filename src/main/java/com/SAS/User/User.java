/**
 * The class represents a user in the system
 */

package com.SAS.User;

import java.util.HashSet;

public abstract class User {

    protected HashSet<String> myPrivileges;

    /**
     * constructor
     */
    public User() {
        this.myPrivileges = new HashSet<>();
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
