/**
 * The class represents a coach
 */

package com.SAS.User;

import com.SAS.team.Team;

import java.util.HashSet;

public class Coach extends Role {

    private User user;
    private int level;
    private FieldRole fieldRole;
    private Team team;
    private PersonalPage personalPage;

    /**
     * Constructor
     * @param user
     */
    public Coach(User user, String fullName) {
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
     * The function returns the level of the coach
     * @return level - int
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * The function returns the field role of the coach
     * @return field role
     */
    public FieldRole getFieldRole() {
        return fieldRole;
    }

    /**
     * The function sets the level of the coach
     * @param level - int
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * The function sets the field role of the coach
     * @param fieldRole
     */
    public void setFieldRole(FieldRole fieldRole) {
        this.fieldRole = fieldRole;
    }

    /**
     * The function returns the team of the coach
     * @return team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * The function sets the team of the coach
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * The function adds personal page to the coach
     * @param description
     */
    public void addPage(String description) {
        this.personalPage = new PersonalPage(description);
    }

    /**
     * The fucntion returns the pageID of the personal page
     * @return pageID - int
     */
    public int getPageID() {
        return personalPage.getPageID();
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
        return "Coach";
    }
}
