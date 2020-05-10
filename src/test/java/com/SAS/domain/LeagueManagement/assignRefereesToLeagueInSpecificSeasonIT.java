package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.League;
import com.SAS.domain.League.Season;
import com.SAS.domain.User.Referee;
import com.SAS.domain.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

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
