package com.SAS.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalPageUT {

    private PersonalPage personalPage;

    @Before
    public void setUp() throws Exception {
        personalPage = new PersonalPage("This is my page.");
    }

    @Test
    public void setDescription() {
        String desc = "This is the best page.";
        personalPage.setDescription(desc);

        assertEquals(desc, personalPage.getDescription());
    }
}