/**
 * The class represents the privileges of registered user
 */

package com.SAS.User;

import java.util.LinkedList;
import java.util.List;

public abstract class Role extends User {

    protected String fullName;
    private List<String> notifications;

    public Role(String fullName) {
        this.fullName = fullName;
        notifications = new LinkedList<>();
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


    /**
     * The function receives a notification and adds it to the notification list
     * @param message
     */
    public void getNotification(String message) {
        notifications.add(message);
        System.out.println(getRole() + " got the message: " + message);
    }

}