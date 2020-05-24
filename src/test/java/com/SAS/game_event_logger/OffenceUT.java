package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OffenceUT {

    @Test
    void getCommitted() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Offence o = new Offence("1", LocalDate.now(), 0, p, null, "bla bla");
        assertEquals(p.getFullName(), o.getCommitted().getFullName());
        u.deleteUSer(p.getUserName());

    }

    @Test
    void setCommitted() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertEquals(p.getFullName(), o.getCommitted().getFullName());
        u.deleteUSer(p.getUserName());
    }

    @Test
    void getAgainst() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertNull(o.getAgainst());
        u.deleteUSer(p.getUserName());
    }

    @Test
    void setAgainst() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Player p2 = (Player) u.createUser("Yael", "123456", "yael amit", "rami@gmail.com","PLAYER", true);
        Offence o = new Offence("1", LocalDate.now(), 0, null, null, "bla bla");
        o.setCommitted(p);
        assertNull(o.getAgainst());
        o.setAgainst(p2);
        assertEquals(p2.getFullName(), o.getAgainst().getFullName());
        u.deleteUSer(p.getUserName());
        u.deleteUSer(p2.getUserName());
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