package com.SAS.User;

import com.SAS.team.Team;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CoachUT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Coach(user, "Avi Levi");
        ((Coach)user).setFieldRole(FieldRole.STRIKER);
        Team team = new Team();
        ((Coach)user).setTeam(team);
        team.setCoach((Coach)user);
    }

    @Test
    public void setLevelSuccess() {
        ((Coach)user).setLevel(2);
        assertEquals(2, ((Coach)user).getLevel());
    }

    @Test
    public void setLevelFail() {
        ((Coach)user).setLevel(-1);
        assertNotEquals(-1, ((Coach)user).getLevel());
    }

    @Test
    public void setFieldRoleSuccess() {
        ((Coach)user).setFieldRole(FieldRole.DEFENDER);
        assertEquals(FieldRole.DEFENDER, ((Coach)user).getFieldRole());
    }

    @Test
    public void setFieldRoleFailNull() {
        ((Coach)user).setFieldRole(null);
        assertNotNull(((Coach)user).getFieldRole());
    }

    @Test
    public void addPageSuccess() {
        ((Coach)user).addPage("I'm a coach of the best team.");
        assertTrue(((Coach)user).getPageID() > -1);
    }

    @Test
    public void addPageFail() {
        ((Coach)user).addPage("     ");
        assertTrue(((Coach)user).getPageID() == -1);
    }

    @Test
    public void addPageFailNull() {
        ((Coach)user).addPage(null);
        assertTrue(((Coach)user).getPageID() == -1);
    }

    @Test
    public void getRole() {
        assertEquals("Coach", ((Coach)user).getRole());
    }

    @Test
    public void editDetailsSuccess() {
        List<String> details = new ArrayList<String>() {
            {
                add("1");
                add("Defender");
            }
        };

        assertTrue(((Coach)user).editDetails(details));
    }

    @Test
    public void editDetailsFailNotEnoughParams() {
        List<String> details = new ArrayList<String>() {
            {
                add("1");
            }
        };

        assertFalse(((Coach)user).editDetails(details));
    }

    @Test
    public void editDetailsFailNotLegalRole() {
        List<String> details = new ArrayList<String>() {
            {
                add("2");
                add("defense");
            }
        };

        assertFalse(((Coach)user).editDetails(details));
    }
}