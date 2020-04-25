package com.SAS.User;

import com.SAS.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeamOwnerIT {

    private User user;
    private User owner;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new TeamManager(user, "Avi Levi");
        Team macabi = new Team();

        //set team
        ((TeamManager)user).setTeam(macabi);
        macabi.setTeamManager((TeamManager) user);

        //set nominated
        owner = new Registered("shalom", "123456", "Shalom Caspi");
        owner = new TeamOwner(user, "Shalom Caspi");
        ((TeamOwner)owner).setTeam(macabi);
        ((TeamManager)user).setNominatedBy((TeamOwner) owner);
    }

    @Test
    public void setTeamSuccess() {
        Team hapoel = new Team();
        ((TeamManager)user).setTeam(hapoel);
        assertEquals(hapoel, ((TeamManager)user).getTeam());
    }

    @Test
    public void setTeamFailNull() {
        Team hapoel = null;
        ((TeamManager)user).setTeam(hapoel);
        assertNotNull(((TeamManager)user).getTeam());
    }

    @Test
    public void setNominatedBySuccess() {
        User nominated = new Registered("moshe", "123456", "Moshe Levi");
        nominated = new TeamOwner(user, "Moshe Levi");
        ((TeamManager)user).setNominatedBy((TeamOwner) nominated);
        assertEquals(nominated, ((TeamManager)user).getNominatedBy());
    }

    @Test
    public void setNominatedByFail() {
        User nominated = null;
        ((TeamManager)user).setNominatedBy((TeamOwner) nominated);
        assertNotNull(((TeamManager)user).getNominatedBy());
    }

    @Test
    public void removeTeam() {
        ((TeamManager)user).removeTeam();
        assertNull(((TeamManager)user).getTeam());
    }
}