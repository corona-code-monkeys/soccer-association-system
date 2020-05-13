package com.SAS.crudoperations;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.League.*;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LeagueCRUDTest {
    private SystemController sys;
    String name = "Ligat Ha'al";
    Season season = new Season(2020, new HashSet<>(), new HashSet<>());
    Referee ref = new Referee(new Registered("dekelle", "dekel", "dekel"), "dekel");

    @BeforeEach
    void setUp() {
        sys = new SystemController();
        sys.initializeDB();
    }

    @AfterEach
    void tearDown() {
        if (LeagueCRUD.isLeagueExist(name)) {
            LeagueCRUD.removeLeague(name);

        }
        if (LeagueCRUD.isSeasonExist(season.getYear())) {
            LeagueCRUD.removeSeason(season);
        }
//        if(LeagueCRUD.isRefExist(ref)){
//            UsersCRUD.deleteUser(ref.getUserName());
//        }
    }

    @Test
    void isLeagueExist() {
        League league = new League(name);
        Assertions.assertFalse(LeagueCRUD.isLeagueExist(name));
        LeagueCRUD.addLeague(league);
        Assertions.assertTrue(LeagueCRUD.isLeagueExist(name));
    }

    @Test
    void addLeague() {
        League league = new League(name);
        Assertions.assertTrue(LeagueCRUD.addLeague(league));
    }

    @Test
    void addLeagueToSeason() {
        League league = new League(name);
        LeagueCRUD.addLeague(league);
        LeagueCRUD.addSeason(season);
        Assertions.assertTrue(LeagueCRUD.addLeagueToSeason(league, season));
    }

    @Test
    void isRefExist() {
        ref.setLevel(2);
        Assertions.assertFalse(LeagueCRUD.isRefExist(ref));
        UsersCRUD.postUser(ref.getUserName(),"dekel",ref.getFullName(),ref.getRole());
        LeagueCRUD.addReferee(ref);
        Assertions.assertTrue(LeagueCRUD.isRefExist(ref));
        UsersCRUD.deleteUser(ref.getUserName());
    }

//    @Test
//    void isRefExistInLeagueInSeason() {
//    }

    @Test
    void isSeasonExist() {
        Assertions.assertFalse(LeagueCRUD.isSeasonExist(season.getYear()));
        LeagueCRUD.addSeason(season);
        Assertions.assertTrue(LeagueCRUD.isSeasonExist(season.getYear()));
    }

    @Test
    void addReferee() {
        Assertions.assertFalse(LeagueCRUD.addReferee(ref));
        ref.setLevel(1);
        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
        Assertions.assertTrue(LeagueCRUD.addReferee(ref));
        LeagueCRUD.removeReferee(ref);
        UsersCRUD.deleteUser(ref.getUserName());

    }

    @Test
    void removeReferee() {
        Assertions.assertFalse(LeagueCRUD.removeReferee(ref));
        ref.setLevel(1);
        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
        LeagueCRUD.addReferee(ref);
        Assertions.assertTrue(LeagueCRUD.removeReferee(ref));
        UsersCRUD.deleteUser(ref.getUserName());
    }
    @Test
    void addPoliciesToLeagueInSeason() {
        League league = new League(name);
        Assertions.assertTrue(LeagueCRUD.addPoliciesToLeagueInSeason(league, season, new GoalDifference(), new ThreeForWinOneForDrawPolicy(), new TwoRoundsLeague()));
    }
//    @Test
//    void addRefToLeagueInSeason() {
//        League league=new League(name);
//        HashSet<Referee> referees= new HashSet();
//        ref.setLevel(1);
//        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
//        referees.add(ref);
//        LeagueCRUD.addLeague(league);
//        LeagueCRUD.addSeason(season);
//        Assertions.assertTrue(LeagueCRUD.addRefToLeagueInSeason(league,season,referees));
//        UsersCRUD.deleteUser(ref.getUserName());
//    }

//    @Test
//    void removeRefFromLeague() {
//        UsersCRUD.postUser(ref.getUserName(),"dekel","dekel",ref.getRole());
//        Assertions.assertFalse(LeagueCRUD.removeRefFromLeague(ref,new League (name)));
//        ref.setLevel(1);
//        LeagueCRUD.addReferee(ref);
//        HashSet <Referee> referees= new HashSet<>();
//        referees.add(ref);
//        LeagueCRUD.addRefToLeagueInSeason(new League(name),season,referees);
//        Assertions.assertTrue(LeagueCRUD.removeRefFromLeague(ref,new League (name)));
//        UsersCRUD.deleteUser(ref.getUserName());
//
//    }


}