package com.SAS.crudoperations;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.League.*;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.User.User;
import com.SAS.dbstub.dbStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LeagueCRUDTest {
    private dbStub db;
    String name = "Ligat Ha'al";
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());
    Referee ref = new Referee(new Registered("dekelle", "dekel", "dekel"), "dekel");

    @BeforeEach
    void setUp() {
        db = new dbStub();
        db.initializeDB();
    }

    @AfterEach
    void tearDown() {
        if (LeagueCRUD.isLeagueExist(name)) {
            LeagueCRUD.removeLeague(name);

        }
        if (LeagueCRUD.isSeasonExist(season.getYear())) {
            LeagueCRUD.removeSeason(season.getYear());
        }
        UsersCRUD.deleteUser(ref.getUserName());
        LeagueCRUD.removePolicies(name, season.getYear());
    }

    @Test
    void isLeagueExist() {
        Assertions.assertFalse(LeagueCRUD.isLeagueExist(name));
        LeagueCRUD.addLeague(name);
        Assertions.assertTrue(LeagueCRUD.isLeagueExist(name));
    }

    @Test
    void addLeague() {
        Assertions.assertTrue(LeagueCRUD.addLeague(name));
    }

    @Test
    void addLeagueToSeason() {
        LeagueCRUD.addLeague(name);
        LeagueCRUD.addSeason(season.getYear());
        Assertions.assertTrue(LeagueCRUD.addLeagueToSeason(name, season.getYear()));
    }

    @Test
    void isRefExist() {
        ref.setLevel(2);
        Assertions.assertFalse(LeagueCRUD.isRefExist(ref.getUserName()));
        UsersCRUD.postUser(ref.getUserName(), "dekel", ref.getFullName(), ref.getRole());
        LeagueCRUD.addReferee(UsersCRUD.getUserIdByUserName(ref.getUserName()), ref.getLevel());
        Assertions.assertTrue(LeagueCRUD.isRefExist(ref.getUserName()));
        UsersCRUD.deleteUser(ref.getUserName());
    }

//    @Test
//    void isRefExistInLeagueInSeason() {
//    }

    @Test
    void isSeasonExist() {
        Assertions.assertFalse(LeagueCRUD.isSeasonExist(season.getYear()));
        LeagueCRUD.addSeason(season.getYear());
        Assertions.assertTrue(LeagueCRUD.isSeasonExist(season.getYear()));
    }

    @Test
    void addReferee() {
        Assertions.assertFalse(LeagueCRUD.addReferee(UsersCRUD.getUserIdByUserName(ref.getUserName()), ref.getLevel()));
        ref.setLevel(1);
        UsersCRUD.postUser(ref.getUserName(), "dekel", "dekel", ref.getRole());
        Assertions.assertTrue(LeagueCRUD.addReferee(UsersCRUD.getUserIdByUserName(ref.getUserName()), ref.getLevel()));
        LeagueCRUD.removeReferee(ref.getUserID());
        UsersCRUD.deleteUser(ref.getUserName());

    }

    @Test
    void removeReferee() {
        Assertions.assertFalse(LeagueCRUD.removeReferee(UsersCRUD.getUserIdByUserName(ref.getUserName())));
        ref.setLevel(1);
        UsersCRUD.postUser(ref.getUserName(), "dekel", "dekel", ref.getRole());
        LeagueCRUD.addReferee(UsersCRUD.getUserIdByUserName(ref.getUserName()), ref.getLevel());
        Assertions.assertTrue(LeagueCRUD.removeReferee(UsersCRUD.getUserIdByUserName(ref.getUserName())));
        UsersCRUD.deleteUser(ref.getUserName());
    }

    @Test
    void addPoliciesToLeagueInSeason() {
        Assertions.assertTrue(LeagueCRUD.addPoliciesToLeagueInSeason(name, season.getYear(), new GoalDifference().getName(), new ThreeForWinOneForDrawPolicy().getName(), new TwoRoundsLeague().getName()));
    }
//    @Test
//    void addRefToLeagueInSeason() {
//        ref.setLevel(1);
//        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
//        LeagueCRUD.addLeague(name);
//        LeagueCRUD.addSeason(season.getYear());
//        Assertions.assertTrue(LeagueCRUD.addRefToLeagueInSeason(name,season.getYear(),ref.getUserName(),ref.getLevel()));
//
//    }

//    @Test
//    void removeRefFromLeague() {
//        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
//        Assertions.assertFalse(LeagueCRUD.removeRefFromLeague(ref.getUserName(),name));
//        ref.setLevel(1);
//        LeagueCRUD.addReferee(UsersCRUD.getUserIdByUserName(ref.getUserName()),ref.getLevel());
//        HashSet <Referee> referees= new HashSet<>();
//        referees.add(ref);
//        LeagueCRUD.addRefToLeagueInSeason(name,season.getYear(),ref.getUserName(),ref.getLevel());
//        Assertions.assertTrue(LeagueCRUD.removeRefFromLeague(ref.getUserName(),name));
//
//    }


}