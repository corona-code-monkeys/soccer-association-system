/**
 * The class represents a referee
 */

package com.SAS.User;

import com.SAS.game.Game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Referee extends Role {

    private User user;
    private int level;
    private List<Game> games;

    /**
     * Constructor
     * @param user
     */
    public Referee(User user, String fullName) {
        super(fullName);
        this.user = user;
        games = new LinkedList<>();
    }

    /**
     * The function returns the user
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * The function returns the level of the referee
     * @return level - int
     */
    public int getLevel() {
        return level;
    }

    /**
     * The function sets the level of the referee
     * @param level - int
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * The function receives a game and adds it to the games list
     * @param game
     */
    public void addGame (Game game) {
        games.add(game);
    }

    /**
     * The function receives a game and removes it from the games list
     * @param game
     */
    public void removeGame (Game game) {
        games.remove(game);
    }

    /**
     * The function returns all the privileges of the user
     * @return
     */
    public HashSet<String> getMyPrivileges() {
        return myPrivileges;
    }

    @Override
    public String getRole() {
        return "Referee";
    }

}
