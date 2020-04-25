package com.SAS.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class PersonalPageUT {

    private PersonalPage personalPage;

    @BeforeEach
    public void setUp() throws Exception {
        personalPage = new PersonalPage("This is my page.");
    }

    @Test
    public void setDescription() {
        String desc = "This is the best page.";
        personalPage.setDescription(desc);

        Assertions.assertEquals(desc, personalPage.getDescription());
    }
}