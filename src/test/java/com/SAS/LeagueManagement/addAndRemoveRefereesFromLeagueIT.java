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

public class addAndRemoveRefereesFromLeagueIT {
    public static boolean driverAssignAndRemoveRefereesFromLeague(League league, List<Referee> referees) {
        if (CRUD.isLeagueExist(league)) {
            CRUD.addAndRemoveRefereesFromLeague(league, referees);
            return true;
        }
        return false;
    }
    @Test
    public void addAndRemoveRefereesFromLeagueTester() {
        League league= new League("Ligat Ha'al");
        LinkedList<Referee> referees= new LinkedList<>();
        Referee ref= new Referee(new Registered("asd", "asd", "asd"),"dekel lev");
        referees.add(ref);
        Assertions.assertTrue(driverAssignAndRemoveRefereesFromLeague(league,referees));
    }
}
