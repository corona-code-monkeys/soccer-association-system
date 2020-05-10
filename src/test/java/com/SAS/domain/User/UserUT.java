package com.SAS.domain.User;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;




public class UserUT {

    private User user;
    private HashSet<String> priv;

    @BeforeEach
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

        Assertions.assertArrayEquals(priv.toArray(), user.getMyPrivileges().toArray());
    }


}

