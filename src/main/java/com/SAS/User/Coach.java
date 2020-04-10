/**
 * The class represents a coach
 */

package com.SAS.User;

public class Coach extends Role {

    private User user;
    private int level;
    private FieldRole fieldRole;
    /*private Team team; */
    private PersonalPage personalPage;

    /**
     * Constructor
     * @param user
     * @param level
     * @param fieldRole
     */
    public Coach(User user, Integer level, FieldRole fieldRole /* Team team*/) {
        this.user = user;
        this.level = level;
        this.fieldRole = fieldRole;
        /*this.team = team;*/
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
}
