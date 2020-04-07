/**
 * The class represents the privileges of registered user
 */

package com.SAS.soccer_association_system.User;

public abstract class Role extends User {

    private Privileges privileges;

    /**
     * The function edits the privileges of the user
     * @param privilege
     */
    public void setPrivileges(String privilege) {
        this.privileges.edit(privilege);
    }
}
