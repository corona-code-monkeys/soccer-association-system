package com.SAS.crudoperations;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.User.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersCRUDTest {

    private SystemController sys;

    @BeforeEach
    void setUp() {
        sys = new SystemController();
        sys.initializeDB();
    }

    @Test
    void postUser() {
        assertTrue(UsersCRUD.postUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER.toString()));
    }

    @Test
    void getUserIdByNameFail() {
        assertEquals(-1,UsersCRUD.getUserIdByUserName("vladi"));
    }

    /*
    @Test
    void isUserValidSuccess(){
        assertTrue(UsersCRUD.isUserValid("VladimirI", "Vladi123"));
    }
     */

    @Test
    void isUserValidFail(){
        assertFalse(UsersCRUD.isUserValid("VladimirI", "Vladi12"));
    }

    @Test
    void deleteUserSuccess(){
        assertTrue(UsersCRUD.deleteUser("VladimirI"));
    }

}