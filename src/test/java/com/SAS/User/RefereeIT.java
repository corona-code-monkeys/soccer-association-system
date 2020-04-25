package com.SAS.User;

import com.SAS.game.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RefereeIT {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi");
        user = new Referee(user, "Avi Levi");
    }

    @Test
    public void addGameSuccess() {
        Game game = new Game();
        ((Referee)user).addGame(game);

        assertTrue(((Referee)user).getGames().contains(game));
    }

    @Test
    public void addGameFailNull() {
        Game game = null;
        ((Referee)user).addGame(game);

        assertTrue(((Referee)user).getGames().isEmpty());
    }

    @Test
    public void removeGame() {
        Game game = new Game();
        ((Referee)user).addGame(game);
        ((Referee)user).removeGame(game);

        assertTrue(!((Referee)user).getGames().contains(game));
    }
}