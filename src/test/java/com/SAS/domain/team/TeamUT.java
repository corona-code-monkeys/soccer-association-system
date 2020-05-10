package com.SAS.domain.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamUT {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
    }

    @Test
    void setValidNameTest() {
        String name = "team";
        team.setName(name);
        assertEquals(name, team.getName());
    }

    @Test
    void setNullNameTest() {
        String name = "team";
        team.setName(name);
        String nullName = null;
        team.setName(nullName);
        assertEquals(name, team.getName());
    }

    @Test
    void setZeroLengthNameTest() {
        String name = "team";
        team.setName(name);
        String noName = "";
        team.setName(noName);
        assertEquals(name, team.getName());
    }

    @Test
    void inactivateTeam() {
        team.inactivateTeam();
        assertFalse(team.isActive());
    }

    @Test
    void reactivateTeam() {
        team.inactivateTeam();
        team.reactivateTeam();
        assertTrue(team.isActive());
    }
}