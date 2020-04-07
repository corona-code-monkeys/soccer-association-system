/**
 * The class represents an AssociationRepresentative
 */

package com.SAS.soccer_association_system.User;

public class AssociationRepresentative extends Role {

    private User user;

    /**
     * Constructor
     * @param user
     */
    public AssociationRepresentative(User user) {
        this.user = user;
        super.setPrivileges("AssociationRepresentative");
    }
}
