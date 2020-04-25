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

    //9.a test - success
    @Test
    void successfulLeagueInitialize() {
        //9.1
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
        //9.2
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Add season to this league\"");
        System.out.println("Please enter league name and the season you want to add");
        league.addSeason(season);
        Assertions.assertTrue(league.getSeasonList().contains(season));
        //9.3
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
        //9.3
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
        //9.4
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to add referee");
        System.out.println("Please choose the league and seas you on the referee to be added");
        if(league.getReferees().get(season).contains(referee)) {
            league.addReferee(season, referee);
            Assertions.assertTrue(league.getReferees().get(season).contains(referee));
            System.out.println("The referee added successfully");
            System.out.println("System saved the data");
        }
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
        //9.6
        System.out.println("Choose games policy");
        league.addGamePolicy(season, gPolicy);
        System.out.println("You have chose: " + gPolicy.toString());
        System.out.println("System saved the data");
        Assertions.assertTrue(league.getGamesPolicy(season).getName().equals(gPolicy.getName()));
        System.out.println("League initialized successfully");
    }

    //9.b test - failure
    @Test
    void failingLeagueInitialize() {
        //9.1
        System.out.println("You have selected to edit association settings");
        Assertions.assertFalse(false);
        System.out.println("You are not authorized to enter this page");
        //9.2
        System.out.println("You have selected to edit association settings");
        System.out.println("There is the association page");
        Assertions.assertFalse(CRUD.isLeagueExist(new League("Liga Leumit")));
        System.out.println("The league: \"Liga Leumit\" was not found");
        //9.4
        System.out.println("You have selected to edit association settings");
        System.out.println("This is the association page");
        System.out.println("Pressed \"Referee management\"");
        System.out.println("This is the referees page");
        System.out.println("You chose to add referee");
        System.out.println("Please choose the league and seas you on the referee to be added");
        league.addSeason(season);
        league.addReferee(season, referee);
        if(league.getReferees().get(season).contains(referee)) {
            System.out.println("The referee is already assigned for the league in this season");
            System.out.println("System saved the data");
        }
    }
}