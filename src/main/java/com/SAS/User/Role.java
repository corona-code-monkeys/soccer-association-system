/**
 * The class represents the privileges of registered user
 */

package com.SAS.User;

import com.SAS.crudoperations.CRUD;
import com.SAS.crudoperations.UsersCRUD;

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
     *
     * @return String - role
     */
    public abstract String getRole();


    /**
     * The function returns the full name of the user
     *
     * @return fullName - String
     */
    public String getFullName() {
        return UsersCRUD.getFullNameByUserName(userName);
    }

    /**
     * The function returns the user name
     *
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
    public static FieldRole convertStringToFieldRole(String fieldRole) {
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
     * This function converts a string to UserType
     *
     * @param type
     * @return UserType
     */
    public static UserType convertStringToUserType(String type) {
        switch (type.toUpperCase()) {
            case "PLAYER":
                return UserType.PLAYER;
            case "TEAM_OWNER":
                return UserType.TEAM_OWNER;
            case "COACH":
                return UserType.COACH;
            case "REFEREE":
                return UserType.REFEREE;
            case "TEAM_MANAGER":
                return UserType.TEAM_MANAGER;
            case "FAN":
                return UserType.FAN;
            case "ASSOCIATION_REPRESENTATIVE":
                return UserType.ASSOCIATION_REPRESENTATIVE;
            case "SYSTEM_ADMIN":
                return UserType.SYSTEM_ADMIN;
            default:
                System.out.println("Error, no such type");
                return null;
        }
    }

        /**
         * The function receives a notification and adds it to the notification list
         * @param message
         */
        public void getNotification (String message){
            if (message != null && !(message.trim().isEmpty())) {
                notifications.add(message);
                System.out.println(getRole() + " - " + getUserName() + " got the message: " + message);
            }
        }
    }