package com.SAS.crudoperations;

import com.SAS.dbstub.dbStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerCRUDTest {

    private dbStub db;

    @BeforeEach
    public void setUp() {
        db = new dbStub();
        db.initializeDB();
    }

    @Test
    void getAllExternalSystems() {
        assertEquals(2, SystemCRUD.getAllExternalSystems().size());
    }

    @Test
    void connectToSystem() {
        assertTrue(SystemCRUD.connectToSystem("Tax"));
        SystemCRUD.removeSystem("Tax");
    }

    @Test
    void isSystemConnected() {
        SystemCRUD.connectToSystem("Tax");
        assertTrue(SystemCRUD.isSystemConnected("Tax"));
        SystemCRUD.removeSystem("Tax");
    }
}