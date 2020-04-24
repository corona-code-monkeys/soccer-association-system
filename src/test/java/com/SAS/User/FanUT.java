package com.SAS.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FanUT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Fan(user, "Avi Levi");
    }


    @Test
    public void getRole() {
        assertEquals("Fan", ((Fan)user).getRole());
    }
}