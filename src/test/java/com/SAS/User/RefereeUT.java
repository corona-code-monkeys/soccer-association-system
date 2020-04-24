package com.SAS.User;

import com.SAS.game.Game;
import org.junit.Before;
import org.junit.Test;

import java.sql.Ref;

import static org.junit.Assert.*;

public class RefereeUT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Referee(user, "Avi Levi");
    }

    @Test
    public void setLevel() {
        ((Referee)user).setLevel(1);
        assertEquals(1, ((Referee)user).getLevel());
    }

    @Test
    public void getRole() {
        assertEquals("Referee", ((Referee)user).getRole());
    }
}