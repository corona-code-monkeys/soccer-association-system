package com.SAS.domain.facility;

import com.SAS.domain.game.Game;
import com.SAS.domain.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacilityIT {

    private Facility facility;
    private Game game;
    private Team team;

    @BeforeEach
    void setUp() {
        facility = new Facility();
        game = new Game();
        team = new Team();
    }

    @Test
    void addValidGameToFacilityBooleanCheck() {
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
        facility.setTeam(team);
        facility.removeAssetFromTeam();
        assertNull(facility.getTeam());
    }

    @Test
    void setValidTeamTest() {
        facility.setTeam(team);
        assertEquals(team, facility.getTeam());
    }

    @Test
    void setNullTeamTest() {
        facility.setTeam(team);
        Team nullTeam = null;
        facility.setTeam(nullTeam);
        assertEquals(team, facility.getTeam());
    }
}