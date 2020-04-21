/**
 * The class represents a fan
 */

package com.SAS.User;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Fan extends Role {

    private User user;
    private List<PersonalPage> followedPages;

    /**
     * Constructor
     * @param user
     */
    public Fan(User user, String fullName) {
        super(fullName);
        this.user = user;
        followedPages = new LinkedList<>();
    }

    /**
     * The function returns thr user
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * The function returns all the privileges of the user
     * @return
     */
    public HashSet<String> getMyPrivileges() {
        return myPrivileges;
    }

    /**
     * The function receives a page and adds it to the followed pages list of the user
     * @param personalPage
     */
    public void addPageToFollow(PersonalPage personalPage) {
        followedPages.add(personalPage);
    }

    /**
     * The function receives a page and removes it from the followed pages list of the user
     * @param personalPage
     */
    public void removePageFromFollow(PersonalPage personalPage) {
        followedPages.remove(personalPage);
    }

    @Override
    public String getRole() {
        return "Fan";
    }
}
