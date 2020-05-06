/**
 * The class represents the privileges of registered user
 */

package com.SAS.User;

import com.SAS.crudoperations.CRUD;

import java.util.LinkedList;
import java.util.List;

public abstract class Role extends User {

    protected String userName;
    private List<String> notifications;

    public Role(String userName) {
        this.userName = userName;
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
        return CRUD.getFullNameByUserName(userName);
    }

    /**
     * The function returns the user name
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This function converts a string to FieldRole
     *
     * @param fieldRole
     * @return FieldRole
     */
    protected FieldRole convertStringToFieldRole(String fieldRole) {
        switch (fieldRole) {
            case "Goal Keeper":
                return FieldRole.GOAL_KEEPER;
            case "Defender":
                return FieldRole.DEFENDER;
            case "Striker":
                return FieldRole.STRIKER;
            case "Midfielder":
                return FieldRole.MIDFIELDER;
            default:
                System.out.println("Error, no such type");
                return null;
        }
    }

    /**
     * The function receives a notification and adds it to the notification list
     * @param message
     */
    public void getNotification(String message) {
        notifications.add(message);
        System.out.println(getRole() + " - " + getUserName() + " got the message: " + message);
    }

}