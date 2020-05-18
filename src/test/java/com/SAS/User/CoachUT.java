package com.SAS.User;

import com.SAS.team.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;


public class CoachUT {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi", "avi@gmail.com");
        user = new Coach(user, "Avi Levi");
        ((Coach) user).setFieldRole(FieldRole.STRIKER);
        Team team = new Team();
        ((Coach) user).setTeam(team);
        team.setCoach((Coach) user);
    }

    @Test
    public void setLevelSuccess() {
        ((Coach) user).setLevel(2);
        Assertions.assertEquals(2, ((Coach) user).getLevel());
    }

    @Test
    public void setLevelFail() {
        ((Coach) user).setLevel(-1);
        Assertions.assertNotEquals(-1, ((Coach) user).getLevel());
    }

    @Test
    public void setFieldRoleSuccess() {
        ((Coach) user).setFieldRole(FieldRole.DEFENDER);
        Assertions.assertEquals(FieldRole.DEFENDER, ((Coach) user).getFieldRole());
    }

    @Test
    public void setFieldRoleFailNull() {
        ((Coach) user).setFieldRole(null);
        Assertions.assertNotNull(((Coach) user).getFieldRole());
    }

    @Test
    public void addPageSuccess() {
        ((Coach) user).addPage("I'm a coach of the best team.");
        Assertions.assertTrue(((Coach) user).getPageID() > -1);
    }

    @Test
    public void addPageFail() {
        ((Coach) user).addPage("     ");
        Assertions.assertTrue(((Coach) user).getPageID() == -1);
    }

    @Test
    public void addPageFailNull() {
        ((Coach) user).addPage(null);
        Assertions.assertTrue(((Coach) user).getPageID() == -1);
    }

    @Test
    public void getRole() {
        Assertions.assertEquals("Coach", ((Coach) user).getRole());
    }

    @Test
    public void editDetailsSuccess() {
        List<String> details = new ArrayList<String>() {
            {
                add("1");
                add("Defender");
            }
        };

        Assertions.assertTrue(((Coach) user).editDetails(details));
    }

    @Test
    public void editDetailsFailNotEnoughParams() {
        List<String> details = new ArrayList<String>() {
            {
                add("1");
            }
        };

        Assertions.assertFalse(((Coach) user).editDetails(details));
    }

    @Test
    public void editDetailsFailNotLegalRole() {
        List<String> details = new ArrayList<String>() {
            {
                add("2");
                add("defense");
            }
        };

        Assertions.assertFalse(((Coach) user).editDetails(details));
    }
}