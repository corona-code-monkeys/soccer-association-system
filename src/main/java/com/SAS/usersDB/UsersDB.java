package com.SAS.usersDB;

import java.util.HashMap;

/**
 * The class represents a users database
 */
public class UsersDB {

    private HashMap<String, String> usersDB;
    private static UsersDB dbInstance = null;

    /**
     * Constructor
     */
    private UsersDB() {
        usersDB = new HashMap<>();
    }

    public static UsersDB getInstance() {
        if (dbInstance == null) {
            synchronized (UsersDB.class) {
                if (dbInstance == null) {
                    dbInstance = new UsersDB();
                }
            }
        }
        return dbInstance;
    }

    /**
     * The function receives userName and password and insert the user to our DB
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean postUser(String userName, String password) {
        if (usersDB.containsKey(userName)) {
            return false;
        }

        usersDB.put(userName, password);
        return true;
    }

    /**
     * The function receives userName and password and checks if the userName and password are valid
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean isUserValid(String userName, String password) {
        if (!usersDB.containsKey(userName)) {
            return false;
        }

        return password == usersDB.get(userName);

    }

    /**
     * The function receives userName and password and delete the user from our DB
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean deleteUser(String userName, String password) {
        if(!isUserValid(userName, password)){
            return false;
        }

        usersDB.remove(userName);
        return true;
    }

}
