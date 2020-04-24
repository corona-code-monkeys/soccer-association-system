package com.SAS.User;

import com.SAS.team.Team;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamManagerIT {

    private User user;
    private User owner;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new TeamOwner(user, "Avi Levi");
        Team macabi = new Team();

        //set team
        ((TeamOwner)user).setTeam(macabi);
        macabi.addTeamOwner((TeamOwner) user);

        //set nominated
        owner = new Registered("shalom", "123456", "Shalom Caspi");
        owner = new TeamOwner(user, "Shalom Caspi");
        ((TeamOwner)user).setNominatedBy((TeamOwner) owner);
    }

    @Test
    public void setTeamSuccess() {
        Team hapoel = new Team();
        ((TeamOwner)user).setTeam(hapoel);
        assertEquals(hapoel, ((TeamOwner)user).getTeam());
    }

    @Test
    public void setTeamFailNull() {
        Team hapoel = null;
        ((TeamOwner)user).setTeam(hapoel);
        assertNotNull(((TeamOwner)user).getTeam());
    }

    @Test
    public void setNominatedBySuccess() {
        User nominated = new Registered("moshe", "123456", "Moshe Levi");
        nominated = new TeamOwner(user, "Moshe Levi");
        ((TeamOwner)user).setNominatedBy((TeamOwner) nominated);
        assertEquals(nominated, ((TeamOwner)user).getNominatedBy());
    }

    @Test
    public void setNominatedByFail() {
        User nominated = null;
        ((TeamOwner)user).setNominatedBy((TeamOwner) nominated);
        assertNotNull(((TeamOwner)user).getNominatedBy());
    }

    @Test
    public void removeTeam() {
        ((TeamOwner)user).removeTeam();
        assertNull(((TeamOwner)user).getTeam());
    }
}