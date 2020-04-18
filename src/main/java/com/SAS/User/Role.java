/**
 * The class represents the privileges of registered user
 */

package com.SAS.User;

public abstract class Role extends User {

    protected String fullName;

    public Role(String fullName) {
        this.fullName = fullName;
    }

    /**
     * The function returns the role of the user
     * @return String - role
     */
    public abstract String getRole ();


    /**
     * The function returns the full name of the user
     * @return fullName - String
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * The function sets the full name of the user
     * @param fullName - String
     */
    public void setFullName (String fullName){
        this.fullName = fullName;
    }


}