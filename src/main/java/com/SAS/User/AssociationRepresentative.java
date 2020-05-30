/**
 * The class represents an AssociationRepresentative
 */

package com.SAS.User;

import com.SAS.team.Team;
import org.json.JSONObject;

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
