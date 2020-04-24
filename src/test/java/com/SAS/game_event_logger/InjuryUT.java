package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InjuryUT {

    @Test
    void getInjuredPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Injury injury = new Injury(gameID, LocalDate.now(), 0, p, "bla bla");
        assertEquals(p.getFullName(), injury.getInjuredPlayer().getFullName());

    }

    @Test
    void setInjuredPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Injury injury = new Injury(gameID, LocalDate.now(), 0, null, "bla bla");
        assertNull(injury.getInjuredPlayer());
        injury.setInjuredPlayer(p);
        assertEquals(p.getFullName(), injury.getInjuredPlayer().getFullName());
    }

    @Test
    void getDescription() {
        UserController u = new UserController();
        String gameID = "1";
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Injury injury = new Injury(gameID, LocalDate.now(), 0, p, "bla bla");
        assertEquals("bla bla", injury.getDescription());
    }

    @Test
    void setDescription() {
        UserController u = new UserController();
        String gameID = "1";
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Injury injury = new Injury(gameID, LocalDate.now(), 0, p, "bla bla");
        assertEquals("bla bla", injury.getDescription());
        injury.setDescription("not bla bla");
        assertEquals("not bla bla", injury.getDescription());
    }
}