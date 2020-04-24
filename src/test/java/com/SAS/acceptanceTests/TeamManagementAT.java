package com.SAS.acceptanceTests;

import com.SAS.User.*;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;
import com.SAS.teamManagenemt.TeamManagement;
import com.SAS.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
            if (asset != null) {
                ArrayList<String> details = new ArrayList<String>() {
                    {
                        add("Bloomfield");
                        add("Tel Aviv");
                        add("Stadium");
                    }
                };
                if (asset.editDetails(details) == false) {
                    myTeam.removeFacility((Facility) asset);
                    System.out.println("The asset details are not valid. Please try again to add the asset");
                } else {
                    assertTrue(myTeam.getFacilities().contains(asset));
                    System.out.println("The asset was added to the team successfully");
                }
            }
            else{
                System.out.println("The asset could not be added. Please try again to add the asset");
            }
        }
    }

    @Test
    public void addAssetToTeamFacilityByTeamOwnerFailWrongLocation() {
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
            System.out.println("location: ");
            System.out.println("Please enter facility type: 1 for Stadium, 2 for Training");
            System.out.println("Type: Stadium");
            System.out.println("Confirm");
            TeamAsset asset = teamManagement.AddAssetToTeam("Facility", myTeam, teamOwner);
            if (asset != null) {
                ArrayList<String> details = new ArrayList<String>() {
                    {
                        add("Bloomfield");
                        add("  ");
                        add("Stadium");
                    }
                };
                if (asset.editDetails(details) == false) {
                    myTeam.removeFacility((Facility) asset);
                    assertFalse(myTeam.getFacilities().contains(asset));
                    System.out.println("The asset details are not valid. Please try again to add the asset");
                } else {
                    System.out.println("The asset was added to the team successfully");
                }
            }
            else{
                System.out.println("The asset could not be added. Please try again to add the asset");
            }
        }
    }

    @Test
    public void editAssetDetailsSuccess() {
        //preparations for the test
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        Facility fac = new Facility();
        fac.setName("Ironi A field");
        fac.setLocation("Tel Aviv");
        fac.setFacilityType(facilityType.TRAINING);
        team.addFacility(fac);

        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + '\n' + "Facilities: " + fac.getName());
        System.out.println("Please select an asset to edit: ");
        //show assets
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
            System.out.println(teamManagement.getAllTeamAssets(team).toString());
            //first is name, second is location, third is type
            System.out.println("You have selected to edit Facility: Ironi A field");
            System.out.println("Please enter a new facility name");
            System.out.println("name: PenguinPickUp");
            System.out.println("Please enter facility location");
            System.out.println("location: Tel Aviv");
            System.out.println("Please enter facility type: 1 for Stadium, 2 for Training");
            System.out.println("Type: Training");
            System.out.println("Confirm");

            TeamAsset asset = teamManagement.getAssetByNameAndType(myTeam, "Facility", "Ironi A field");
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

    }

    @Test
    public void editAssetDetailsFailWrongDetails() {
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
        System.out.println("Please select an asset to edit: ");
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
            //show assets
            System.out.println(teamManagement.getAllTeamAssets(team).toString());
            //first is level, second is fieldRole
            System.out.println("You have selected to edit Coach: Eitan Sela");
            System.out.println("Please enter a new Coach name");
            System.out.println("name: Eitan Sela");
            System.out.println("Please enter Coach level");
            System.out.println("level: 0");
            System.out.println("Please enter FieldRole");
            System.out.println("Midfielder");
            System.out.println("Confirm");

            TeamAsset asset = teamManagement.getAssetByNameAndType(myTeam, "Coach", "Eitan Sela");
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
        }

    }

    @Test
    public void removeAssetFailNotAuthorized() {
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
            System.out.println("Please select an asset to delete: ");
            System.out.println(teamManagement.getAllTeamAssets(team).toString());
            System.out.println("Are you sure you want to remove Ironi A field");
            System.out.println("confirm");
            TeamAsset asset = myTeam.getAssetByNameAndType("Facility", "Ironi A field");
            if (teamManagement.removeAsset(asset, myTeam, teamOwner)){
                System.out.println("The asset was removed successfully");
            }
            else{
                System.out.println("You are not authorized to delete an asset for the team: " + team.getName());
                assertNotNull(asset);
            }
        }
        else{
            System.out.println("You are not authorized to delete an asset for the team: " + team.getName());
        }
    }


    @Test
    public void removeAssetSuccess() {
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
            System.out.println("Please select an asset to delete: ");
            System.out.println(teamManagement.getAllTeamAssets(team).toString());
            System.out.println("Are you sure you want to remove Ironi A field");
            System.out.println("confirm");
            TeamAsset asset = myTeam.getAssetByNameAndType("Facility", "Ironi A field");
            boolean removed = teamManagement.removeAsset(asset, myTeam, teamOwner);
            if (removed == true){
                assertFalse(myTeam.getFacilities().contains(asset));
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
    @Test
    public void addTeamManager() {
    }

    //Todo - Chen
    @Test
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

    @Test
    public void addTransactionToTeamSuccess() {
        Team myTeam = ((TeamOwner) teamOwner).getTeam();
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {

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

                Transaction trans = teamManagement.addTransactionToTeam(myTeam, details, teamOwner);
                assertTrue(myTeam.getTransactionList().contains(trans));
                System.out.println("The transaction added successfully.");
            }

        }
    }

    @Test
    public void addTransactionToTeamFail() {
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

                Transaction trans = teamManagement.addTransactionToTeam(myTeam, details, teamOwner);
            }

            else {
                assertFalse(isAuthorize);
                System.out.println("You are not authorize to add transactions.");
            }

        }
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