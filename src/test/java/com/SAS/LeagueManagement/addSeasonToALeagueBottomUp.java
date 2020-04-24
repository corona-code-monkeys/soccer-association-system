package com.SAS.LeagueManagement;

import com.SAS.League.*;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class addSeasonToALeagueBottomUp {
    public static boolean isLeagueExistStub(League league) {
        return CRUD.isLeagueExist(league);
    }
    public static boolean isSeasonExistStub(Season season) {
        return CRUD.isSeasonExist(season);
    }

    public static boolean addLeagueToSeasonStub(Season season, League league, LeagueRankPolicy policy1, PointsPolicy policy2, GamesPolicy policy3) {
        return CRUD.addLeagueToSeason(season, league, policy1, policy2, policy3);
    }
    public static boolean addSeasonToLeagueStub(League league,Season season, LeagueRankPolicy policy1, PointsPolicy policy2, GamesPolicy policy3) {
        return CRUD.addSeasonToLeague(league, season, policy1, policy2, policy3);
    }

    @Test
    public void initLeagueTester() {
        League league= new League("Ligat Ha'al");
        Season season= new Season(2020, new HashSet<>(), new HashSet<>());
        Assertions.assertTrue(isLeagueExistStub(league));
        Assertions.assertTrue(isSeasonExistStub(season));
        Assertions.assertTrue(addLeagueToSeasonStub(season, league, new GoalDifference(),new OnePointForWinAndNoneForDraw(),new TwoRoundsLeague()));
        Assertions.assertTrue(addSeasonToLeagueStub(league, season, new GoalDifference(),new OnePointForWinAndNoneForDraw(),new TwoRoundsLeague()));

    }
}
