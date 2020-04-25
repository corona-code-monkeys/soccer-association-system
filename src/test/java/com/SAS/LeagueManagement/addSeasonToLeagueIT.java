package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.crudoperations.CRUD;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class addSeasonToLeagueIT {
    public static boolean driverAddSeasonToALeague(Season season, League league) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            CRUD.addLeagueToSeason(season, league, null, null, null);
            CRUD.addSeasonToLeague(league, season, null, null, null);
            return true;
        }
        return false;
    }

    @Test
    public void addSeasonToLeagueTester() {
        League league = new League("Ligat Ha'al");
        Season season = new Season(2020, new HashSet<>(), new HashSet<>());
        Assert.assertTrue(driverAddSeasonToALeague(season, league));
    }
}

