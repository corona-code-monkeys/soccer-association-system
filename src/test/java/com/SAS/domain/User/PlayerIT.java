package com.SAS.domain.User;

import com.SAS.domain.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerIT {

    private User user;

    @BeforeEach
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