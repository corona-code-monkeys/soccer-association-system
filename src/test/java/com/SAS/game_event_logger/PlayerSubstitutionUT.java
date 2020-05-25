package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSubstitutionUT {

    @Test
    void getIn() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit","rami@gmail.com","PLAYER", true);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, p1, p2);
        assertEquals(p1.getFullName(), substitution.getIn().getFullName());
        u.deleteUSer(p1.getUserName());
        u.deleteUSer(p2.getUserName());
    }

    @Test
    void setIn() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", "rami@gmail.com","PLAYER", true);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, null, p2);
        assertNull(substitution.getIn());
        substitution.setIn(p1);
        assertEquals(p1.getFullName(), substitution.getIn().getFullName());
        u.deleteUSer(p1.getUserName());
        u.deleteUSer(p2.getUserName());
    }

    @Test
    void getOut() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", "rami@gmail.com","PLAYER", true);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, p1, p2);
        assertEquals(p2.getFullName(), substitution.getOut().getFullName());
        u.deleteUSer(p1.getUserName());
        u.deleteUSer(p2.getUserName());
    }

    @Test
    void setOut() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p1 = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Player p2 = (Player) u.createUser("yael", "123456", "yael amit", "rami@gmail.com","PLAYER", true);
        PlayerSubstitution substitution = new PlayerSubstitution(gameID, LocalDate.now(), 0, null, p2);
        assertNull(substitution.getIn());
        substitution.setOut(p2);
        assertEquals(p2.getFullName(), substitution.getOut().getFullName());
        u.deleteUSer(p1.getUserName());
        u.deleteUSer(p2.getUserName());
    }
}