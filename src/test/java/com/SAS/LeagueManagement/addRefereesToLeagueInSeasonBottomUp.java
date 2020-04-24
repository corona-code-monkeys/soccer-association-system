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

public class addRefereesToLeagueInSeasonBottomUp {
    public static boolean driverAssignRefereesToLeagueInSpecificSeason(League league, Season season, List<Referee> referees) {
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
        LinkedList<Referee> referees= new LinkedList<>();
        Referee ref= new Referee(new Registered("asd", "asd", "asd"),"dekel lev");
        referees.add(ref);
        Assertions.assertTrue(driverAssignRefereesToLeagueInSpecificSeason(league,new Season(2020, new HashSet<>(),new HashSet<>()), referees));
    }
}
