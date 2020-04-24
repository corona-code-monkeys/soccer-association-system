package com.SAS.League;

import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.game.Game;
import com.SAS.team.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SeasonUT {
    League league = new League("test");
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());

    @Test
    void addTeam() {
        Team team = new Team();
        Assertions.assertFalse(season.getTeamsList().contains(team));
        season.addTeam(team);
        Assertions.assertTrue(season.getTeamsList().contains(team));

    }

    @Test
    void addLeague() {
        Assertions.assertFalse(season.getLeaguesList().contains(league));
        season.addLeague(league);
        Assertions.assertTrue(season.getLeaguesList().contains(league));

    }

    @Test
    void addGamesList() {
        LinkedList<Game> gamesList = new LinkedList();
        gamesList.add(new Game());
        Assertions.assertTrue(season.getGamesList(league) == null);
        season.addLeague(league);
        season.addGamesList(league, gamesList);
        Assertions.assertTrue(season.getGamesList(league) != null);
    }

    @Test
    void addBudget() {
        Team team = new Team();
        Assertions.assertTrue(season.getBudgets(team) == null);
        season.addTeam(team);
        season.addBudget(team, 105);
        Assertions.assertTrue(season.getBudgets(team) != null);

    }

    @Test
    void addTable() {
        Table table = new Table();
        Assertions.assertTrue(season.getTable(league) == null);
        season.addLeague(league);
        season.addTable(league, table);
        Assertions.assertTrue(season.getTable(league) == table);
    }

    @Test
    void addGamePolicy() {
        TwoRoundsLeague policy= new TwoRoundsLeague();
        season.addLeague(league);
        Assertions.assertTrue(season.getGamesPolicy(league)==null);
        season.addGamePolicy(league,policy);
        Assertions.assertTrue(season.getGamesPolicy(league)!=null);
    }

    @Test
    void addPointsPolicy() {
        ThreeForWinOneForDrawPolicy policy= new ThreeForWinOneForDrawPolicy();
        season.addLeague(league);
        Assertions.assertTrue(season.getPointsPolicy(league)==null);
        season.addPointsPolicy(league,policy);
        Assertions.assertTrue(season.getPointsPolicy(league)!=null);
    }

    @Test
    void addRankPolicy() {
        GoalDifference policy= new GoalDifference();
        season.addLeague(league);
        Assertions.assertTrue(season.getRankPolicy(league)==null);
        season.addRankPolicy(league,policy);
        Assertions.assertTrue(season.getRankPolicy(league)!=null);
    }

    @Test
    void addReferee() {
        Referee ref = new Referee(new Registered("dekel", "dekel", "dekellevy"),"dekellevy");
        season.addLeague(league);
        Assertions.assertFalse(season.getReferees().get(league).contains(ref));
        season.addReferee(league,ref);
        Assertions.assertTrue(season.getReferees().get(league).contains(ref));
    }
}