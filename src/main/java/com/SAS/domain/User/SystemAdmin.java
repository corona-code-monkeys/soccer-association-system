/**
 * The class represents a system admin
 */

package com.SAS.domain.User;

import java.util.HashSet;

public class SystemAdmin extends Role {

    private User user;

    /**
     * Constructor
     * @param user
     */
    public SystemAdmin(User user, String fullName) {
        super(fullName);
        this.user = user;
    }

    /**
     * The function returns all the privileges of the user
     * @return
     */
    public HashSet<String> getMyPrivileges() {
        return myPrivileges;
    }

    @Override
    public String getRole() {
        return "SystemAdmin";
    }
}
