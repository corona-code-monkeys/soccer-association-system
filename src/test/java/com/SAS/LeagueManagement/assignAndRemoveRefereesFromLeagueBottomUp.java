package com.SAS.LeagueManagement;

import com.SAS.League.*;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class assignAndRemoveRefereesFromLeagueBottomUp {

    public static boolean isLeagueExistStub(League league) {
        return CRUD.isLeagueExist(league);
    }

    public static boolean addAndRemoveRefereesFromLeagueStub(League league, List<Referee> referees) {
        return CRUD.addAndRemoveRefereesFromLeague(league, referees);
    }

    @Test
    public void initLeagueTester() {
        League league = new League("Ligat Ha'al");
        Assertions.assertTrue(isLeagueExistStub(league));
        LinkedList referees= new LinkedList();
        Referee ref= new Referee(new Registered("dekelle","dekele","dekele levy"),"dekele levy");
        referees.add(ref);
        Assertions.assertTrue(addAndRemoveRefereesFromLeagueStub(league, referees));

    }
}