/**
 * The class represents a fan
 */

package com.SAS.User;

public class Fan extends Role {

    private User user;

    /**
     * Constructor
     * @param user
     */
    public Fan(User user) {
        this.user = user;
        super.setPrivileges("Fan");
    }

    /**
     * The function returns thr user
     * @return
     */
    public User getUser() {
        return user;
    }
}
