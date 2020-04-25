package com.SAS.League;

import com.SAS.User.Referee;
import com.SAS.User.Registered;

import com.SAS.game.Game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;



class LeagueUT {
    League league = new League("test");
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());

    @Test
    void addSeason() {
        Assertions.assertFalse(league.getSeasonList().contains(season));
        league.addSeason(season);
        Assertions.assertTrue(league.getSeasonList().contains(season));

    }

    @Test
    void addGamesList() {
        LinkedList<Game> gamesList= new LinkedList();
        gamesList.add(new Game());
        league.addSeason(season);
        Assertions.assertTrue(league.getGamesList(season)==null);
        league.addGamesList(season,gamesList);
        Assertions.assertTrue(league.getGamesList(season)!=null);

    }

    @Test
    void addTable() {
        Table table= new Table();
        league.addSeason(season);
        Assertions.assertTrue(league.getTables(season)==null);
        league.addTable(season,table);
        Assertions.assertTrue(league.getTables(season)!=null);
    }

    @Test
    void addGamePolicy() {
        TwoRoundsLeague policy= new TwoRoundsLeague();
        league.addSeason(season);
        Assertions.assertTrue(league.getGamesPolicy(season)==null);
        league.addGamePolicy(season,policy);
        Assertions.assertTrue(league.getGamesPolicy(season)!=null);
    }

    @Test
    void addPointsPolicy() {
        TwoForWinOneForDraw policy= new TwoForWinOneForDraw();
        league.addSeason(season);
        Assertions.assertTrue(league.getPointsPolicy(season)==null);
        league.addPointsPolicy(season,policy);
        Assertions.assertTrue(league.getPointsPolicy(season)!=null);
    }

    @Test
    void addRankPolicy() {
        NumberOfWins policy= new NumberOfWins();
        league.addSeason(season);
        Assertions.assertTrue(league.getRankPolicy(season)==null);
        league.addRankPolicy(season,policy);
        Assertions.assertTrue(league.getRankPolicy(season)!=null);
    }

    @Test
    void addReferee() {
        Referee ref= new Referee(new Registered("dekel", "dekel", "dekel levy"), "dekel levy");
        league.addSeason(season);
        Assertions.assertTrue(league.getReferees().get(season).size()==0);
        league.addReferee(season,ref);
        Assertions.assertTrue(league.getReferees().get(season).size()==1);
    }
}