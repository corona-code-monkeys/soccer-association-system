package com.SAS.Controllers.sasApplication;

import com.SAS.User.User;
import com.SAS.User.UserType;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SASApplicationUT {

    private SASApplication sasApp;
    private User user;

    @BeforeEach
    public void setUp() {
        sasApp= new SASApplication();
        sasApp.createUser("AviCo", "Avi2468","Avi Cohen", UserType.TEAM_OWNER, true, null );
        user = CRUD.getUser("AviCo", "Avi2468");
    }

    @Test
    public void loginSuccess() {
        Boolean loggedIn = sasApp.login("AviCo", "Avi2468");
        assertTrue(loggedIn);
    }

    @Test
    public void loginFailWrongPassword() {
        Boolean loggedIn = sasApp.login("AviCo", "Avi246");
        assertFalse(loggedIn);
    }


}