package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.League;
import com.SAS.domain.User.Referee;
import com.SAS.domain.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class addAndRemoveRefereesFromLeagueIT {
    public static boolean driverAssignAndRemoveRefereesFromLeague(League league, HashSet<Referee> referees) {
        if (CRUD.isLeagueExist(league)) {
            return CRUD.addAndRemoveRefereesFromLeague(league, referees);
        }
        return false;
    }
    @Test
    public void addAndRemoveRefereesFromLeagueTester() {
        League league= new League("Ligat Ha'al");
        HashSet<Referee> referees= new HashSet<>();
        Referee ref= new Referee(new Registered("asd", "asd", "asd"),"dekel lev");
        referees.add(ref);
        Assertions.assertTrue(driverAssignAndRemoveRefereesFromLeague(league,referees));
    }
}
