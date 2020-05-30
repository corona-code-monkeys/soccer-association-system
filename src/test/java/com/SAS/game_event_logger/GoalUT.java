package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoalUT {

    @Test
    void getScoringTeam() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true));
        assertEquals("1", goal.getGameID());
        assertEquals(t.getName(), goal.getScoringTeam().getName());
        u.deleteUSer("matan");
    }

    @Test
    void setScoringTeam() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, null, (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true));
        assertEquals("1", goal.getGameID());
        assertNull(goal.getScoringTeam());
        goal.setScoringTeam(t);
        assertEquals(t.getName(), goal.getScoringTeam().getName());
        u.deleteUSer("matan");
    }

    @Test
    void getScoringPlayer() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true);
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, p);
        assertEquals("1", goal.getGameID());
        assertEquals(p.getFullName(), goal.getScoringPlayer().getFullName());
        u.deleteUSer("matan");
    }

    @Test
    void setScoringPlayer() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "rami@gmail.com", "PLAYER", true);
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, null);
        assertEquals("1", goal.getGameID());
        assertNull(goal.getScoringPlayer());
        goal.setScoringPlayer(p);
        assertEquals(p.getFullName(), goal.getScoringPlayer().getFullName());
        u.deleteUSer(p.getUserName());
    }
}