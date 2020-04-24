package com.SAS.acceptanceTests;

import com.SAS.User.*;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
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
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true);
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
        User coach = userController.createUser("EitanS", "Ei123", "Eitan Sela", UserType.COACH, true);
        ((Coach) coach).setLevel(3);
        ((Coach) coach).setFieldRole(FieldRole.MIDFIELDER);
        ((Coach) coach).setTeam(myTeam);
        myTeam.setCoach ((Coach) coach);

        team.getPersonalPage().setDescription(team.getPersonalPage().getDescription() + '\n' + "Coach: " + ((Coach) coach).getFullName());
        System.out.println("Please select an asset to edit: ");
        //show assets
        ((TeamOwner)teamOwner).getTeam().getPersonalPage().showPersonalPage();
        //enter editing mode
        if (teamManagement.enterEditingMode(teamOwner, ((TeamOwner) teamOwner).getTeam())) {
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

    //Todo - Chen
    @Test
    public void removeAsset() {
    }

    //Todo - Yaar
    @Test
    public void addAdditionalTeamOwner() {
    }

    //Todo - Chen
    @Test
    public void addTeamManager() {
    }

    //Todo - Chen
    @Test
    public void removeTeamManager() {
    }

    //Todo - Yaar
    @Test
    public void removeTeamOwner() {
    }

    //Todo
    @Test
    public void addTransactionToTeam() {
    }

    //Todo - Yaar
    @Test
    public void closeTeam() {
    }

    //Todo - Yaar
    @Test
    public void openTeam() {
    }
}