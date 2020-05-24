package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OffsideUT {

    @Test
    void getTeamInFavor() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi","rami@gmail.com","PLAYER", true);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, p);
        assertEquals(t.getName(), offside.getTeamInFavor().getName());
        u.deleteUSer(p.getUserName());
    }

    @Test
    void setTeamInFavor() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, null, p);
        assertNull(offside.getTeamInFavor());
        offside.setTeamInFavor(t);
        assertEquals(t.getName(), offside.getTeamInFavor().getName());
        u.deleteUSer(p.getUserName());
    }

    @Test
    void getPlayerInOffside() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, p);
        assertEquals(p.getFullName(), offside.getPlayerInOffside().getFullName());
        u.deleteUSer(p.getUserName());
    }

    @Test
    void setPlayerInOffside() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com","PLAYER", true);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, null);
        assertNull(offside.getPlayerInOffside());
        offside.setPlayerInOffside(p);
        assertEquals(p.getFullName(), offside.getPlayerInOffside().getFullName());
        u.deleteUSer(p.getUserName());
    }
}