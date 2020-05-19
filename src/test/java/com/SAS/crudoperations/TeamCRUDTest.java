package com.SAS.crudoperations;

import com.SAS.dbstub.dbStub;
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
}