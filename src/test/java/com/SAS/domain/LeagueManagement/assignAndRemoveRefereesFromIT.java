package com.SAS.domain.LeagueManagement;

import com.SAS.domain.League.*;
import com.SAS.domain.User.Referee;
import com.SAS.domain.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashSet;

public class assignAndRemoveRefereesFromIT {

    public static boolean isLeagueExistStub(League league) {
        return CRUD.isLeagueExist(league);
    }

    public static boolean addAndRemoveRefereesFromLeagueStub(League league, HashSet<Referee> referees) {
        return CRUD.addAndRemoveRefereesFromLeague(league, referees);
    }

    @Test
    public void initLeagueTester() {
        League league = new League("Ligat Ha'al");
        Assertions.assertTrue(isLeagueExistStub(league));
        HashSet referees= new HashSet();
        Referee ref= new Referee(new Registered("dekelle","dekele","dekele levy"),"dekele levy");
        referees.add(ref);
        Assertions.assertTrue(addAndRemoveRefereesFromLeagueStub(league, referees));

    }
}