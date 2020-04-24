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

public class addLeagueBottomUp {
    public static League driverInitLeague(String name) {
        League league = new League(name);
            if (CRUD.addLeague(league)) {
        return league;
    }

        return null;
}

    @Test
    public void isLeagueExistTester() {
        League league= driverInitLeague("Ligat Ha'al");
        Assertions.assertTrue(league!=null);
    }
}
