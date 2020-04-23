package com.SAS.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisteredUT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
    }

    @Test
    public void setPasswordSuccess() {
        String pass = "654321";
        assertTrue(((Registered)user).setPassword(pass));
    }

    @Test
    public void setPasswordFailNull() {
        String pass = null;
        assertFalse(((Registered)user).setPassword(pass));
    }

    @Test
    public void setPasswordFailNotLegal() {
        String pass = "   ";
        assertFalse(((Registered)user).setPassword(pass));
    }

    @Test
    public void setFullNameSuccess() {
        String fullName = "Avi Levi";
        ((Registered)user).setFullName(fullName);
        assertEquals(fullName, ((Registered)user).getFullName());
    }
    @Test
    public void setFullNameFailNull() {
        String fullName = null;
        ((Registered)user).setFullName(fullName);
        assertNotEquals(fullName, ((Registered)user).getFullName());
    }
    @Test
    public void setFullNameFailNotLegal() {
        String fullName = "   ";
        ((Registered)user).setFullName(fullName);
        assertNotEquals(fullName, ((Registered)user).getFullName());
    }
}