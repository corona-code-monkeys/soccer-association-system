package com.SAS.Controllers.sasApplication;

import com.SAS.User.User;
import com.SAS.User.UserType;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.dbstub.dbStub;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SASApplicationUT {

    private SASApplication sasApp;
    private dbStub db;

    @BeforeEach
    public void setUp() {
        db = new dbStub();
        db.initializeDB();
        sasApp= new SASApplication();
        sasApp.createUser("AviCo", "Avi2468","Avi Cohen", "rami@gmail.com","PLAYER", true);
    }

    @AfterEach
    public void tearDown(){
        sasApp.deleteUser("AviCo");
    }


    @Test
    public void loginSuccess() {
        String loggedIn = sasApp.login("AviCo", "Avi2468", "");
        assertFalse(loggedIn.isEmpty());
    }

    @Test
    public void loginFailWrongPassword() {
        String loggedIn = sasApp.login("AviCo", "Avi246", "");
        assertTrue(loggedIn.isEmpty());
    }


    @Test
    public void editdetailsPlayerSuccess(){

        JSONObject details = new JSONObject();
        details.put("dateOfBirth", "1992-12-20");
        details.put("fieldRole", "Striker");
        details.put("team", "macabi");
        sasApp.editUserDetails("AviCo", details, "PLAYER");
        details.remove("fieldRole");
        details.put("fieldRole", "Midfielder");
        assertTrue(sasApp.editUserDetails("AviCo", details, "PLAYER"));
    }

    @Test
    public void insertFullPlayerSuccess(){
        JSONObject details = new JSONObject();
        details.put("dateOfBirth", "1992-12-20");
        details.put("fieldRole", "Striker");
        details.put("team", "macabi");
        assertTrue(sasApp.editUserDetails("AviCo", details, "PLAYER"));
    }


    @Test
    public void insertFullRefereeSuccess(){
       sasApp.createUser("BeniCoo", "Beni2468","Beni Cohen", "rami@gmail.com","REFEREE", true);
        JSONObject details = new JSONObject();
        details.put("level", "1");
        assertTrue(sasApp.editUserDetails("BeniCoo", details, "REFEREE"));
        sasApp.deleteUser("BeniCoo");
    }

    @Test
    public void insertFullCoachSuccess(){
        sasApp.createUser("BenCohen0", "Ben2468","Ben Cohen", "rami@gmail.com","COACH", true );
        JSONObject details = new JSONObject();
        details.put("level", "1");
        details.put("fieldRole", "Striker");
        details.put("team", "macabi tel aviv");
        assertTrue(sasApp.editUserDetails("BenCohen0", details, "COACH"));
        sasApp.deleteUser("BenCohen0");
    }

    @Test
    public void insertFullTeamOwnerSuccess(){
        sasApp.createUser("BenCohen12456", "Ben2468","Ben Cohen", "rami@gmail.com","TEAM_OWNER", true );
        JSONObject details = new JSONObject();
        details.put("team", "Macabi Tel Aviv");
        assertTrue(sasApp.editUserDetails("BenCohen12456", details, "TEAM_OWNER"));
        sasApp.deleteUser("BenCohen12456");
    }

    @Test
    public void insertFullTeamManagerSuccess(){
        sasApp.createUser("AviL1", "Avi2468","Avi Co", "rami@gmail.com","TEAM_OWNER", true);
        sasApp.createUser("RamCo3456", "Ram2468","Ram Cohen", "rami@gmail.com","TEAM_MANAGER", true);
        JSONObject details = new JSONObject();
        details.put("team", "Macabi Tel Aviv");
        details.put("nominatedBy", "AviL1");
        assertTrue(sasApp.editUserDetails("RamCo3456", details, "TEAM_MANAGER"));
        sasApp.deleteUser("AviL1");
        sasApp.deleteUser("RamCo3456");
    }


    @Test
    public void restoreComplexUserSuccess() {
        sasApp.createUser("AviL12", "Avi2468", "Avi Co", "rami@gmail.com","PLAYER", true);
        JSONObject details = new JSONObject();
        details.put("dateOfBirth", "1992-12-20");
        details.put("fieldRole", "Striker");
        details.put("team", "macabi tel aviv");
        sasApp.editUserDetails("AviL12", details, "PLAYER");
        assertNotNull(UsersCRUD.restoreRoleForUser(UsersCRUD.getUserIdByUserName("AviL12")));
        sasApp.deleteUser("AviL12");
    }
}