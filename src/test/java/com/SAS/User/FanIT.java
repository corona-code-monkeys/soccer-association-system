package com.SAS.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;




public class FanIT {

    private User user;
    private PersonalPage personalPage;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi", "avi@gmail.com");
        user = new Fan(user, "Avi Levi");
        personalPage = new PersonalPage("This is the first page.");
        ((Fan)user).addPageToFollow(personalPage);
    }


    @Test
    public void addPageToFollow() {
        PersonalPage page = new PersonalPage("This is the second page.");
        ((Fan)user).addPageToFollow(page);

        Assertions.assertTrue(((Fan)user).getFollowedPages().contains(page.getPageID()));
    }

    @Test
    public void removePageFromFollow() {
        ((Fan)user).removePageFromFollow(personalPage);
        Assertions.assertFalse(((Fan)user).getFollowedPages().contains(personalPage.getPageID()));
    }

    @Test
    public void getFollowedPages() {
        addPageToFollow();
        Assertions.assertNotNull(((Fan)user).getFollowedPages());
    }
}
