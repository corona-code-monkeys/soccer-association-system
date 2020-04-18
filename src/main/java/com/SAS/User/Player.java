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
    public Player(User user, String fullName) {
        super(fullName);
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
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    public void setFieldRole(FieldRole fieldRole) {
        this.fieldRole = fieldRole;
    }

    /**
     * The function adds a personal page to the player
     * @param description
     */
    public void addPage(String description) {
        this.personalPage = new PersonalPage(description);
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
    public void setTeam(Team team) {
        this.team = team;
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
     */
    @Override
    public void editDetails(List<String> details) {
        //first is dateOfBirth, second is fieldRole
        setDateOfBirth(LocalDate.parse(details.get(0)));
        setFieldRole(convertStringToFieldRole(details.get(1)));
    }




}
