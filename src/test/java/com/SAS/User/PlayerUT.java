package com.SAS.User;

import com.SAS.team.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class PlayerUT {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi", "avi@gmail.com");
        user = new Player(user, "Avi Levi");
        Team team = new Team();
        ((Player)user).setTeam(team);
        team.addPlayerToTeam((Player)user);
    }

    @Test
    public void setDateOfBirth() {
        LocalDate birthDate = LocalDate.parse("1993-10-10");
        ((Player)user).setDateOfBirth(birthDate);
        Assertions.assertEquals(birthDate, ((Player)user).getDateOfBirth());
    }

    @Test
    public void setFieldRole() {
        ((Player)user).setFieldRole(FieldRole.DEFENDER);
        Assertions.assertEquals(FieldRole.DEFENDER, ((Player)user).getFieldRole());
    }

    @Test
    public void addPage() {
        ((Player)user).addPage("I'm a player of the best team.");
        Assertions.assertTrue(((Player)user).getPageID() > -1);
    }

    @Test
    public void setTeam() {
        Team team = new Team();
        ((Player)user).setTeam(team);
        Assertions.assertEquals(team, ((Player)user).getTeam());
    }

    @Test

    public void removeAssetFromTeam() {
        ((Player)user).removeAssetFromTeam();
        Assertions.assertNull(((Player)user).getTeam());
    }

    @Test
    public void editDetails() {
        List<String> details = new ArrayList<String>() {
            {
                add("1993-09-09");
                add("Striker");
            }
        };

        Assertions.assertTrue(((Player)user).editDetails(details));
    }

    @Test
    public void convertFieldRoleSuccessD() {
        String fieldRole = "Defender";
        Assertions.assertEquals(FieldRole.DEFENDER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleSuccessS() {
        String fieldRole = "Striker";
        Assertions.assertEquals(FieldRole.STRIKER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleSuccessM() {
        String fieldRole = "Midfielder";
        Assertions.assertEquals(FieldRole.MIDFIELDER, ((Player)user).convertStringToFieldRole(fieldRole));
    }
    @Test
    public void convertFieldRoleSuccessG() {
        String fieldRole = "Goal Keeper";
        Assertions.assertEquals(FieldRole.GOAL_KEEPER, ((Player)user).convertStringToFieldRole(fieldRole));
    }

    @Test
    public void convertFieldRoleFail() {
        String fieldRole = "d";
        Assertions.assertNull(((Player)user).convertStringToFieldRole(fieldRole));
    }
}