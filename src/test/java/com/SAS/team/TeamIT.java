package com.SAS.team;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.*;
import com.SAS.facility.Facility;
import com.SAS.teamManagenemt.TeamManagement;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TeamIT {

    private Team team;
    private TeamManagement teamManagement;
    private UserController userController;
    private User teamOwner;
    private User coach;
    private User teamManager;
    private User player;

    @BeforeEach
    void setUp() {
        team = new Team();
    }

    @Test
    void addValidPlayerToTeamTest() {
        player = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.PLAYER, true);
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true);
        boolean res = team.addPlayerToTeam((Player) player);
        assertEquals(1, team.getPlayers().size());
    }

    @Test
    void addInvalidPlayerToTeamTest() {
        player = null;
        boolean res = team.addPlayerToTeam((Player) player);
        assertEquals(0, team.getPlayers().size());
    }

    @Test
    void addValidSeasonTest() {
        HashSet<Team> teamsList = new HashSet<>();
        HashSet<League> leaguesList = new HashSet<>();
        Season season = new Season(2020, teamsList, leaguesList);
        boolean res = team.addSeason(season);
        assertTrue(res);
    }

    @Test
    void addExistSeasonTest() {
        HashSet<Team> teamsList = new HashSet<>();
        HashSet<League> leaguesList = new HashSet<>();
        Season season = new Season(2020, teamsList, leaguesList);
        team.addSeason(season);
        boolean res = team.addSeason(season);
        assertFalse(res);
    }

    @Test
    void addValidTransactionToTeamTest() {
        LocalDate localDate = LocalDate.now();
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true);
        Transaction transaction = new Transaction(1000.0, TransactionType.INCOME, localDate, team, "tickets", (TeamOwner) teamOwner);
        boolean res = team.addTransactionToTeam(transaction);
        assertTrue(res);
    }

    @Test
    void addInvalidTransactionToTeamTest() {
        LocalDate localDate = LocalDate.now();
        Transaction transaction = null;
        boolean res = team.addTransactionToTeam(transaction);
        assertFalse(res);
    }

    @Test
    void addTransactionLowerThenZeroToTeamTest() {
        LocalDate localDate = LocalDate.now();
        Transaction transaction = new Transaction(1000.0, TransactionType.EXPENSE, localDate, team, "tickets", (TeamOwner) teamOwner);
        boolean res = team.addTransactionToTeam(transaction);
        assertFalse(res);
    }

    @Test
    void addFacilityTest() {
        Facility facility = new Facility();
        team.addFacility(facility);
        assertEquals(1, team.getTeamFacilities().size());
    }

    //TODO
    @Test
    void setTeamManager() {
    }

    //TODO
    @Test
    void addTeamOwner() {
    }

    //TODO
    @Test
    void removeTeamOwner() {
    }

    //TODO
    @Test
    void setCoach() {
    }
}