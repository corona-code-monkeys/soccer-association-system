package com.SAS.User;

import java.util.HashSet;
import org.junit.*;


import static org.junit.Assert.*;

public class UserUT {

    private User user;
    private HashSet<String> priv;

    @Before
    public void setUp() {
        user = new Registered("avil", "123456", "Avi Levi");
        priv = new HashSet<>();
    }

    @Test
    public void setPrivileges() {
        HashSet<String> newPrivileges = new HashSet<>();
        newPrivileges.add("editPage");
        user.setPrivileges(newPrivileges);
        priv.add("editPage");

        assertArrayEquals(priv.toArray(), user.getMyPrivileges().toArray());
    }


}

