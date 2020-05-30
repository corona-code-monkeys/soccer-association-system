package com.SAS.game;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GameIT {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void setValidSeasonTest() {
        Season season = new Season(2020, new HashSet<Team>(), new HashSet<League>());
        boolean res = game.setSeason(season);
        assertTrue(res);
    }

    @Test
    void setInvalidSeasonTest() {
        Season season = null;
        boolean res = game.setSeason(season);
        assertFalse(res);
    }

    @Test
    void setValidHost() {
        Team team = new Team();
        boolean res = game.setHost(team);
        assertTrue(res);
    }

    @Test
    void setInvalidHost() {
        Team team = null;
        boolean res = game.setHost(team);
        assertFalse(res);
    }

    @Test
    void setValidGuest() {
        Team team = new Team();
        boolean res = game.setGuest(team);
        assertTrue(res);
    }

    @Test
    void setInvalidGuest() {
        Team team = null;
        boolean res = game.setGuest(team);
        assertFalse(res);
    }

    @Test
    void addGoalToHost() {
        Team team = new Team();
        boolean res = game.setHost(team);
        game.addGoalToHost();
        assertEquals(1, game.getHostScore());
    }

    @Test
    void addGoalToGuest() {
        Team team = new Team();
        boolean res = game.setGuest(team);
        game.addGoalToGuest();
        assertEquals(1, game.getGuestScore());
    }

    @Test
    void setValidStadium() {
        Facility facility = new Facility();
        facility.setFacilityType(facilityType.STADIUM);
        boolean res = game.setStadium(facility);
        assertTrue(res);
    }

    @Test
    void setNullStadium() {
        Facility facility = null;
        boolean res = game.setStadium(facility);
        assertFalse(res);
    }

    @Test
    void setInvalidTrainiingToStadium() {
        Facility facility = new Facility();
        facility.setFacilityType(facilityType.TRAINING);
        boolean res = game.setStadium(facility);
        assertFalse(res);
    }

    //TODO
    @Test
    void setEvents() {
    }

    //TODO
    @Test
    void setGameReport() {
    }

    @Test
    void setValidReferees() {
        dbStub db = new dbStub();
        dbStub.initializeDB();

        UserController userController = new UserController();
        LinkedList<Referee> referees = new LinkedList<>();
        User ref1 = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", "rami@gmail.com", "REFEREE", true);
        User ref2 = userController.createUser("Vladimir2", "Vladi1234", "Vladimir Ivich2", "rami@gmail.com", "REFEREE", true);
        referees.add((Referee) ref1);
        referees.add((Referee) ref2);
        boolean res = game.setReferees(referees);
        assertTrue(res);
        userController.deleteUSer(((Referee) ref1).getUserName());
        userController.deleteUSer(((Referee) ref2).getUserName());
    }

    @Test
    void setInvalidRefereesLowerThen2() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController userController = new UserController();
        LinkedList<Referee> referees = new LinkedList<>();
        User ref1 = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", "rami@gmail.com", "REFEREE", true);
        referees.add((Referee) ref1);
        boolean res = game.setReferees(referees);
        assertFalse(res);
        userController.deleteUSer(((Referee) ref1).getUserName());
    }

    @Test
    void setInvalidReferees() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController userController = new UserController();
        LinkedList<Referee> referees = new LinkedList<>();
        User ref1 = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", "rami@gmail.com", "REFEREE", true);
        User ref2 = null;
        referees.add((Referee) ref1);
        referees.add((Referee) ref2);
        boolean res = game.setReferees(referees);
        assertFalse(res);
        userController.deleteUSer(((Referee) ref1).getUserName());
    }
}