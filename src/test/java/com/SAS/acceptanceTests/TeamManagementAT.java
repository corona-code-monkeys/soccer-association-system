package com.SAS.acceptanceTests;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.User.*;
import com.SAS.crudoperations.TeamCRUD;
import com.SAS.dbstub.dbStub;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;
import com.SAS.teamManagenemt.TeamManagement;
import com.SAS.transaction.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class TeamManagementAT {

    private TeamManagement teamManagement;
    private UserController userController;
    private Team team;
    private User teamOwner;
    private SASApplication sasApp;
    private dbStub db;

    @BeforeEach
    public void setUp()  {
        db = new dbStub();
        db.initializeDB();
        sasApp= new SASApplication();

        userController = new UserController();
        teamManagement = new TeamManagement(userController);
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        team = new Team("Maccabi Tel Aviv", (TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
        TeamCRUD.postTeam("Maccabi Tel Aviv");
        teamManagement.enterTeamPage("Maccabi Tel Aviv");
    }

    @AfterEach
    public void tearDown() {
        TeamCRUD.removeTeam("Maccabi Tel Aviv");
    }

    @Test
    public void addAssetToTeamFacilityByTeamOwnerSuccess() {
        long startTime = System.nanoTime();
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){
            System.out.println("--Select to add a team asset--");
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
            ArrayList<String> details = new ArrayList<String>() {
                {
                    add("Bloomfield");
                    add("Tel Aviv");
                    add("Stadium");
                }
            };
            TeamAsset asset = teamManagement.AddAssetToTeam("Facility", teamOwner, details);
            if (asset != null) {
                    assertTrue(myTeam.getFacilities().contains(asset));
                    System.out.println("The asset was added to the team successfully");
            }
            else{
                System.out.println("The asset could not be added. Please try again to add the asset");
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addAssetToTeamFacilityByTeamOwnerFailWrongLocation() {
        long startTime = System.nanoTime();
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){
            System.out.println("--Select to add a team asset--");
            //first is name, second is location, third is type
            System.out.println("Please select asset type: 1 for Facility, 2 for Player, 3 for Coach");
            System.out.println("You have selected Facility");
            System.out.println("Please enter facility name");
            System.out.println("name: Bloomfield");
            System.out.println("Please enter facility location");
            System.out.println("location: ");
            System.out.println("Please enter facility type: 1 for Stadium, 2 for Training");
            System.out.println("Type: Stadium");
            System.out.println("Confirm");
            ArrayList<String> details = new ArrayList<String>() {
                {
                    add("Bloomfield");
                    add(null);
                    add("Stadium");
                }
            };
            TeamAsset asset = teamManagement.AddAssetToTeam("Facility", teamOwner, details);
            if (asset != null) {
                myTeam.removeFacility((Facility) asset);
                assertFalse(myTeam.getFacilities().contains(asset));
                System.out.println("The asset details are not valid. Please try again to add the asset");
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void editAssetDetailsSuccess() {
        long startTime = System.nanoTime();
        //preparations for the test
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        Facility fac = new Facility();
        fac.setName("Ironi A field");
        fac.setLocation("Tel Aviv");
        fac.setFacilityType(facilityType.TRAINING);
        team.addFacility(fac);

        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + '\n' + "Facilities: " + fac.getName());
        //show assets
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        System.out.println("--Select to edit a team asset--");
        System.out.println("Please select an asset to edit: ");

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
            System.out.println(teamManagement.getAllTeamAssets().toString());
            //first is name, second is location, third is type
            System.out.println("You have selected to edit Facility: Ironi A field");
            System.out.println("Please enter a new facility name");
            System.out.println("name: PenguinPickUp");
            System.out.println("Please enter facility location");
            System.out.println("location: Tel Aviv");
            System.out.println("Please enter facility type: 1 for Stadium, 2 for Training");
            System.out.println("Type: Training");
            System.out.println("Confirm");

            TeamAsset asset = teamManagement.getAssetByNameAndType("Facility", "Ironi A field");
            if (asset != null) {
                ArrayList<String> details = new ArrayList<String>() {
                    {
                        add("PenguinPickUp");
                        add("Tel Aviv");
                        add("Training");
                    }
                };
                Boolean edited = teamManagement.editAssetDetails(asset, details);
                assertTrue(edited);
                System.out.println("The asset has been changed");

            } else {
                System.out.println("Invalid asset, please try again to edit the asset");
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void editAssetDetailsFailWrongDetails() {
        long startTime = System.nanoTime();
        //preparations for the test
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        User coach = userController.createUser("EitanS", "Ei123", "Eitan Sela", UserType.COACH, true, null);
        ((Coach) coach).setLevel(3);
        ((Coach) coach).setFieldRole(FieldRole.MIDFIELDER);
        ((Coach) coach).setTeam(myTeam);
        myTeam.setCoach ((Coach) coach);

        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + '\n' + "Coach: " + ((Coach) coach).getFullName());
        //show personal page
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        System.out.println("--Select to edit a team asset--");
        System.out.println("Please select an asset to edit: ");
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
            //show assets
            System.out.println(teamManagement.getAllTeamAssets().toString());
            //first is level, second is fieldRole
            System.out.println("You have selected to edit Coach: Eitan Sela");
            System.out.println("Please enter a new Coach name");
            System.out.println("name: Eitan Sela");
            System.out.println("Please enter Coach level");
            System.out.println("level: 0");
            System.out.println("Please enter FieldRole");
            System.out.println("Midfielder");
            System.out.println("Confirm");

            TeamAsset asset = teamManagement.getAssetByNameAndType( "Coach", "Eitan Sela");
            if (asset != null) {
                ArrayList<String> details = new ArrayList<String>() {
                    {
                        add("PenguinPickUp");
                        add("Midfielder");
                    }
                };
                Boolean edited = teamManagement.editAssetDetails(asset, details);
                assertFalse(edited);
                System.out.println("The asset details are not valid. Please try again to add the asset");

            } else {
                System.out.println("Invalid asset, please try again to edit the asset");
            }
        } else{
            System.out.println("You are not authorized to edit the team");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void removeAssetFailNotAuthorized() {
        long startTime = System.nanoTime();
        //preparations
        //Add asset to team
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        Facility fac = new Facility();
        fac.setName("Ironi A field");
        fac.setLocation("Tel Aviv");
        fac.setFacilityType(facilityType.TRAINING);
        myTeam.addFacility(fac);
        myTeam.getPersonalPage().setDescription(myTeam.getPersonalPage().getDescription() + '\n' + "Facilities: " + fac.getName());

        //show personal page
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //add new Team and make him its owner
        Team otherTeam = new Team("Maccabi Beer Sheva", (TeamOwner)teamOwner);
        ((TeamOwner)teamOwner).setTeam(otherTeam);

        //enter editing mode
        boolean canEdit = teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam());
        if (canEdit) {
            //show assets
            System.out.println("--Select to delete a team asset--");
            System.out.println("Please select an asset to delete: ");
            System.out.println(teamManagement.getAllTeamAssets().toString());
            System.out.println("Are you sure you want to remove Ironi A field");
            System.out.println("confirm");
            TeamAsset asset = myTeam.getAssetByNameAndType("Facility", "Ironi A field");
            if (teamManagement.removeAsset(asset, teamOwner)){
                System.out.println("The asset was removed successfully");
            }
            else{
                System.out.println("You are not authorized to delete an asset for the team: " + team.getName());
                assertTrue(myTeam.getAllAssets().contains(asset));
            }
        }
        else{
            System.out.println("You are not authorized to edit the team: " + team.getName());
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }


    @Test
    public void removeAssetSuccess() {
        long startTime = System.nanoTime();
        //preparations
        //Add asset to team
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        Facility fac = new Facility();
        fac.setName("Ironi A field");
        fac.setLocation("Tel Aviv");
        fac.setFacilityType(facilityType.TRAINING);
        fac.setTeam(myTeam);
        myTeam.addFacility(fac);
        myTeam.getPersonalPage().setDescription(myTeam.getPersonalPage().getDescription() + '\n' + "Facilities: " + fac.getName());

        //show personal page
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        boolean canEdit = teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam());
        if (canEdit) {
            //show assets
            System.out.println("--Select to delete a team asset--");
            System.out.println("Please select an asset to delete: ");
            System.out.println(teamManagement.getAllTeamAssets().toString());
            System.out.println("Are you sure you want to remove Ironi A field");
            System.out.println("confirm");
            TeamAsset asset = myTeam.getAssetByNameAndType("Facility", "Ironi A field");
            boolean removed = teamManagement.removeAsset(asset, teamOwner);
            if (removed == true){
                assertFalse(myTeam.getAllAssets().contains(asset));
                System.out.println("The asset was removed successfully");
                asset = null;
            }
            else{
                System.out.println("You are not authorized to delete an asset for the team: " + team.getName());
            }
        }
        else{
            System.out.println("You are not authorized to delete an asset for the team: " + team.getName());
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }


    @Test
    public void addAdditionalTeamOwnerSuccess() {
        long startTime = System.nanoTime();
        //add player to the team
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){
            System.out.println("--Select to add another team owner--");
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
                    newTeamOwner = teamManagement.addAdditionalTeamOwner(newTeamOwner, teamOwner);

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
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addAdditionalTeamOwnerFail() {
        long startTime = System.nanoTime();
        //add player to the team
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){
            System.out.println("--Select to add another team owner--");
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
                    newTeamOwner = teamManagement.addAdditionalTeamOwner(newTeamOwner,  teamOwner);

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
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addTeamManagerSuccess() {
        long startTime = System.nanoTime();
        //preparations- create a player
        User player = userController.createUser("ItayC", "ItAY1234", "Itay Cohen", UserType.PLAYER, true, null);
        ((Player)player).setFieldRole(FieldRole.MIDFIELDER);
        ((Player)player).setTeam(team);
        ((Player)player).setDateOfBirth(LocalDate.parse("1990-12-01"));
        team.addPlayerToTeam((Player)player);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        myTeam.getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, myTeam) && teamManagement.canAddRemoveTeamManager(teamOwner)) {
            System.out.println("--Select to add a team manager--");
            System.out.println("Please select a nominee from the followings:");
            System.out.println(teamManagement.showOptionalNomineesForTeamManager());
            System.out.println("Selected: Itay Cohen");
            System.out.println("Would you like to give the new team manager editing assets privileges? 1 to give privileges, otherwise 0");
            String approval = "1";
            System.out.println(approval);
            boolean givePrivileges = approval.equals("1") ? true : false;
            User toMakeTeamOwner = teamManagement.getUserForTeamManagerNominees("Itay Cohen");
            if (toMakeTeamOwner !=null){
                toMakeTeamOwner = teamManagement.addTeamManager(toMakeTeamOwner, teamOwner, givePrivileges);
                assertTrue(toMakeTeamOwner instanceof TeamManager);
                System.out.println(((TeamManager)toMakeTeamOwner).getFullName() + " was nominated as team manager");
            }else{
                System.out.println("The team manager could not be nominated");
            }
        }
        else {
            System.out.println("You do not have the privileges to edit the team");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addTeamManagerFailWrongName() {
        long startTime = System.nanoTime();
        //preparations- create a player
        User player = userController.createUser("ItayC", "ItAY1234", "Itay Cohen", UserType.PLAYER, true, null);
        ((Player)player).setFieldRole(FieldRole.MIDFIELDER);
        ((Player)player).setTeam(team);
        ((Player)player).setDateOfBirth(LocalDate.parse("1990-12-01"));
        team.addPlayerToTeam((Player)player);

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        myTeam.getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, myTeam) && teamManagement.canAddRemoveTeamManager(teamOwner)) {
            System.out.println("--Select to add a team manager--");
            System.out.println("Please select a nominee from the followings:");
            System.out.println(teamManagement.showOptionalNomineesForTeamManager());
            System.out.println("Selected: ");
            System.out.println("Would you like to give the new team manager editing assets privileges? 1 to give privileges, otherwise 0");
            String approval = "1";
            System.out.println(approval);
            boolean givePrivileges = approval.equals("1") ? true : false;
            User toMakeTeamOwner = teamManagement.getUserForTeamManagerNominees("");
            if (toMakeTeamOwner !=null){
                toMakeTeamOwner = teamManagement.addTeamManager(toMakeTeamOwner, teamOwner, givePrivileges);
                System.out.println(((TeamManager)toMakeTeamOwner).getFullName() + " was nominated as team manager");
            }else{
                assertFalse(toMakeTeamOwner instanceof TeamManager);
                System.out.println("The team manager could not be nominated");
            }
        }
        else {
            System.out.println("You do not have the privileges to edit the team");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void removeTeamManagerSuccess() {
        long startTime = System.nanoTime();
        //preparations- create a player and nominate it to team manager
        User playerToBeManager = userController.createUser("ItayC", "ItAY1234", "Itay Cohen", UserType.PLAYER, true, null);
        ((Player)playerToBeManager).setFieldRole(FieldRole.MIDFIELDER);
        ((Player)playerToBeManager).setTeam(team);
        ((Player)playerToBeManager).setDateOfBirth(LocalDate.parse("1990-12-01"));
        team.addPlayerToTeam((Player)playerToBeManager);
        playerToBeManager = teamManagement.addTeamManager(playerToBeManager, teamOwner, true);
        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + ", " + "Team manager: " + ((Player)playerToBeManager).getFullName());

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        myTeam.getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, myTeam) && teamManagement.canAddRemoveTeamManager(teamOwner)) {
            System.out.println("--Select to remove the team manager--");
            System.out.println("Are you sure you want to remove the team manager: " + team.getManager().getFullName());
            System.out.println("confirm");
            playerToBeManager = teamManagement.removeTeamManager(playerToBeManager, teamOwner);
            assertNotEquals(team.getManager(), playerToBeManager);
            System.out.println("The team manager was removed successfully");
        }
        else {
            System.out.println("The user do not have sufficient privileges");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }


    @Test
    public void removeTeamManagerFailNominatedByAnotherTeamOwner() {
        long startTime = System.nanoTime();
        //preparations- create a player and nominate it to team manager
        User playerToBeManager = userController.createUser("ItayC", "ItAY1234", "Itay Cohen", UserType.PLAYER, true, null);
        ((Player)playerToBeManager).setFieldRole(FieldRole.MIDFIELDER);
        ((Player)playerToBeManager).setTeam(team);
        ((Player)playerToBeManager).setDateOfBirth(LocalDate.parse("1990-12-01"));
        team.addPlayerToTeam((Player)playerToBeManager);

        //another team owner
        User playerToBeTeamOwner = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) playerToBeTeamOwner);
        ((Player)playerToBeTeamOwner).setTeam(team);
        playerToBeTeamOwner= teamManagement.addAdditionalTeamOwner(playerToBeTeamOwner, teamOwner);

        playerToBeManager = teamManagement.addTeamManager(playerToBeManager, playerToBeTeamOwner, true);
        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + ", " + "Team manager: " + ((Player)playerToBeManager).getFullName());

        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        myTeam.getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, myTeam) && teamManagement.canAddRemoveTeamManager(teamOwner)) {
            System.out.println("--Select to remove the team manager--");
            System.out.println("Are you sure you want to remove the team manager: " + team.getManager().getFullName());
            System.out.println("confirm");
            playerToBeManager = teamManagement.removeTeamManager(playerToBeManager, teamOwner);
            if (! (playerToBeManager instanceof TeamManager)) {
                System.out.println("The team manager was removed successfully");
            }
            else{
                assertEquals(team.getManager(), playerToBeManager);
                System.out.println("The user is unauthorized to remove the team manager");
            }
        }
        else {
            System.out.println("The user do not have sufficient privileges");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }


    @Test
    public void removeTeamOwnerSuccess() {
        //add owner to the team
        long startTime = System.nanoTime();
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        User player = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) player);
        ((Player)player).setTeam(team);
        teamManagement.addAdditionalTeamOwner(player, teamOwner);

        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){ ;
            System.out.println("--Select to remove a team owner--");
            //checks if the user can remove team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)) {
                System.out.println("These are the team owners of " + team.getName() + ":");
                System.out.println(teamManagement.showTeamOwners());
                System.out.println("Please enter the full name of the user you want to remove");
                String fullName = "Rami Oron";
                System.out.println(fullName);
                User removedTeamOwner = teamManagement.getTeamOwnerUserByName(fullName);

                //checks if the user that chosen is legal
                if (removedTeamOwner  != null) {
                    removedTeamOwner = teamManagement.removeTeamOwner(removedTeamOwner, teamOwner);

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
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void removeTeamOwnerFail() {
        long startTime = System.nanoTime();
        //add owner to the team
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        User playerRami = userController.createUser("RamiO", "Rami321", "Rami Oron", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) playerRami);
        ((Player)playerRami).setTeam(team);
        playerRami = teamManagement.addAdditionalTeamOwner(playerRami, teamOwner);
        User playerZohar = userController.createUser("ZoharC", "Zohar321", "Zohar Catz", UserType.PLAYER, true, null);
        team.addPlayerToTeam((Player) playerZohar);
        ((Player)playerZohar).setTeam(team);
        playerZohar = teamManagement.addAdditionalTeamOwner(playerZohar, playerRami);

        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){ ;
            System.out.println("--Select to remove a team owner--");
            //checks if the user can remove team owner
            if (teamManagement.canAddRemoveTeamOwner(teamOwner)) {
                System.out.println("These are the team owners of " + team.getName() + ":");
                System.out.println(teamManagement.showTeamOwners());
                System.out.println("Please enter the full name of the user you want to remove");
                String fullName = "Zohar Catz";
                System.out.println(fullName);
                User removedTeamOwner = teamManagement.getTeamOwnerUserByName(fullName);

                //checks if the user that chosen is legal
                if (removedTeamOwner  != null) {
                    removedTeamOwner = teamManagement.removeTeamOwner(removedTeamOwner, teamOwner);

                    if (removedTeamOwner != null && (removedTeamOwner instanceof TeamOwner)) {
                        assertTrue(myTeam.getOwners().contains(removedTeamOwner));
                        System.out.println("The user " + fullName + " hasn't been removed from being a team owner of the team " + myTeam.getName() + ".");
                    }
                }
            }

        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addTransactionToTeamSuccess() {
        long startTime = System.nanoTime();
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
            System.out.println("--Select to add a new transaction--");

            //checks is thr user can add transaction
            if (teamManagement.canAddTransaction(teamOwner)) {

                ///first amount, second type, third date and last description
                System.out.println("Please select transaction type: 1 for Income, 2 for Expense");
                String type = "Income";
                System.out.println("You have selected Income");
                System.out.println("Please enter the amount");
                String amount = "1200";
                System.out.println(amount);
                System.out.println("Please enter the date");
                String date = "2020-04-12";
                System.out.println(date);
                System.out.println("Please enter the description");
                String desc = "Stadium maintenance";
                System.out.println(desc);

                List<String> details = new LinkedList<String>() {
                    {
                        add(amount);
                        add(type);
                        add(date);
                        add(desc);
                    }
                };

                Transaction trans = teamManagement.addTransactionToTeam(details, teamOwner);
                assertTrue(myTeam.getTransactionList().contains(trans));
                System.out.println("The transaction added successfully.");
            }

        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void addTransactionToTeamFail() {
        long startTime = System.nanoTime();
        Team myTeam = ((TeamOwner) teamOwner).getTeam();

        //add team manager
        User teamManager = userController.createUser("Rami Levi", "RamiL123", "Rami Levi", UserType.TEAM_MANAGER, true, null);
        ((TeamManager)teamManager).setTeam(myTeam);
        myTeam.setTeamManager((TeamManager) teamManager);
        myTeam.getPersonalPage().setDescription(myTeam.getPersonalPage().getDescription() + "\n Team Manager: " + ((TeamManager) teamManager).getFullName());

        //show page
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamManager, ((TeamManager) teamManager).getTeam())) {
            System.out.println("--Select to add a new transaction--");

            //checks is thr user can add transaction
            boolean isAuthorize = teamManagement.canAddTransaction(teamManager);
            if (isAuthorize) {

                ///first amount, second type, third date and last description
                System.out.println("Please select transaction type: 1 for Income, 2 for Expense");
                String type = "Income";
                System.out.println("You have selected Income");
                System.out.println("Please enter the amount");
                String amount = "1200";
                System.out.println(amount);
                System.out.println("Please enter the date");
                String date = "2020-04-12";
                System.out.println(date);
                System.out.println("Please enter the description");
                String desc = "Stadium maintenance";
                System.out.println(desc);

                List<String> details = new LinkedList<String>() {
                    {
                        add(amount);
                        add(type);
                        add(date);
                        add(desc);
                    }
                };

                Transaction trans = teamManagement.addTransactionToTeam(details, teamOwner);
            }

            else {
                assertFalse(isAuthorize);
                System.out.println("You are not authorize to add transactions.");
            }

        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void closeTeamSuccess() {
        long startTime = System.nanoTime();
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to close the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.closeTeam(teamOwner));

                assertFalse(myTeam.isActive());
                System.out.println("The team has been closed.");
            }

            else {
                System.out.println("The user is unauthorized to close the team " + myTeam.getName());
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void closeTeamFail() {
        long startTime = System.nanoTime();
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to close the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.closeTeam(teamOwner));

                assertFalse(myTeam.isActive());
            }

            else {
                System.out.println("The user is unauthorized to close the team " + myTeam.getName());
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }


    @Test
    public void openTeamSuccess() {
        long startTime = System.nanoTime();
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //close the team
        teamManagement.closeTeam(teamOwner);

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to open the team please enter confirm");
                System.out.println("confirm");
                assertTrue(teamManagement.openTeam(teamOwner));

                assertTrue(myTeam.isActive());
                System.out.println("The team has been opened.");
            }

            else {
                System.out.println("The user is unauthorized to open the team " + myTeam.getName());
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    @Test
    public void openTeamFail() {
        long startTime = System.nanoTime();
        //show page
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();

        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())){

            //checks if the user can close the team
            if (teamManagement.canCloseOpenTeam(teamOwner)) {
                System.out.println("If you want to open the team please enter confirm");
                System.out.println("confirm");
                assertFalse(teamManagement.openTeam(teamOwner));
                assertTrue(myTeam.isActive());
            }

            else {
                System.out.println("The user is unauthorized to open the team " + myTeam.getName());
            }
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }
}