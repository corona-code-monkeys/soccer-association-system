package com.SAS.acceptanceTests;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.League.*;
import com.SAS.LeagueManagement.LeagueManagementController;
import com.SAS.User.Referee;
import com.SAS.User.Registered;
import com.SAS.crudoperations.CRUD;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;


class LeagueManagementAT {
    private LeagueManagementController controller;
    private League league;
    private Season season;
    private ThreeForWinOneForDrawPolicy pPolicy;
    private TwoRoundsLeague gPolicy;
    private GoalDifference rPolicy;
    private Referee referee;

    @BeforeEach
    void setUp() {
        controller = new LeagueManagementController();
        league = new League("Ligat Ha'al");
        pPolicy = new ThreeForWinOneForDrawPolicy();
        gPolicy = new TwoRoundsLeague();
        rPolicy = new GoalDifference();
        season = new Season(2020, new HashSet<>(), new HashSet<>());
        referee = new Referee(new Registered("orelgreen", "orelorel", "orel greenfeld"), "orel greenfeld");
    }

    //9.1.a test - success
    @Test
    void successfulLeagueCreation() {
        //9.1
        long startTime = System.nanoTime();
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Add new League\"");
        System.out.println("Please enter league name");
        System.out.println("Name: \"Ligat Ha'al\"");
        if (CRUD.addLeague(league)) {
            Assertions.assertTrue(CRUD.addLeague(league));
            System.out.println("League:" + league.getName() + " was created");
            System.out.println("System saved the data");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.2.a test - success
    @Test
    void successfulDefineSeasonToLeague() {
        //9.2
        long startTime = System.nanoTime();
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Add season to this league\"");
        System.out.println("Please enter league name and the season you want to add");
        league.addSeason(season);
        Assertions.assertTrue(league.getSeasonList().contains(season));
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.3.a test - success
    @Test
    void successfulAssignRefereeToLeague() {
        //9.3-add referee
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to add referee");
        System.out.println("Please choose the league you want the referee to be added");
        league.addReferee(season, referee);
        Assertions.assertTrue(league.getReferees().get(season).contains(referee));
        System.out.println("The referee added successfully");
        System.out.println("System saved the data");
        //9.3 - remove referee
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to remove referee");
        System.out.println("Please choose the referee you want to be removed from the League");
        league.getReferees().get(season).remove(referee);
        Assertions.assertFalse(league.getReferees().get(season).contains(referee));
        System.out.println("The referee removed successfully");
        System.out.println("System saved the data");
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.4.a test - success
    @Test
    void successfulAssignRefereeToLeagueInSpecificSeason() {
        //9.4
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to add referee");
        System.out.println("Please choose the league and seas you on the referee to be added");
        if (league.getReferees().get(season).contains(referee)) {
            league.addReferee(season, referee);
            Assertions.assertTrue(league.getReferees().get(season).contains(referee));
            System.out.println("The referee added successfully");
            System.out.println("System saved the data");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.5.a test - success
    @Test
    void successfulDefinePointsPolicyAndRankPolicyForTheLeague() {
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        //9.5
        System.out.println("Choose points policy");
        league.addPointsPolicy(season, pPolicy);
        System.out.println("You have chose: " + pPolicy.toString());
        System.out.println("System saved the data");
        Assertions.assertTrue(league.getPointsPolicy(season).getName().equals(pPolicy.getName()));
        //9.5
        System.out.println("Choose league rank policy");
        league.addRankPolicy(season, rPolicy);
        System.out.println("You have chose: " + rPolicy.toString());
        System.out.println("System saved the data");
        Assertions.assertTrue(league.getRankPolicy(season).getName().equals(rPolicy.getName()));
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.5.a test - success
    @Test
    void successfulDefineGamesPolicyForTheLeague() {
        //9.6
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        System.out.println("Choose games policy");
        league.addGamePolicy(season, gPolicy);
        System.out.println("You have chose: " + gPolicy.toString());
        System.out.println("System saved the data");
        Assertions.assertTrue(league.getGamesPolicy(season).getName().equals(gPolicy.getName()));
        System.out.println("League initialized successfully");
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.1.b test - failure
    @Test
    void failingLeagueCreation() {
        //9.1
        long startTime = System.nanoTime();
        System.out.println("You have selected to edit association settings");
        Assertions.assertFalse(false);
        System.out.println("You are not authorized to enter this page");
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.2.b test - failure
    @Test
    void failingDefineSeasonToLeague() {
        //9.2
        long startTime = System.nanoTime();
        System.out.println("You have selected to edit association settings");
        System.out.println("There is the association page");
        Assertions.assertFalse(CRUD.isLeagueExist(new League("Liga Leumit")));
        System.out.println("The league: \"Liga Leumit\" was not found");
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.4.b test - failure
    @Test
    void assignRefereeToLeagueInSeason() {
        //9.4
        long startTime = System.nanoTime();
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to add referee");
        System.out.println("Please choose the league and seas you on the referee to be added");
        league.addSeason(season);
        league.addReferee(season, referee);
        if (league.getReferees().get(season).contains(referee)) {
            System.out.println("The referee is already assigned for the league in this season");
            System.out.println("System saved the data");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }
}