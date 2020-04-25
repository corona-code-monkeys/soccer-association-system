package com.SAS.League;

import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.game.Game;
import com.SAS.team.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;


class SeasonUT {
    League league = new League("test");
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());

    @Test
    void addTeam() {
        Team team = new Team();
        Assert.assertFalse(season.getTeamsList().contains(team));
        season.addTeam(team);
        Assert.assertTrue(season.getTeamsList().contains(team));

    }

    @Test
    void addLeague() {
        Assert.assertFalse(season.getLeaguesList().contains(league));
        season.addLeague(league);
        Assert.assertTrue(season.getLeaguesList().contains(league));

    }

    @Test
    void addGamesList() {
        LinkedList<Game> gamesList = new LinkedList();
        gamesList.add(new Game());
        Assert.assertTrue(season.getGamesList(league) == null);
        season.addLeague(league);
        season.addGamesList(league, gamesList);
        Assert.assertTrue(season.getGamesList(league) != null);
    }

    @Test
    void addBudget() {
        Team team = new Team();
        Assert.assertTrue(season.getBudgets(team) == null);
        season.addTeam(team);
        season.addBudget(team, 105);
        Assert.assertTrue(season.getBudgets(team) != null);

    }

    @Test
    void addTable() {
        Table table = new Table();
        Assert.assertTrue(season.getTable(league) == null);
        season.addLeague(league);
        season.addTable(league, table);
        Assert.assertTrue(season.getTable(league) == table);
    }

    @Test
    void addGamePolicy() {
        TwoRoundsLeague policy = new TwoRoundsLeague();
        season.addLeague(league);
        Assert.assertTrue(season.getGamesPolicy(league) == null);
        season.addGamePolicy(league, policy);
        Assert.assertTrue(season.getGamesPolicy(league) != null);
    }

    @Test
    void addPointsPolicy() {
        ThreeForWinOneForDrawPolicy policy = new ThreeForWinOneForDrawPolicy();
        season.addLeague(league);
        Assert.assertTrue(season.getPointsPolicy(league) == null);
        season.addPointsPolicy(league, policy);
        Assert.assertTrue(season.getPointsPolicy(league) != null);
    }

    @Test
    void addRankPolicy() {
        GoalDifference policy = new GoalDifference();
        season.addLeague(league);
        Assert.assertTrue(season.getRankPolicy(league) == null);
        season.addRankPolicy(league, policy);
        Assert.assertTrue(season.getRankPolicy(league) != null);
    }

    @Test
    void addReferee() {
        Referee ref = new Referee(new Registered("dekel", "dekel", "dekellevy"), "dekellevy");
        season.addLeague(league);
        Assert.assertFalse(season.getReferees().get(league).contains(ref));
        season.addReferee(league, ref);
        Assert.assertTrue(season.getReferees().get(league).contains(ref));
    }
}