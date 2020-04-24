package com.SAS.User;

import com.SAS.team.Team;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerUT {

    User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Player(user, "Avi Levi");
        Team team = new Team();
        ((Player)user).setTeam(team);
        team.addPlayerToTeam((Player)user);
    }

    @Test
    public void setDateOfBirth() {
        LocalDate birthDate = LocalDate.parse("1993-10-10");
        ((Player)user).setDateOfBirth(birthDate);
        assertEquals(birthDate, ((Player)user).getDateOfBirth());
    }

    @Test
    public void setFieldRole() {
        ((Player)user).setFieldRole(FieldRole.DEFENDER);
        assertEquals(FieldRole.DEFENDER, ((Player)user).getFieldRole());
    }

    @Test
    public void addPage() {
        ((Player)user).addPage("I'm a player of the best team.");
        assertTrue(((Player)user).getPageID() > -1);
    }

    @Test
    public void setTeam() {
        Team team = new Team();
        ((Player)user).setTeam(team);
        assertEquals(team, ((Player)user).getTeam());
    }

    @Test
    public void removeAssetFromTeam() {
        ((Player)user).removeAssetFromTeam();
        assertNull(((Player)user).getTeam());
    }

    @Test
    public void editDetails() {
        List<String> details = new ArrayList<String>() {
            {
                add("1993-09-09");
                add("Striker");
            }
        };

        assertTrue(((Player)user).editDetails(details));
    }

    @Test
    public void convertFieldRoleSuccessD() {
        String fieldRole = "Defender";
        assertEquals(FieldRole.DEFENDER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleSuccessS() {
        String fieldRole = "Striker";
        assertEquals(FieldRole.STRIKER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleSuccessM() {
        String fieldRole = "Midfielder";
        assertEquals(FieldRole.MIDFIELDER, ((Player)user).convertStringToFieldRole(fieldRole));
    }
    @Test
    public void convertFieldRoleSuccessG() {
        String fieldRole = "Goal Keeper";
        assertEquals(FieldRole.GOAL_KEEPER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleFail() {
        String fieldRole = "d";
        assertNull(((Player)user).convertStringToFieldRole(fieldRole));
    }
}