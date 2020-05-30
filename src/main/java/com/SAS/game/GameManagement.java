/**
 * The class represents game management controller
 */
package com.SAS.game;

import java.util.*;

public class GameManagement extends Observable {

    private static Map<String, List<String>> gameFollowers;

    public GameManagement() {
        this.gameFollowers = new HashMap<>();
    }

    /**
     * The function receives userName and game ID and adds a new follower to the game if exists and returns true,
     * otherwise returns false
     * @param userName
     * @param gameID
     * @return true or false
     */
    public boolean addGameFollower(String userName, String gameID) {
        if (validateParam(userName) && validateParam(gameID)) {
            if (gameFollowers.containsKey(gameID)) {
                List<String> followers = gameFollowers.get(gameID);
                if (followers == null) {
                    followers = new LinkedList<>();
                }
                followers.add(userName);
                return true;
            }
        }

        return false;
    }

    /**
     * The function receives param and returns true if it is valid, otherwise returns false
     * @param param
     * @return true or false
     */
    private boolean validateParam(String param) {
        return param != null && !param.trim().isEmpty();
    }

    /**
     * The function receives gameID and game event and sent notification to the game followers
     * @param gameID
     * @param gameEvent
     * @return true or false
     */
    public boolean sendNotification(String gameID, String gameEvent) {
        if (validateParam(gameID) && validateParam(gameEvent)) {

            String message = "The game " + gameID + " has new event: " + gameEvent;
            List<String> notify = new LinkedList<String>() {
                {
                    add(message);
                    addAll(gameFollowers.get(gameID));
                }
            };
            setChanged();
            notifyObservers(notify);
            return true;
        }

        return false;
    }

    /**
     * The function receives userName and game ID and removes the follower from the game if exists and returns true,
     * otherwise returns false
     * @param userName
     * @param gameID
     * @return true or false
     */
    public boolean removeGameFollower(String userName, String gameID) {
        if (validateParam(userName) && validateParam(gameID)) {
            if (gameFollowers.containsKey(gameID)) {
                List<String> followers = gameFollowers.get(gameID);
                if (followers != null && followers.contains(userName)) {
                    followers.remove(userName);
                    return true;
                }
            }
        }

        return false;
    }
}
