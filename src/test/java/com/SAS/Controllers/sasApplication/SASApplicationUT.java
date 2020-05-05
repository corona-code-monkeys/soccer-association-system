package com.SAS.Controllers.sasApplication;

import com.SAS.User.User;
import com.SAS.User.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SASApplicationUT {

    private SASApplication sasController;
    private User user;

    @BeforeEach
    public void setUp() {
        sasController= new SASApplication();
        user = sasController.createUser("AviCo", "Avi2468","Avi Cohen", UserType.FAN, true, null );

    }

    @Test
    public void loginSuccess() {
        Boolean loggedIn = sasController.login("AviCo", "Avi2468");
        assertTrue(loggedIn);
    }

    @Test
    public void loginFailWrongPassword() {
        Boolean loggedIn = sasController.login("AviCo", "Avi246");
        assertFalse(loggedIn);
    }

}