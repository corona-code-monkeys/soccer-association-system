package com.SAS.League;

import com.SAS.game.Game;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GamesArrangementUT {

    @Test
    public void addGame() {
        GamesArrangement arrangement = new GamesArrangement(new League("test"),
                new Season(2020, new HashSet<>(), new HashSet<>()), new LinkedList<>());
        Game game = new Game();
        Assert.assertFalse(arrangement.getGameslist().contains(game));
        arrangement.addGame(game);
        Assert.assertTrue(arrangement.getGameslist().contains(game));
    }
}