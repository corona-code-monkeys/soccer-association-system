package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class isLeagueExistBottomUp {


    public static League driverInitLeague(String name) {
        League league = new League(name);
            if (CRUD.addLeague(league)) {
                return league;
            }

        return null;
    }

    public static boolean driverAddSeasonToALeague(Season season, League league) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            CRUD.addLeagueToSeason(season, league, null, null, null);
            CRUD.addSeasonToLeague(league, season, null, null, null);
            return true;
        }
        return false;
    }

    public static boolean driverAssignAndRemoveRefereesFromLeague(League league, List<Referee> referees) {
        if (CRUD.isLeagueExist(league)) {
            CRUD.addAndRemoveRefereesFromLeague(league, referees);
            return true;
        }
        return false;
    }

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
        League league= driverInitLeague("Ligat Ha'al");
        LinkedList<Referee> referees= new LinkedList<>();
        Referee ref= new Referee(new Registered("asd", "asd", "asd"),"dekel lev");
        referees.add(ref);
        Assertions.assertTrue(league!=null);
        Assertions.assertTrue(driverAddSeasonToALeague(new Season(2020, new HashSet<>(),new HashSet<>()), league));
        Assertions.assertTrue(driverAssignAndRemoveRefereesFromLeague(league,referees));
        Assertions.assertTrue(driverAssignRefereesToLeagueInSpecificSeason(league,new Season(2020, new HashSet<>(),new HashSet<>()), referees));
    }
}