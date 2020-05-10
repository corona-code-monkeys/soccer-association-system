package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.League;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class addLeagueIT {
    public static League driverInitLeague(String name) {
        League league = new League(name);
        if (CRUD.addLeague(league)) {
            return league;
        }

        return null;
    }

    @Test
    public void addLeagueTester() {
        League league = driverInitLeague("Ligat leumit");
        Assertions.assertNotNull(league);
    }
}
