package com.SAS.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class RegisteredUT {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi", "avi@gmail.com");
    }

    @Test
    public void setPasswordSuccess() {
        String pass = "654321";
        Assertions.assertTrue(((Registered)user).setPassword(pass));
    }

    @Test
    public void setPasswordFailNull() {
        String pass = null;
        Assertions.assertFalse(((Registered)user).setPassword(pass));
    }

    @Test
    public void setPasswordFailNotLegal() {
        String pass = "   ";
        Assertions.assertFalse(((Registered)user).setPassword(pass));
    }

    @Test
    public void setFullNameSuccess() {
        String fullName = "Avi Levi";
        ((Registered)user).setFullName(fullName);
        Assertions.assertEquals(fullName, ((Registered)user).getFullName());
    }
    @Test
    public void setFullNameFailNull() {
        String fullName = null;
        ((Registered)user).setFullName(fullName);
        Assertions.assertNotEquals(fullName, ((Registered)user).getFullName());
    }
    @Test
    public void setFullNameFailNotLegal() {
        String fullName = "   ";
        ((Registered)user).setFullName(fullName);
        Assertions.assertNotEquals(fullName, ((Registered)user).getFullName());
    }
}