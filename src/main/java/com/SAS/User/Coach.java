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
    public Integer getLevel() {
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
     *
     * @param level - int
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * The function sets the field role of the coach
     *
     * @param fieldRole
     */
    public void setFieldRole(FieldRole fieldRole) {
        this.fieldRole = fieldRole;
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
    public void setTeam(Team team) {
        this.team = team;
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
     *
     * @param description
     */
    public void addPage(String description) {
        this.personalPage = new PersonalPage(description);
    }

    /**
     * The fucntion returns the pageID of the personal page
     *
     * @return pageID - int
     */
    public int getPageID() {
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
        FieldRole fieldRole = convertStringToFieldRole((details.get(1)));
        int level = -1;
        try{
            level = Integer.parseInt(details.get(0));
        }catch (Exception e){

        }
        if (fieldRole == null || level == -1) {
            return false;
        }else {
            setLevel(Integer.parseInt(details.get(0)));
            setFieldRole(convertStringToFieldRole(details.get(1)));
            return true;
        }
    }



}
