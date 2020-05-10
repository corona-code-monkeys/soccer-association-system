package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.League;
import com.SAS.domain.League.Season;
import com.SAS.domain.User.Referee;
import com.SAS.domain.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class isSeasonExistIT {
    public static boolean driverAddSeasonToALeague(Season season, League league) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            CRUD.addLeagueToSeason(season, league, null, null, null);
            CRUD.addSeasonToLeague(league, season, null, null, null);
            return true;
        }
        return false;
    }

    public static boolean driverAssignRefereesToLeagueInSpecificSeason(League league, Season season, HashSet<Referee> referees) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            if (CRUD.addRefereesToLeagueInSeason(league, season, referees)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void isSeasonExistTester() {
        League league = new League("Ligat Ha'al");
        Season season = new Season(2020, new HashSet<>(), new HashSet<>());
        HashSet<Referee> referees = new HashSet<>();
        Referee ref = new Referee(new Registered("asd", "asd", "asd"), "dekel lev");
        referees.add(ref);
        Assertions.assertTrue(driverAddSeasonToALeague(season, league));
        Assertions.assertTrue(driverAssignRefereesToLeagueInSpecificSeason(league, season, referees));
    }
}
