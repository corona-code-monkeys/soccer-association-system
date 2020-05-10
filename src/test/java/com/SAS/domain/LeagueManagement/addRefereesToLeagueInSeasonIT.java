package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.League;
import com.SAS.domain.League.Season;
import com.SAS.domain.User.Referee;
import com.SAS.domain.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class addRefereesToLeagueInSeasonIT {
    public static boolean driverAssignRefereesToLeagueInSpecificSeason(League league, Season season, HashSet<Referee> referees) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            if (CRUD.addRefereesToLeagueInSeason(league, season, referees)) {
                return true;
            }
        }
        return false;
    }
    @Test
    public void isLeagueExistTester() {
        League league= new League("Ligat Ha'al");
        HashSet<Referee> referees= new HashSet<>();
        Referee ref= new Referee(new Registered("asd", "asd", "asd"),"dekel lev");
        referees.add(ref);
        Assertions.assertTrue(driverAssignRefereesToLeagueInSpecificSeason(league,new Season(2020, new HashSet<>(),new HashSet<>()), referees));
    }
}
