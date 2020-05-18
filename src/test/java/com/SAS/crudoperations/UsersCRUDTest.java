package com.SAS.crudoperations;

import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersCRUDTest {

    private dbStub db;

    @BeforeEach
    void setUp() {
        db = new dbStub();
        db.initializeDB();
    }

    @Test
    void postUser() {
        assertTrue(UsersCRUD.postUser("VladimirI", "Vladi123", "Vladimir Ivich", "vlad@gmail.com","TEAM_OWNER"));
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