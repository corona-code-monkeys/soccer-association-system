/**
 * The class represents a team owner
 */
package com.SAS.User;

public class TeamOwner extends Role {

    private User user;
    /*private Team team;*/

    /**
     * Constructor
     * @param user
     */
    public TeamOwner(User user) {
        this.user = user;
        super.setPrivileges("TeamOwner");
    }

    /**
     * The function returns the user
     * @return user
     */
    public User getUser() {
        return user;
    }
}
