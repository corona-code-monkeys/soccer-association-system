package com.SAS.User;

import com.SAS.game.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class RefereeUT {

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new Registered("avil", "123456", "Avi Levi", "avi@gmail.com");
        user = new Referee(user, "Avi Levi");
    }

    @Test
    public void setLevel() {
        ((Referee)user).setLevel(1);
        Assertions.assertEquals(1, ((Referee)user).getLevel());
    }

    @Test
    public void addGame() {
        Game game = new Game();
        ((Referee)user).addGame(game);

        Assertions.assertTrue(((Referee)user).getGames().contains(game));
    }

    @Test
    public void removeGame() {
        Game game = new Game();
        ((Referee)user).addGame(game);
        ((Referee)user).removeGame(game);

        Assertions.assertTrue(!((Referee)user).getGames().contains(game));
    }

    @Test

    public void getRole() {
        Assertions.assertEquals("Referee", ((Referee)user).getRole());
    }
}