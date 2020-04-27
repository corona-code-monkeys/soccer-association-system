package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class assignRefereesToLeagueInSpecificSeasonIT {
    public static boolean isLeagueExistStub(League league) {
        return CRUD.isLeagueExist(league);
    }
    public static boolean isSeasonExistStub(Season season) {
        return CRUD.isSeasonExist(season);
    }
    public static boolean addRefereesToLeagueInSeasonStub(League league, Season season, HashSet<Referee> referees) {
    return CRUD.addRefereesToLeagueInSeason(league,season,referees);
    }

    @Test
    public void initLeagueTester() {
        League league = new League("Ligat Ha'al");
        Assertions.assertTrue(isLeagueExistStub(league));
        Season season= new Season(2020, new HashSet<>(), new HashSet<>());
        Assertions.assertTrue(isSeasonExistStub(season));
        HashSet<Referee> referees= new HashSet<>();
        Referee ref= new Referee(new Registered("dekelle","dekele","dekele levy"),"dekele levy");
        referees.add(ref);
        Assertions.assertTrue(addRefereesToLeagueInSeasonStub(league,season, referees));

    }
}
