/**
 * The class represents a player
 */

package com.SAS.User;

import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class Player extends Role implements TeamAsset {

    private User user;
    private LocalDate dateOfBirth;
    private FieldRole fieldRole;
    private PersonalPage personalPage;
    private Team team;

    /**
     * Constructor
     * @param user
     */
    public Player(User user, String userName) {
        super(userName);
        this.user = user;
        myPrivileges.addAll(user.myPrivileges);
    }

    /**
     * The function return the user
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * The function returns the date of birth of the player
     * @return
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * The function sets the birth of date of the player
     * @param dateOfBirth
     */
    public boolean setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null)
            return false;
         this.dateOfBirth = dateOfBirth;
         return true;
    }

    /**
     * The function returns the field role of the player
     * @return field role
     */
    public FieldRole getFieldRole() {
        return fieldRole;
    }

    /**
     * The function sets the field role of player
     * @param fieldRole
     */
    public boolean setFieldRole(FieldRole fieldRole) {
        if (fieldRole == null)
            return false;
        this.fieldRole = fieldRole;
        return true;
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
     * The function returns the pageID of the personal page
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
        return "Player";
    }

    /**
     * The function returns the team of the player
     * @return
     */
    public Team getTeam() {
        return team;
    }

    /**
     * The fucntion sets the team of the player
     * @param team
     */
    public boolean setTeam(Team team) {
        if (team == null)
            return false;
        this.team = team;
        return true;
    }

    /**
     * This function remove the asset from the team
     */
    @Override
    public void removeAssetFromTeam() {
        team.removePlayer(this);
        this.team = null;
    }

    /**
     * This function edits the player details
     * @param details
     * @return true if details have been edited successfully, false otherwise.
     */
    @Override
    public boolean editDetails(List<String> details) {
        //first is dateOfBirth, second is fieldRole
        FieldRole fieldRole = null;
        LocalDate dateOfBirth = null;

        try {
            fieldRole = convertStringToFieldRole((details.get(1)));
            dateOfBirth = LocalDate.parse(details.get(0));
        }
        catch (Exception e) {
        }

        if (fieldRole == null || dateOfBirth == null) {
            return false;
        }else {
            setDateOfBirth(dateOfBirth);
            setFieldRole(fieldRole);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "fullName='" + getFullName() + '\'' +
                '}';
    }

    @Override
    public String type() {
        return "Player";
    }
}
