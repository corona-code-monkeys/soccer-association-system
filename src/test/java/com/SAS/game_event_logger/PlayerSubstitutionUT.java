package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSubstitutionUT {

    @Test
    void getIn() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", UserType.PLAYER, true,null);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, p1, p2);
        assertEquals(p1.getFullName(), substitution.getIn().getFullName());
    }

    @Test
    void setIn() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", UserType.PLAYER, true,null);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, null, p2);
        assertNull(substitution.getIn());
        substitution.setIn(p1);
        assertEquals(p1.getFullName(), substitution.getIn().getFullName());
    }

    @Test
    void getOut() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", UserType.PLAYER, true,null);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, p1, p2);
        assertEquals(p2.getFullName(), substitution.getOut().getFullName());
    }

    @Test
    void setOut() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", UserType.PLAYER, true,null);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, null, p2);
        assertNull(substitution.getIn());
        substitution.setOut(p2);
        assertEquals(p2.getFullName(), substitution.getOut().getFullName());
    }
}