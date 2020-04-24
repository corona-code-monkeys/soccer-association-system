package com.SAS.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemAdminUT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new SystemAdmin(user, "Avi Levi");
    }

    @Test
    public void getRole() {
        assertEquals("SystemAdmin", ((SystemAdmin)user).getRole());
    }
}