package com.SAS.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FanIT {

    private User user;
    private PersonalPage personalPage;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Fan(user, "Avi Levi");
        personalPage = new PersonalPage("This is the first page.");
        ((Fan)user).addPageToFollow(personalPage);
    }


    @Test
    public void addPageToFollow() {
        PersonalPage page = new PersonalPage("This is the second page.");
        ((Fan)user).addPageToFollow(page);

        assertTrue(((Fan)user).getFollowedPages().contains(page.getPageID()));
    }

    @Test
    public void removePageFromFollow() {
        ((Fan)user).removePageFromFollow(personalPage);
        assertFalse(((Fan)user).getFollowedPages().contains(personalPage.getPageID()));
    }

    @Test
    public void getFollowedPages() {
        addPageToFollow();
        assertNotNull(((Fan)user).getFollowedPages());
    }
}
