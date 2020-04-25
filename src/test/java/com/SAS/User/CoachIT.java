package com.SAS.User;

import com.SAS.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoachIT {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Coach(user, "Avi Levi");
        ((Coach)user).setFieldRole(FieldRole.STRIKER);
        Team macabi = new Team();
        ((Coach)user).setTeam(macabi);
        macabi.setCoach((Coach)user);
    }

    @Test
    public void setTeamSuccess() {
        Team hapoel = new Team();
        ((Coach)user).setTeam(hapoel);
        hapoel.setCoach((Coach)user);
        assertEquals(hapoel, ((Coach)user).getTeam());
    }

    @Test
    public void setTeamFailNull() {
        Team team = null;
        ((Coach)user).setTeam(team);
        assertNotNull(((Coach)user).getTeam());
    }
}