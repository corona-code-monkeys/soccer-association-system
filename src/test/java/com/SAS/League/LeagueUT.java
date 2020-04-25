package com.SAS.League;

import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.User.User;
import com.SAS.game.Game;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class LeagueUT {
    League league = new League("test");
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());

    @Test
    void addSeason() {
        Assert.assertFalse(league.getSeasonList().contains(season));
        league.addSeason(season);
        Assert.assertTrue(league.getSeasonList().contains(season));

    }

    @Test
    void addGamesList() {
        LinkedList<Game> gamesList= new LinkedList();
        gamesList.add(new Game());
        league.addSeason(season);
        Assert.assertTrue(league.getGamesList(season)==null);
        league.addGamesList(season,gamesList);
        Assert.assertTrue(league.getGamesList(season)!=null);

    }

    @Test
    void addTable() {
        Table table= new Table();
        league.addSeason(season);
        Assert.assertTrue(league.getTables(season)==null);
        league.addTable(season,table);
        Assert.assertTrue(league.getTables(season)!=null);
    }

    @Test
    void addGamePolicy() {
        TwoRoundsLeague policy= new TwoRoundsLeague();
        league.addSeason(season);
        Assert.assertTrue(league.getGamesPolicy(season)==null);
        league.addGamePolicy(season,policy);
        Assert.assertTrue(league.getGamesPolicy(season)!=null);
    }

    @Test
    void addPointsPolicy() {
        TwoForWinOneForDraw policy= new TwoForWinOneForDraw();
        league.addSeason(season);
        Assert.assertTrue(league.getPointsPolicy(season)==null);
        league.addPointsPolicy(season,policy);
        Assert.assertTrue(league.getPointsPolicy(season)!=null);
    }

    @Test
    void addRankPolicy() {
        NumberOfWins policy= new NumberOfWins();
        league.addSeason(season);
        Assert.assertTrue(league.getRankPolicy(season)==null);
        league.addRankPolicy(season,policy);
        Assert.assertTrue(league.getRankPolicy(season)!=null);
    }

    @Test
    void addReferee() {
        Referee ref= new Referee(new Registered("dekel", "dekel", "dekel levy"), "dekel levy");
        league.addSeason(season);
        Assert.assertTrue(league.getReferees().get(season).size()==0);
        league.addReferee(season,ref);
        Assert.assertTrue(league.getReferees().get(season).size()==1);
    }
}