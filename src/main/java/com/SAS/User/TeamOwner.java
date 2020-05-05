/**
 * The class represents a team owner
 */
package com.SAS.User;

import com.SAS.team.Team;

import java.util.HashSet;

public class TeamOwner extends Role {

    private User user;
    private Team team;
    private TeamOwner nominatedBy;


    /**
     * Constructor
     * @param user
     */
    public TeamOwner(User user, String fullName) {
        super(fullName);
        this.user = user;
        myPrivileges.addAll(user.myPrivileges);
    }

    /**
     * The function returns the user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * The function returns the team of the owner
     * @return
     */
    public Team getTeam() {
        return team;
    }

    /**
     * The function sets the team of the owner
     * @param team
     */
    public void setTeam(Team team) {
        if (team != null) {
            this.team = team;
        }
    }

    /**
     * The function returns the user that nominated this user to team owner
     * @return user
     */
    public User getNominatedBy() {
        return nominatedBy;
    }

    /**
     * The function sets the user that nominated this user to team owner
     * @param nominatedBy
     */
    public void setNominatedBy(TeamOwner nominatedBy) {
        if (nominatedBy != null) {
            this.nominatedBy = nominatedBy;
        }
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
        return "TeamOwner";
    }

    /**
     * This function removes the team of the owner
     */
    public void removeTeam() {
        this.team = null;
    }
}
