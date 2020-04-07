/**
 * The class represents a system admin
 */

package com.SAS.soccer_association_system.User;

public class SystemAdmin extends Role {

    private User user;

    /**
     * Constructor
     * @param user
     */
    public SystemAdmin(User user) {
        this.user = user;
        super.setPrivileges("Referee");
    }
}
