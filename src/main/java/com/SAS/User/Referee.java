/**
 * The class represents a referee
 */

package com.SAS.User;

public class Referee extends Role {

    private User user;
    private int level;
    /*private List<Game> games;*/

    /**
     * Constructor
     * @param user
     * @param level
     */
    public Referee(User user, int level) {
        this.user = user;
        this.level = level;
        super.setPrivileges("Referee");
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

    /*public addGame (Game game) {
        games.add(game);
    }

    public removeGame (Game game) {
        games.remove(game)
    }*/

}
