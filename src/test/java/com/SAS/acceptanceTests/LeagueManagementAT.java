package com.SAS.acceptanceTests;

import com.SAS.League.*;
import com.SAS.LeagueManagement.LeagueManagementController;
import com.SAS.User.AssociationRepresentative;
import com.SAS.User.Referee;
import com.SAS.User.Registered;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;



class LeagueManagementAT {
    private LeagueManagementController controller;
    private League leagueLeumit;
    private League leagueAl;
    private Season season;
    private ThreeForWinOneForDrawPolicy pPolicy;
    private TwoRoundsLeague gPolicy;
    private GoalDifference rPolicy;
    private Referee referee;
    private HashSet<Referee> referees;
    private AssociationRepresentative representative;
    private HashSet<String> privliges;

    @BeforeEach
    void setUp() {
        controller = new LeagueManagementController();
        leagueLeumit = new League("Ligat Leumit");
        leagueAl = new League("Ligat Ha'al");
        pPolicy = new ThreeForWinOneForDrawPolicy();
        gPolicy = new TwoRoundsLeague();
        rPolicy = new GoalDifference();
        season = new Season(2020, new HashSet<>(), new HashSet<>());
        referee = new Referee(new Registered("orelgreen", "orelorel", "orel greenfeld"), "orel greenfeld");
        referees = new HashSet<>();
        representative = new AssociationRepresentative(new Registered("dekelle", "dekeldekel", "dekel levy"), "dekel levy");
        privliges = new HashSet<>();
    }

    //9.1.a test - success
    @Test
    void successfulLeagueCreation() {
        //9.1
        long startTime = System.nanoTime();
        privliges.add("defineL");
        representative.setPrivileges(privliges);
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        if (representative.getMyPrivileges().contains("defineL")) {
            System.out.println("Pressed \"Add new League\"");
            System.out.println("Please enter league name");
            System.out.println("Name: \"Ligat Leumit\"");
            if (controller.initLeague(leagueLeumit)) {
                Assertions.assertTrue(controller.initLeague(leagueLeumit));
                System.out.println("League:" + leagueLeumit.getName() + " was created");
                System.out.println("System saved the data");
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.2.a test - success
    @Test
    void successfulDefineSeasonToLeague() {
        //9.2
        long startTime = System.nanoTime();
        privliges.add("defineSL");
        representative.setPrivileges(privliges);
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        if (representative.getMyPrivileges().contains("defineSL")) {

            System.out.println("Pressed \"Add season to this league\"");
            System.out.println("Please enter league name and the season you want to add");
            Assertions.assertTrue(controller.addSeasonToALeague(season, leagueAl));
        }
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
        if (representative.getMyPrivileges().contains("defineSL")) {

            System.out.println("Pressed \"Referee management\"");
            System.out.println("This is the referees page");
            System.out.println("You chose to add referee");
            System.out.println("Please choose the league you want the referee to be added");
            referees.add(referee);
            leagueAl.addReferee(season, referee);
            Assertions.assertTrue(controller.assignAndRemoveRefereesFromLeague(leagueAl, referees));
            System.out.println("The referee added successfully");
            System.out.println("System saved the data");
            //9.3 - remove referee
            System.out.println("You have selected to edit association settings");
            System.out.println("This is the association page");
            System.out.println("Pressed \"Referee management\"");
            System.out.println("This is the referees page");
            System.out.println("You chose to remove referee");
            System.out.println("Please choose the referee you want to be removed from the League");
            Assertions.assertTrue(controller.assignAndRemoveRefereesFromLeague(leagueAl, referees));
            System.out.println("The referee removed successfully");
            System.out.println("System saved the data");
        }
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
        if (representative.getMyPrivileges().contains("defineSL")) {

            System.out.println("Pressed \"Referee management\"");
            System.out.println("This is the referees page");
            System.out.println("You chose to add referee");
            System.out.println("Please choose the league and seas you on the referee to be added");
            referees.add(referee);
            if (controller.assignRefereesToLeagueInSpecificSeason(leagueAl, season, referees)) {
                leagueAl.addReferee(season, referee);
                Assertions.assertTrue(controller.assignRefereesToLeagueInSpecificSeason(leagueAl, season, referees));
                System.out.println("The referee added successfully");
                System.out.println("System saved the data");
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.5.a test - success
    @Test
    void successfulDefinePointsPolicyAndRankPolicyForTheLeague() {
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        privliges.add("define/changePolicy");
        if (representative.getMyPrivileges().contains("define/changePolicy")) {
            //9.5
            System.out.println("Choose points policy");
            leagueLeumit.addPointsPolicy(season, pPolicy);
            System.out.println("You have chose: " + pPolicy.toString());
            System.out.println("System saved the data");
            Assertions.assertTrue(controller.addPointsPolicy(leagueLeumit, season, pPolicy.getName(), representative));
            //9.5
            System.out.println("Choose league rank policy");
            leagueLeumit.addRankPolicy(season, rPolicy);
            System.out.println("You have chose: " + rPolicy.toString());
            System.out.println("System saved the data");
            Assertions.assertTrue(controller.addPointsPolicy(leagueLeumit, season, rPolicy.getName(), representative));
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.5.a test - success
    @Test
    void successfulDefineGamesPolicyForTheLeague() {
        //9.6
        long startTime = System.nanoTime();
        successfulDefineSeasonToLeague();
        privliges.add("define/changePolicy");
        if (representative.getMyPrivileges().contains("define/changePolicy")) {
            System.out.println("Choose games policy");
            leagueLeumit.addGamePolicy(season, gPolicy);
            System.out.println("You have chose: " + gPolicy.toString());
            System.out.println("System saved the data");
            Assertions.assertTrue(controller.addPointsPolicy(leagueLeumit, season, gPolicy.getName(), representative));
            System.out.println("League initialized successfully");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.1.b test - failure
    @Test
    void failingLeagueCreation() {
        //9.1
        long startTime = System.nanoTime();
        if (!representative.getMyPrivileges().contains("defineL")) {

            System.out.println("You have selected to edit association settings");
            Assertions.assertFalse(representative.getMyPrivileges().contains("defineSL"));
            System.out.println("You are not authorized to enter this page");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    //9.2.b test - failure
    @Test
    void failingDefineSeasonToLeague() {
        //9.2
        long startTime = System.nanoTime();
        if (!representative.getMyPrivileges().contains("defineSL")) {
            System.out.println("You have selected to edit association settings");
            System.out.println("There is the association page");
            Assertions.assertFalse(controller.addSeasonToALeague(season,leagueLeumit));
            System.out.println("The league: "+leagueLeumit.getName()+" was not found");
        }
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
        leagueLeumit.addSeason(season);
        if (!controller.assignAndRemoveRefereesFromLeague(leagueLeumit,referees)) {
            System.out.println("The referee is already assigned for the league in this season");
            System.out.println("System saved the data");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }
}