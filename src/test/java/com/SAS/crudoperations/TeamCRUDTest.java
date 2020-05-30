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


        UsersCRUD.postUser("aviC1", "123", "Avi Cohen", "avi@gmail.com","COACH");
        UsersCRUD.postUser("Rami123", "1234", "Rami Levi", "rami@gmail.com","TEAM_OWNER");
        UsersCRUD.postUser("NoamL1", "12345", "Noam Levi", "123@gmail.com","TEAM_MANAGER");
        UsersCRUD.postUser("Assasfi1", "123456", "Assaf Nahum", "assa123@gmail.com","PLAYER");

        TeamCRUD.postTeam("Hapoel Haifa");
        TeamCRUD.addOwnerToTeam("Rami123", "Hapoel Haifa");
        UsersCRUD.setTeamOwnerDetails(UsersCRUD.getUserIdByUserName("Rami123"), "Hapoel Haifa");
        TeamCRUD.setCoachToTeam("aviC1", "Hapoel Haifa");
        UsersCRUD.setCoachDetails(UsersCRUD.getUserIdByUserName("aviC1"), "1", "STRIKER", "Hapoel Haifa");
        TeamCRUD.setManagerToTeam("NoamL1", "Hapoel Haifa");
        UsersCRUD.setTeamOwnerOrManagerDetails(UsersCRUD.getUserIdByUserName("NoamL1"), "Hapoel Haifa", "Rami123", "TEAM_MANAGER");
        TeamCRUD.addPlayerToTeam("Hapoel Haifa", "Assasfi1");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("Assasfi1"), "1992-12-20", "STRIKER", "Hapoel Haifa");
        Team team = TeamCRUD.getTeamByName("Hapoel Haifa");
        assertNotNull(team);

        UsersCRUD.postUser("MosheH", "123", "Moshe Hogeg", "moshe@gmail.com","TEAM_OWNER");
        UsersCRUD.postUser("BeniD", "1234", "Beni Dahan", "beni@gmail.com","COACH");
        UsersCRUD.postUser("RonnyL", "12345", "Ronny Levy", "ronnylevy@gmail.com","TEAM_MANAGER");
        UsersCRUD.postUser("ItamarN", "123456", "Itamar Nitzan", "itamarNi@gmail.com","PLAYER");
        UsersCRUD.postUser("AntoineC", "123456", "Antoine Conte", "AntoineC@gmail.com","PLAYER");
        UsersCRUD.postUser("MaximG", "123456", "Maxim Grechkin", "MaximG@gmail.com","PLAYER");
        UsersCRUD.postUser("OrZ", "123456", "Or Zehavi", "OrZ@gmail.com","PLAYER");
        UsersCRUD.postUser("IdanV", "123456", "Idan Vered", "idanV@gmail.com","PLAYER");

        TeamCRUD.postTeam("Beitar Jerusalem");
        TeamCRUD.addOwnerToTeam("MosheH", "Beitar Jerusalem");
        UsersCRUD.setTeamOwnerDetails(UsersCRUD.getUserIdByUserName("MosheH"), "Beitar Jerusalem");
        TeamCRUD.setCoachToTeam("BeniD", "Beitar Jerusalem");
        UsersCRUD.setCoachDetails(UsersCRUD.getUserIdByUserName("BeniD"), "1", "STRIKER", "Beitar Jerusalem");
        TeamCRUD.setManagerToTeam("RonnyL", "Beitar Jerusalem");
        UsersCRUD.setTeamOwnerOrManagerDetails(UsersCRUD.getUserIdByUserName("RonnyL"), "Beitar Jerusalem", "MosheH", "TEAM_MANAGER");
        TeamCRUD.addPlayerToTeam("Beitar Jerusalem", "ItamarN");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("ItamarN"), "1994-01-20", "GOAL_KEEPER", "Beitar Jerusalem");
        TeamCRUD.addPlayerToTeam("Beitar Jerusalem", "AntoineC");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("AntoineC"), "1993-08-23", "DEFENDER", "Beitar Jerusalem");
        TeamCRUD.addPlayerToTeam("Beitar Jerusalem", "MaximG");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("MaximG"), "1995-07-14", "DEFENDER", "Beitar Jerusalem");
        TeamCRUD.addPlayerToTeam("Beitar Jerusalem", "OrZ");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("OrZ"), "1996-04-10", "DEFENDER", "Beitar Jerusalem");
        TeamCRUD.addPlayerToTeam("Beitar Jerusalem", "IdanV");
        UsersCRUD.setPlayerDetails(UsersCRUD.getUserIdByUserName("IdanV"), "1996-11-15", "MIDFIELDER", "Beitar Jerusalem");
        Team beitar = TeamCRUD.getTeamByName("Beitar Jerusalem");
        assertNotNull(beitar);

    }
}