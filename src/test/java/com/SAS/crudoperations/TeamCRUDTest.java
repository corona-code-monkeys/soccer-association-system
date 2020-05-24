package com.SAS.crudoperations;

import com.SAS.dbstub.dbStub;
import com.SAS.team.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamCRUDTest {

    private dbStub db;

    @BeforeEach
    void setUp() {
        db = new dbStub();
        db.initializeDB();
        TeamCRUD.postTeam("Macabi");
        UsersCRUD.postUser("aviC", "cohen123", "Avi Choen", "rami@gmail.com","COACH");
    }

    @AfterEach
    void tearDown(){
        TeamCRUD.removeTeam("Macabi");
        UsersCRUD.deleteUser("aviC");
    }

    @Test
    void setCoachToTeam() {
        assertTrue(TeamCRUD.setCoachToTeam("aviC", "Macabi"));
    }

    @Test
    void removeCoachFromTeam() {
        TeamCRUD.setCoachToTeam("aviC", "Macabi");
        assertTrue(TeamCRUD.removeCoachFromTeam("aviC", "Macabi"));
    }

    @Test
    void setManagerToTeam() {
        assertTrue(TeamCRUD.setManagerToTeam("aviC", "Macabi"));
    }

    @Test
    void removeManagerFromTeam() {
        TeamCRUD.setManagerToTeam("aviC", "Macabi");
        assertTrue(TeamCRUD.removeManagerFromTeam("aviC", "Macabi"));
    }

    @Test
    void addOwnerToTeam() {
        assertTrue(TeamCRUD.addOwnerToTeam("aviC", "Macabi"));
    }

    @Test
    void removeOwnerFromTeam() {
        TeamCRUD.addOwnerToTeam("aviC", "Macabi");
        assertTrue(TeamCRUD.removeOwnerFromTeam("aviC", "Macabi"));
    }

    @Test
    void addAndRemoveFacilityTeam() {
        assertTrue(TeamCRUD.addOrEditFacilityToTeam("Macabi", "Terner", "STADIUM", "Beer-Sheva"));
        assertTrue(TeamCRUD.removeFacilityFromTeam("Macabi", "Terner"));

    }

    @Test
    void setTeamActivity() {
        assertTrue(TeamCRUD.setTeamActivity("Macabi", true));
    }

    @Test
    void setTeamRegistration() {
        assertTrue(TeamCRUD.setTeamRegistration("Macabi", true));
    }

    @Test
    void postTeamForUI() {
        UsersCRUD.deleteUser("Rami123");
        UsersCRUD.deleteUser("aviC1");
        UsersCRUD.deleteUser("NoamL1");
        UsersCRUD.deleteUser("Assasfi1");
        UsersCRUD.deleteUser("Rami12");
        UsersCRUD.deleteUser("Rami1");
        UsersCRUD.deleteUser("aviC");
        UsersCRUD.deleteUser("NoamL");
        UsersCRUD.deleteUser("Assasfi");
        TeamCRUD.removeTeam("Hapoel");


        UsersCRUD.postUser("aviC1", "123", "Avi Choen", "avi@gmail.com","COACH");
        UsersCRUD.postUser("Rami123", "1234", "Rami Levi", "rami@gmail.com","TEAM_OWNER");
        UsersCRUD.postUser("NoamL1", "12345", "Noam Levi", "123@gmail.com","TEAM_MANAGER");
        UsersCRUD.postUser("Assasfi1", "123456", "Assaf Nahum", "assa123@gmail.com","PLAYER");

        TeamCRUD.postTeam("Hapoel");
        TeamCRUD.addOwnerToTeam("Rami123", "Hapoel");
        UsersCRUD.setTeamOwnerDetails(UsersCRUD.getUserIdByUserName("Rami123"), "Hapoel");
        TeamCRUD.setCoachToTeam("aviC1", "Hapoel");
        UsersCRUD.setCoachDetails(UsersCRUD.getUserIdByUserName("aviC1"), "1", "STRIKER", "Hapoel");
        TeamCRUD.setManagerToTeam("NoamL1", "Hapoel");
        UsersCRUD.setTeamOwnerOrManagerDetails(UsersCRUD.getUserIdByUserName("NoamL1"), "Hapoel", "Rami123", "TEAM_MANAGER");
        TeamCRUD.addPlayerToTeam("Hapoel", "Assasfi1");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("Assasfi1"), "1992-12-20", "STRIKER", "Hapoel");
        Team team = TeamCRUD.getTeamByName("Hapoel");
        assertNotNull(team);

    }
}