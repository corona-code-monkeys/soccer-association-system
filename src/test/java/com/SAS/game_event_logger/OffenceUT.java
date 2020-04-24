package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OffenceUT {

    @Test
    void getCommitted() {
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Offence o = new Offence("1", LocalDate.now(), 0, p, null, "bla bla");
        assertEquals(p.getFullName(), o.getCommitted().getFullName());

    }

    @Test
    void setCommitted() {
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertEquals(p.getFullName(), o.getCommitted().getFullName());
    }

    @Test
    void getAgainst() {
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertNull(o.getAgainst());
    }

    @Test
    void setAgainst() {
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true);
        Player p2 = (Player) u.createUser("Yael", "123456", "yael amit", UserType.PLAYER, true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertNull(o.getAgainst());
        o.setAgainst(p2);
        assertEquals(p2.getFullName(), o.getAgainst().getFullName());
    }

    @Test
    void getDescription() {
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        assertEquals("bla bla", o.getDescription());
    }

    @Test
    void setDescription() {
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, null);
        assertNull(o.getDescription());
        o.setDescription("bla bla");
        assertEquals("bla bla", o.getDescription());
    }
}