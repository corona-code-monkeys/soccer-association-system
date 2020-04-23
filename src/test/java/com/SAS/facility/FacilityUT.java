package com.SAS.facility;

import com.SAS.game.Game;
import com.SAS.team.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FacilityUT {

    private Facility facility;
    private Game game;

    @BeforeEach
    void setUp() {
        facility = new Facility();
        game = new Game();
    }

    @Test
    void setNameTest() {
        String name = "Camp Nou";
        facility.setName("Camp Nou");
        assertEquals(name, facility.getName());
    }

    @Test
    void setLocationTest() {
        String location = "Barcelona";
        facility.setLocation("Barcelona");
        assertEquals(location, facility.getLocation());
    }

    @Test
    void setTeamTest() {
        Team team = new Team();
        facility.setTeam(team);
        assertEquals(team, facility.getTeam());
    }


    @Test
    void addValidGameToFacilityBooleanCheck() {
        facility.addGame(game);
        boolean result = facility.addGame(game);
        assertTrue(result);
    }

    @Test
    void addValidGameToFacilityListCheck() {
        facility.addGame(game);
        int result = facility.getGamesList().size();
        assertEquals(1, result);
    }

    @Test
    void addInvalidGameToFacilityBooleanCheck() {
        game = null;
        facility.addGame(game);
        boolean result = facility.addGame(game);
        assertFalse(result);
    }

    @Test
    void addInvalidGameToFacilityListCheck() {
        game = null;
        facility.addGame(game);
        int result = facility.getGamesList().size();
        assertEquals(0, result);
    }

    @Test
    void removeAssetFromTeamBooleanCheck() {
        facility.addGame(game);
    }

    @Test
    void editValidDetailsBooleanCheckWithTraining() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.STADIUM);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("Training");

        boolean result = facility.editDetails(details);
        assertTrue(result);
    }

    @Test
    void editValidDetailsBooleanCheckWithStadium() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.TRAINING);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("Stadium");

        boolean result = facility.editDetails(details);
        assertTrue(result);
    }

    @Test
    void editInvalidFacilityTypeDetailsParamsCheck() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.TRAINING);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("field");

        boolean result = facility.editDetails(details);
        assertFalse(result);
    }

}