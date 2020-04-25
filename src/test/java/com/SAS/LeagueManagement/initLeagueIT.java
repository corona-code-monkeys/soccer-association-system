package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.crudoperations.CRUD;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class initLeagueIT {
    public static boolean addLeagueStub(League league) {
        return CRUD.addLeague(league);
    }

    public static boolean isLeagueExistStub(League league) {
        return CRUD.isLeagueExist(league);
    }

    @Test
    public void initLeagueTester() {
        League league = new League("Ligat leumit");
        Assert.assertTrue(addLeagueStub(league));
//        Assert.assertTrue(isLeagueExistStub(league)); test fail because not a readl db exist, weill remove from comment after building DB

    }
}
