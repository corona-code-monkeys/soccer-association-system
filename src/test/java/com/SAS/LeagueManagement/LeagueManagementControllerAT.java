package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;


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
        associationRepres = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", "vlad@gmail.com","ASSOCIATION_REPRESENTATIVE", true);

    }

    @Test
    void addRankPolicySuccess() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Rank Policy ID:");
        System.out.println(leagueManagementController.showRankPolicies());
        userChoose = "2";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose,"1","1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add rank policy");
        }
    }

    @Test
    void addPointsPolicySuccess() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Point Policy ID:");
        System.out.println(leagueManagementController.showPointsPolicies());
        userChoose = "1";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose,"1","1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add points policy");
        }
    }

    @Test
    void addGamePolicySuccess() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Game Policy ID:");
        System.out.println(leagueManagementController.showGamePolicies());
        userChoose = "1";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose,"1","1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add game policy");
        }
    }

    @Test
    void addRankPolicyFailure() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Rank Policy ID:");
        System.out.println(leagueManagementController.showRankPolicies());
        userChoose = "5";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose,"1","1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
        }
        else {
            System.out.println("Couldn't add rank policy");
            assertFalse(res);
        }
    }

    @Test
    void addPointsPolicyFailure() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Point Policy ID:");
        System.out.println(leagueManagementController.showPointsPolicies());
        userChoose = "5";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose, "1", "1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add points policy");
            assertFalse(res);
        }
    }

    @Test
    void addGamePolicyFailure() {
        boolean res;
        String userChoose;
        System.out.println("Please choose Game Policy ID:");
        System.out.println(leagueManagementController.showGamePolicies());
        userChoose = "5";
        System.out.println(userChoose);
        res = leagueManagementController.addPolicies(league, season, userChoose,"1","1", associationRepres);
        if (res) {
            System.out.println("The policy was added successfully");
            assertTrue(res);
        }
        else {
            System.out.println("Couldn't add game policy");
            assertFalse(res);
        }
    }

    @Test
    void assignRefereeSuccess() {
        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("Manage Referees");
            System.out.println("-- choose add new referee");
            System.out.println("Please fill in referee details");
            System.out.println("Please enter the referee name");
            String fullName = "Yossi Levi";
            System.out.println(fullName);
            System.out.println("Please enter the referee level");
            String level = "1";
            System.out.println(level);
            //default userName and password
            String userName = "yossiL";
            String pass = "yossi123";

            System.out.println("Please confirm");
            System.out.println("Confirm");

            //insert the details to list
            List<String> details = new ArrayList<String>() {
                {
                    add(userName);
                    add(pass);
                    add(fullName);
                    add(level);
                }
            };

            User referee = leagueManagementController.addNewReferee(details, associationRepres);
            assertNotNull(referee);
            System.out.println(leagueManagementController.sendInvitationToReferee(referee));
            System.out.println("The referee added successfully");

        }
    }

    @Test
    void assignRefereeFail() {
        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("Manage Referees");
            System.out.println("-- choose add new referee");
            System.out.println("Please fill in referee details");
            System.out.println("Please enter the referee name");
            String fullName = "Yossi Levi";
            System.out.println(fullName);
            System.out.println("Please enter the referee level");
            //default userName and password
            String userName = "yossiL";
            String pass = "yossi123";

            System.out.println("Please confirm");
            System.out.println("Confirm");

            //insert the details to list
            List<String> details = new ArrayList<String>() {
                {
                    add(userName);
                    add(pass);
                    add(fullName);
                }
            };

            User referee = leagueManagementController.addNewReferee(details, associationRepres);
            assertNull(referee);
            System.out.println("The referee wasn't added.");

        }
    }

    @Test
    void removeRefereeSuccess() {
        //set up - add new referee
        //insert the details to list
        List<String> details = new ArrayList<String>() {
            {
                add("yossiL");
                add("yossi123");
                add("Yossi Levi");
                add("1");
            }
        };
        leagueManagementController.addNewReferee(details, associationRepres);

        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("Manage Referees");
            System.out.println("-- choose remove referee");
            System.out.println(leagueManagementController.showAllReferees());

            System.out.println("Please choose the referee you want to remove");
            String fullName = "Yossi Levi";
            System.out.println(fullName);

            System.out.println("Please confirm");
            System.out.println("Confirm");

            //User referee = leagueManagementController.getRefereeByName(fullName);
            User referee = leagueManagementController.getRefereeByUserName("yossiL");
            assertTrue(leagueManagementController.removeReferee(referee, associationRepres));
            System.out.println("The referee removed successfully");

        }
    }

    @Test
    void removeRefereeFail() {
        //set up - add new referee
        //insert the details to list
        List<String> details = new ArrayList<String>() {
            {
                add("yossiL");
                add("yossi123");
                add("Yossi Levi");
                add("1");
            }
        };
        leagueManagementController.addNewReferee(details, associationRepres);

        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("Manage Referees");
            System.out.println("-- choose remove referee");
            System.out.println(leagueManagementController.showAllReferees());

            System.out.println("Please choose the id of the referee you want to remove");
            int id = 123456;
            System.out.println(123456);

            System.out.println("Please confirm");
            System.out.println("Confirm");

            User referee = leagueManagementController.getRefereeID(id);

            assertNull(referee);
            leagueManagementController.removeReferee(referee, associationRepres);
            System.out.println("The referee wasn't removed.");

        }
    }

    @Test
    public void addLeagueSuccess() {
        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("-- choose add new league --");
            System.out.println("Please fill in league details:");
            System.out.println("Enter the name of the league");
            String name = "Ligat Ha'al";
            System.out.println(name);

            assertTrue(leagueManagementController.addNewLeague(name, associationRepres));
            System.out.println("The league " + name + " added successfully!");
        }
    }

    @Test
    public void addLeagueFail() {
        User teamOwner = userController.createUser("Yossil", "tossi123", "Yossi Levi", "yos@gmail.com", "TEAM_OWNER", true);

        System.out.println("-- Enter setting mode --");
        assertFalse(leagueManagementController.canAccessSettingsPage(teamOwner));
        System.out.println("The user doesn't have the required permissions.");
    }

    @Test
    public void addSeasonToLeagueSuccess() {
        //add league
        leagueManagementController.addNewLeague("Ligat Ha'al", associationRepres);

        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("-- choose add season to league --");
            System.out.println(leagueManagementController.showLeagues());

            System.out.println("Please choose the league you want to add season:");
            String name = "Ligat Ha'al";
            System.out.println(name);
            League league = leagueManagementController.getLeagueByName(name);
            if (league != null) {
                System.out.println("Please fill in the season details:");
                System.out.println("Enter the year of the season:");
                String year = "2020";
                System.out.println(year);

                assertTrue(leagueManagementController.addNewSeasonToLeague(year, league, associationRepres));
                System.out.println("The season added successfully!");
            }
        }
    }

    @Test
    public void addSeasonToLeagueFailLeagueNotExist() {
        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("-- choose add season to league --");
            System.out.println(leagueManagementController.showLeagues());

            System.out.println("Please choose the league you want to add season:");
            String name = "Ligat Ha'al";
            System.out.println(name);
            League league = leagueManagementController.getLeagueByName(name);
            assertNull(league);
            System.out.println("The league doesn't exist");
        }
    }

    @Test
    public void addRefereesToSeasonInLeagueSuccess() {
        //add league, season and referee - set up
        List<String> details = new ArrayList<String>() {
            {
                add("yossiL");
                add("yossi123");
                add("Yossi Levi");
                add("1");
            }
        };

        leagueManagementController.addNewReferee(details, associationRepres);
        String leagueName = "Ligat Ha'al";
        leagueManagementController.addNewLeague(leagueName, associationRepres);
        League league = leagueManagementController.getLeagueByName("Ligat Ha'al");
        leagueManagementController.addNewSeasonToLeague("2020", league, associationRepres);

        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("-- choose add referees to league --");
            System.out.println(leagueManagementController.showLeagues());

            System.out.println("Please choose the league you want to add season:");
            String name = "Ligat Ha'al";
            System.out.println(name);
            league = leagueManagementController.getLeagueByName(name);
            System.out.println("Please choose the season you want to add referees:");
            String year = "2020";
            System.out.println(name);
            Season season = leagueManagementController.getSeasonByYearInLeague(league, year);

            if (season != null) {
                System.out.println(leagueManagementController.showAllReferees());
                System.out.println("Please choose the referee you want to add to the season:");
                String fullName = "Yossi Levi";
                System.out.println(fullName);
                //User referee = leagueManagementController.getRefereeByName(fullName);
                User referee = leagueManagementController.getRefereeByUserName("yossiL");
                HashSet<Referee> referees = new HashSet<Referee>() {
                    {
                        add((Referee) referee);
                    }
                };
                assertTrue(leagueManagementController.assignRefereesToLeagueInSpecificSeason(league, season, referees, associationRepres));
                System.out.println("The referees added successfully!");
            }
        }
    }


    @Test
    public void addRefereesToSeasonInLeagueFail() {
        //add league, season and referee - set up
        List<String> details = new ArrayList<String>() {
            {
                add("yossiL");
                add("yossi123");
                add("Yossi Levi");
                add("1");
            }
        };

        leagueManagementController.addNewReferee(details, associationRepres);
        User referee = leagueManagementController.getRefereeByUserName("yossiL");
        HashSet<Referee> referees = new HashSet<>();
        referees.add((Referee) referee);
        String leagueName = "Ligat Ha'al";
        leagueManagementController.addNewLeague(leagueName, associationRepres);
        League league = leagueManagementController.getLeagueByName("Ligat Ha'al");
        leagueManagementController.addNewSeasonToLeague("2020", league, associationRepres);
        Season season = leagueManagementController.getSeasonByYearInLeague(league, "2020");
        leagueManagementController.assignRefereesToLeagueInSpecificSeason(league, season, referees, associationRepres);

        System.out.println("-- Enter setting mode --");
        if (leagueManagementController.canAccessSettingsPage(associationRepres)) {
            System.out.println("Association Settings page:");
            System.out.println("-- choose add referees to league --");
            System.out.println(leagueManagementController.showLeagues());

            System.out.println("Please choose the league you want to add season:");
            String name = "Ligat Ha'al";
            System.out.println(name);
            league = leagueManagementController.getLeagueByName(name);
            System.out.println("Please choose the season you want to add referees:");
            String year = "2020";
            System.out.println(year);
            season = leagueManagementController.getSeasonByYearInLeague(league, year);

            if (season != null) {
                System.out.println(leagueManagementController.showAllReferees());
                System.out.println("Please choose the referee you want to add to the season:");
                String fullName = "Yossi Levi";
                System.out.println(fullName);
                //referee = leagueManagementController.getRefereeByName(fullName);
                referee = leagueManagementController.getRefereeByUserName("yossiL");

                referees = new HashSet<>();
                referees.add((Referee) referee);
                assertFalse(leagueManagementController.assignRefereesToLeagueInSpecificSeason(league, season, referees, associationRepres));
                System.out.println("The referees wasn't added.");
            }
        }
    }

}