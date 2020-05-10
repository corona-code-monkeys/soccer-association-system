package com.SAS.domain.game_event_logger;

import com.SAS.domain.User.Player;
import com.SAS.domain.User.UserController;
import com.SAS.domain.User.UserType;
import com.SAS.domain.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoalUT {

    @Test
    void getScoringTeam() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null));
        assertEquals("1", goal.getGameID());
        assertEquals(t.getName(), goal.getScoringTeam().getName());

    }

    @Test
    void setScoringTeam() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, null, (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null));
        assertEquals("1", goal.getGameID());
        assertNull(goal.getScoringTeam());
        goal.setScoringTeam(t);
        assertEquals(t.getName(), goal.getScoringTeam().getName());
    }

    @Test
    void getScoringPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, p);
        assertEquals("1", goal.getGameID());
        assertEquals(p.getFullName(), goal.getScoringPlayer().getFullName());
    }

    @Test
    void setScoringPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        t.setName("BGU");
        Goal goal = new Goal(gameID, LocalDate.now(), 0, t, null);
        assertEquals("1", goal.getGameID());
        assertNull(goal.getScoringPlayer());
        goal.setScoringPlayer(p);
        assertEquals(p.getFullName(), goal.getScoringPlayer().getFullName());
    }
}