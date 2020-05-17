package com.SAS.Controllers.sasApplication;

import com.SAS.User.User;
import com.SAS.User.UserType;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.dbstub.dbStub;
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
        sasApp.createUser("AviCo", "Avi2468","Avi Cohen", UserType.PLAYER, true, null );
    }

    @AfterEach
    public void tearDown(){
        sasApp.deleteUser("AviCo");
    }


    @Test
    public void loginSuccess() {
        Boolean loggedIn = sasApp.login("AviCo", "Avi2468");
        assertTrue(loggedIn);
    }

    @Test
    public void loginFailWrongPassword() {
        Boolean loggedIn = sasApp.login("AviCo", "Avi246");
        assertFalse(loggedIn);
    }


    @Test
    public void editdetailsPlayerSuccess(){
        List<String> details = new LinkedList<String>(){
            {
                add("1992-12-20");
                add("Striker");
                add("macabi");
            }
        };
        sasApp.editUserDetails("AviCo", details, "PLAYER");
        details.remove(1);
        details.add(1, "Midfielder");
        assertTrue(sasApp.editUserDetails("AviCo", details, "PLAYER"));
    }

    @Test
    public void insertFullPlayerSuccess(){
        List<String> details = new LinkedList<String>(){
            {
                add("1992-12-20");
                add("Striker");
                add("macabi");
            }
        };
        assertTrue(sasApp.editUserDetails("AviCo", details, "PLAYER"));
    }


    @Test
    public void insertFullRefereeSuccess(){
       sasApp.createUser("BeniCoo", "Beni2468","Beni Cohen", UserType.REFEREE, true, null );
        List<String> details = new LinkedList<String>(){
            {
                add("1");
            }
        };
        assertTrue(sasApp.editUserDetails("BeniCoo", details, "REFEREE"));
        sasApp.deleteUser("BeniCoo");
    }

    @Test
    public void insertFullCoachSuccess(){
        sasApp.createUser("BenCohen0", "Ben2468","Ben Cohen", UserType.COACH, true, null );
        List<String> details = new LinkedList<String>(){
            {
                add("1");
                add("Striker");
                add("Macabi Tel Aviv");
            }
        };
        assertTrue(sasApp.editUserDetails("BenCohen0", details, "COACH"));
        sasApp.deleteUser("BenCohen0");
    }

    @Test
    public void insertFullTeamOwnerSuccess(){
        sasApp.createUser("BenCohen12456", "Ben2468","Ben Cohen", UserType.TEAM_OWNER, true, null );
        List<String> details = new LinkedList<String>(){
            {
                add("Macabi Tel Aviv");
            }
        };
        assertTrue(sasApp.editUserDetails("BenCohen12456", details, "TEAM_OWNER"));
        sasApp.deleteUser("BenCohen12456");
    }

    @Test
    public void insertFullTeamManagerSuccess(){
        sasApp.createUser("AviL1", "Avi2468","Avi Co", UserType.TEAM_OWNER, true, null );
        sasApp.createUser("RamCo3456", "Ram2468","Ram Cohen", UserType.TEAM_MANAGER, true, null );
        List<String> details = new LinkedList<String>(){
            {
                add("Macabi Tel Aviv");
                add("AviL1");
            }
        };
        assertTrue(sasApp.editUserDetails("RamCo3456", details, "TEAM_MANAGER"));
        sasApp.deleteUser("AviL1");
        sasApp.deleteUser("RamCo3456");
    }


    @Test
    public void restoreComplexUserSuccess() {
        sasApp.createUser("AviL12", "Avi2468", "Avi Co", UserType.PLAYER, true, null);
        List<String> details = new LinkedList<String>() {
            {
                add("1992-12-20");
                add("Striker");
                add("Macabi");
            }
        };
        sasApp.editUserDetails("AviL12", details, "PLAYER");
        User user = UsersCRUD.restoreRoleForUser(UsersCRUD.getUserIdByUserName("AviL12"));
    }
}