/**
 * The class represents a team manager
 */

package com.SAS.soccer_association_system.User;

public class TeamManager extends Role {

    private User user;
    /*private List<Team> teams;*/

    /**
     * Constructor
     * @param user
     */
    public TeamManager(User user) {
        this.user = user;
        super.setPrivileges("TeamManager");
    }

    /**
     * The function returns the user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /*public addTeam (Team team) {
        teams.add(team);
    }

    public removeTeam (Team team) {
        teams.remove(team);
    }*/
}
