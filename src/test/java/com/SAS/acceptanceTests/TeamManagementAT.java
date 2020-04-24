package com.SAS.acceptanceTests;

import com.SAS.User.*;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;
import com.SAS.teamManagenemt.TeamManagement;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TeamManagementAT {

    private TeamManagement teamManagement;
    private UserController userController;
    private Team team;
    private User teamOwner;

    @Before
    public void setUp()  {
        userController = new UserController();
        teamManagement = new TeamManagement(userController);
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        team = new Team("Maccabi Tel Aviv", (TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
    }

    @Test
    public void addAssetToTeamFacilityByTeamOwnerSuccess() {
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){
            //first is name, second is location, third is type
            System.out.println("Please select asset type: 1 for Facility, 2 for Player, 3 for Coach");
            System.out.println("You have selected Facility");
            System.out.println("Please enter facility name");
            System.out.println("name: Bloomfield");
            System.out.println("Please enter facility location");
            System.out.println("location: Tel Aviv");
            System.out.println("Please enter facility type: 1 for Stadium, 2 for Training");
            System.out.println("Type: Stadium");
            System.out.println("Confirm");
            TeamAsset asset = teamManagement.AddAssetToTeam("Facility", myTeam, teamOwner);
            ArrayList<String> details = new ArrayList<String>(){
                {
                    add("Bloomfield");
                    add("Tel Aviv");
                    add("Stadium");
                }
            };
            asset.editDetails(details);

            assertTrue(myTeam.getFacilities().contains(asset));
            System.out.println("The asset was added to the team.");
        }

    }

    //Todo - Chen
    @org.junit.Test
    public void editAssetDetails() {
    }

    //Todo - Chen
    @org.junit.Test
    public void removeAsset() {
    }

    @Test
    public void addAdditionalTeamOwnerSuccess() {
        //add player to the team
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can nominate team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)){
                System.out.println("These are the optional nominees for team owner:");
                System.out.println(teamManagement.showOptionalNomineesForTeamOwner(teamOwner, myTeam));
                System.out.println("Please enter the full name of the user you want to select");
                String fullName = "Rami Oron";
                System.out.println(fullName);
                User newTeamOwner = teamManagement.getUserForTeamOwnerNominate(fullName, team);

                //checks if the user that chosen is legal
                if (newTeamOwner  != null) {
                    newTeamOwner = teamManagement.addAdditionalTeamOwner(newTeamOwner, team, teamOwner);

                    if (newTeamOwner != null) {
                        System.out.println("The user " + fullName + " was nominated to be a team owner of the team " + myTeam.getName() + ".");
                        assertTrue(myTeam.getOwners().contains(newTeamOwner));
                    }
                }
            }

            else {
                System.out.println("The user is unauthorized to nominate team owner");
            }

        }

    }

    @Test
    public void addAdditionalTeamOwnerFail() {
        //add player to the team
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can nominate team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)){
                System.out.println("These are the optional nominees for team owner:");
                System.out.println(teamManagement.showOptionalNomineesForTeamOwner(teamOwner, myTeam));
                System.out.println("Please enter the full name of the user you want to select");
                String fullName = "Ram Oren";
                System.out.println(fullName);
                User newTeamOwner = teamManagement.getUserForTeamOwnerNominate(fullName, team);

                //checks if the user that chosen is legal
                if (newTeamOwner  != null) {
                    newTeamOwner = teamManagement.addAdditionalTeamOwner(newTeamOwner, team, teamOwner);

                    if (newTeamOwner != null) {
                        System.out.println("The user " + fullName + " was nominated to be a team owner of the team " + myTeam.getName() + ".");
                    }
                }

                else {
                    System.out.println("The user you entered cannot be nominated to be owner of the team");
                    assertNull(newTeamOwner);
                }
            }
            else {
                System.out.println("The user is unauthorized to nominate team owner");
            }

        }

    }

    //Todo - Chen
    @org.junit.Test
    public void addTeamManager() {
    }

    //Todo - Chen
    @org.junit.Test
    public void removeTeamManager() {
    }

    @Test
    public void removeTeamOwnerSuccess() {
        //add owner to the team
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);
        teamManagement.addAdditionalTeamOwner(player, team, teamOwner);

        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){ ;

            //checks if the user can remove team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)) {
                System.out.println("These are the team owners of " + team.getName() + ":");
                System.out.println(teamManagement.showTeamOwners(myTeam));
                System.out.println("Please enter the full name of the user you want to remove");
                String fullName = "Rami Oron";
                System.out.println(fullName);
                User removedTeamOwner = teamManagement.getTeamOwnerUserByName(fullName, team);

                //checks if the user that chosen is legal
                if (removedTeamOwner  != null) {
                    removedTeamOwner = teamManagement.removeTeamOwner(removedTeamOwner, team, teamOwner);

                    if (removedTeamOwner != null && !(removedTeamOwner instanceof TeamOwner)) {
                        assertFalse(myTeam.getOwners().contains(removedTeamOwner));
                        System.out.println("The user " + fullName + " has been removed from being a team owner of the team " + myTeam.getName() + ".");
                    }
                }
            }

            else {
                System.out.println("The user is unauthorized to remove team owner");
            }

        }
    }

    @Test
    public void removeTeamOwnerFail() {
        //add owner to the team
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        User playerRami = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) playerRami);
        ((Player)playerRami).setTeam(team);
        playerRami = teamManagement.addAdditionalTeamOwner(playerRami, team, teamOwner);
        User playerZohar = userController.createUser("ZoharC", "Zohar321", "Zohar Catz", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) playerZohar);
        ((Player)playerZohar).setTeam(team);
        playerZohar = teamManagement.addAdditionalTeamOwner(playerZohar, team, playerRami);

        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){ ;

            //checks if the user can remove team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)) {
                System.out.println("These are the team owners of " + team.getName() + ":");
                System.out.println(teamManagement.showTeamOwners(myTeam));
                System.out.println("Please enter the full name of the user you want to remove");
                String fullName = "Zohar Catz";
                System.out.println(fullName);
                User removedTeamOwner = teamManagement.getTeamOwnerUserByName(fullName, team);

                //checks if the user that chosen is legal
                if (removedTeamOwner  != null) {
                    removedTeamOwner = teamManagement.removeTeamOwner(removedTeamOwner, team, teamOwner);

                    if (removedTeamOwner != null && (removedTeamOwner instanceof TeamOwner)) {
                        assertTrue(myTeam.getOwners().contains(removedTeamOwner));
                        System.out.println("The user " + fullName + " hasn't been removed from being a team owner of the team " + myTeam.getName() + ".");
                    }
                }
            }

        }
    }

    //Todo
    @org.junit.Test
    public void addTransactionToTeam() {
    }

    @Test
    public void closeTeamSuccess() {
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to close the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.closeTeam(myTeam, teamOwner));

                assertFalse(myTeam.isActive());
                System.out.println("The team has been closed.");
            }

            else {
                System.out.println("The user is unauthorized to close the team " + myTeam.getName());
            }
        }

    }

    @Test
    public void closeTeamFail() {
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to close the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.closeTeam(myTeam, teamOwner));

                assertFalse(myTeam.isActive());
            }

            else {
                System.out.println("The user is unauthorized to close the team " + myTeam.getName());
            }
        }

    }


    @Test
    public void openTeamSuccess() {
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //close the team
        teamManagement.closeTeam(myTeam, teamOwner);

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to open the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.openTeam(myTeam, teamOwner));

                assertTrue(myTeam.isActive());
                System.out.println("The team has been opened.");
            }

            else {
                System.out.println("The user is unauthorized to open the team " + myTeam.getName());
            }
        }
    }

    @Test
    public void openTeamFail() {
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to open the team please enter confirm");
                System.out.println("confirm");
                assertFalse(teamManagement.openTeam(myTeam, teamOwner));
                assertTrue(myTeam.isActive());
            }

            else {
                System.out.println("The user is unauthorized to open the team " + myTeam.getName());
            }
        }
    }
}