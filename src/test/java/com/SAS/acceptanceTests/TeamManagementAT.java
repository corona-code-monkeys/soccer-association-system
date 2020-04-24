package com.SAS.acceptanceTests;

import com.SAS.User.TeamOwner;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamAsset;
import com.SAS.teamManagenemt.TeamManagement;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TeamManagementAT {

    private TeamManagement teamManagement;
    private UserController userController;
    private Team team;
    private User teamOwner;

    @org.junit.Before
    public void setUp()  {
        userController = new UserController();
        teamManagement = new TeamManagement(userController);
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        team = new Team("Maccabi Tel Aviv", (TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
    }

    @org.junit.Test
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

    //Todo - Yaar
    @org.junit.Test
    public void addAdditionalTeamOwner() {
    }

    //Todo - Chen
    @org.junit.Test
    public void addTeamManager() {
    }

    //Todo - Chen
    @org.junit.Test
    public void removeTeamManager() {
    }

    //Todo - Yaar
    @org.junit.Test
    public void removeTeamOwner() {
    }

    //Todo
    @org.junit.Test
    public void addTransactionToTeam() {
    }

    //Todo - Yaar
    @org.junit.Test
    public void closeTeam() {
    }

    //Todo - Yaar
    @org.junit.Test
    public void openTeam() {
    }
}