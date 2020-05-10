package com.SAS.domain.game_event_logger;

import com.SAS.domain.User.Player;
import com.SAS.domain.User.UserController;
import com.SAS.domain.User.UserType;
import com.SAS.domain.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameEventLoggerUT {


    @Test
    void addNewEvent() {
        UserController u = new UserController();
        GameEventLogger g = new GameEventLogger("1", LocalDate.now());
        assertEquals(0, g.getEventList().size());
        GameEvent goal = new Goal(g.getGameID(), g.getGameDate(), 0, new Team(), (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null));
        g.addNewEvent(goal);
        assertEquals(1, g.getEventList().size());
        assertEquals(goal.getGameID(), g.getEventList().get(0).getGameID());
        assertEquals(goal.getGameDate(), g.getEventList().get(0).getGameDate());
    }

    @Test
    void sort() {
        UserController u = new UserController();
        GameEventLogger g = new GameEventLogger("1", LocalDate.now());
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int gameMinute = random.nextInt(91);
            GameEvent goal = new Goal(g.getGameID(), g.getGameDate(), gameMinute, new Team(), (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null));
            g.addNewEvent(goal);
        }
        g.sort();
        List<GameEvent> list = g.getEventList();
        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(list.get(i).getGameMinute() <= list.get(i + 1).getGameMinute());
        }

    }

    @Test
    void removeEvent() {
        UserController u = new UserController();
        GameEventLogger g = new GameEventLogger("1", LocalDate.now());
        assertEquals(0, g.getEventList().size());
        GameEvent goal = new Goal(g.getGameID(), g.getGameDate(), 0, new Team(), (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null));
        g.addNewEvent(goal);
        assertEquals(1, g.getEventList().size());
        assertEquals(goal.getGameID(), g.getEventList().get(0).getGameID());
        assertEquals(goal.getGameDate(), g.getEventList().get(0).getGameDate());
        g.removeEvent(goal);
        assertEquals(0, g.getEventList().size());

    }
}