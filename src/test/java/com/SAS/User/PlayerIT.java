package com.SAS.User;

import com.SAS.team.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerIT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Player(user, "Avi Levi");
        Team macabi = new Team();
        ((Player)user).setTeam(macabi);
        macabi.addPlayerToTeam((Player)user);
    }

    @Test
    public void setTeamSuccess() {
        Team hapoel = new Team();
        ((Player)user).setTeam(hapoel);
        hapoel.addPlayerToTeam((Player)user);
        assertEquals(hapoel, ((Player)user).getTeam());
    }

    @Test
    public void setTeamFailNull() {
        Team team = null;
        ((Player)user).setTeam(team);
        assertNotNull(((Player)user).getTeam());
    }
}