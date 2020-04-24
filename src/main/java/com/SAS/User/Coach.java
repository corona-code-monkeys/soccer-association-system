/**
 * The class represents a coach
 */

package com.SAS.User;

import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;
import java.util.HashSet;
import java.util.List;

public class Coach extends Role implements TeamAsset {

    private User user;
    private int level;
    private FieldRole fieldRole;
    private Team team;
    private PersonalPage personalPage;

    /**
     * Constructor
     *
     * @param user
     */
    public Coach(User user, String fullName) {
        super(fullName);
        this.user = user;
        myPrivileges.addAll(user.myPrivileges);
    }

    /**
     * The function returns the user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * The function returns the level of the coach
     *
     * @return level - int
     */
    public int getLevel() {
        return level;
    }

    /**
     * The function returns the field role of the coach
     *
     * @return field role
     */
    public FieldRole getFieldRole() {
        return fieldRole;
    }

    /**
     * The function sets the level of the coach
     * @param level - int
     * @return true if succeeded, otherwise returns false
     */
    public boolean setLevel(int level) {
        if (level > 0) {
            this.level = level;
            return true;
        }

        return false;
    }

    /**
     * The function sets the field role of the coach
     *
     * @param fieldRole
     */
    public boolean setFieldRole(FieldRole fieldRole) {
        if (fieldRole == null)
            return false;
        this.fieldRole = fieldRole;
        return true;
    }

    /**
     * The function returns the team of the coach
     *
     * @return team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * The function sets the team of the coach
     *
     * @param team
     */
    public boolean setTeam(Team team) {
        if (team == null)
            return false;
        this.team = team;
        return true;
    }

    /**
     * This function removes the coach from team
     */
    @Override
    public void removeAssetFromTeam() {
        this.team.removeCoach(this);
        this.team=null;
    }

    /**
     * The function adds personal page to the coach
     * @param description
     * @return pageID - int
     */
    public int addPage(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.personalPage = new PersonalPage(description);
            return this.personalPage.getPageID();
        }
        return -1;
    }

    /**
     * The fucntion returns the pageID of the personal page
     *
     * @return pageID - int
     */
    public int getPageID() {
        if (personalPage == null) {
            return -1;
        }
        return personalPage.getPageID();
    }

    /**
     * The function returns all the privileges of the user
     *
     * @return
     */
    public HashSet<String> getMyPrivileges() {
        return myPrivileges;
    }

    @Override
    public String getRole() {
        return "Coach";
    }

    /**
     * This function edits the player details
     *@param details
     * @return true if details have been edited successfully, false otherwise.
     */
    @Override
    public boolean editDetails(List<String> details) {
        //first is level, second is fieldRole
        FieldRole fieldRole = null;
        int level = -1;

        try{
            level = Integer.parseInt(details.get(0));
            fieldRole = convertStringToFieldRole((details.get(1)));
        }catch (Exception e){

        }

        if (fieldRole == null || level == -1) {
            return false;
        }
        else {
            setLevel(level);
            setFieldRole(fieldRole);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Coach{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
