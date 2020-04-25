package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;


class LeagueManagementControllerAT {

    private LeagueManagementController leagueManagementController;
    private UserController userController;
    private User associationRepres;
    private League league;
    private Season season;


    @BeforeEach
    public void setUp() {
        season = new Season(2020, new HashSet<Team>(), new HashSet<League>());
        league = new League("Premier League");
        leagueManagementController = new LeagueManagementController();
        userController = new UserController();
        associationRepres = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.ASSOCIATION_REPRESENTATIVE, true, null);

    }

    @Test
    void addRankPolicy() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Rank Policy ID:");
        System.out.println(leagueManagementController.showRankPolicies());
        userChoose = "2";
        System.out.println(userChoose);
        res = leagueManagementController.addRankPolicy(league, season, userChoose, associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add rank policy");
        }
    }

    @Test
    void addPointsPolicy() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Point Policy ID:");
        System.out.println(leagueManagementController.showPointsPolicies());
        userChoose = "1";
        System.out.println(userChoose);
        res = leagueManagementController.addPointsPolicy(league, season, userChoose, associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add points policy");
        }
    }

    @Test
    void addGamePolicy() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Game Policy ID:");
        System.out.println(leagueManagementController.showGamePolicies());
        userChoose = "1";
        System.out.println(userChoose);
        res = leagueManagementController.addGamePolicy(league, season, userChoose, associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add game policy");
        }
    }
}