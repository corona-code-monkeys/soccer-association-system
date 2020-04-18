/**
 * The class represents an AssociationRepresentative
 */

package com.SAS.User;

public class AssociationRepresentative extends Role {

    private User user;

    /**
     * Constructor
     * @param user
     */
    public AssociationRepresentative(User user, String fullName) {
        super(fullName);
        this.user = user;
    }


    @Override
    public String getRole() {
        return "AssociationRepresentative";
    }
}
