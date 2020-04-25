package com.SAS.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalReportUT {

    private PersonalReport personalReportFull;
    private PersonalReport personalReportNotFull;


    @BeforeEach
    void setUp() {
        personalReportFull = new PersonalReport(123, "Avi Levi's Page", 89);
        personalReportNotFull = new PersonalReport();
    }

    @Test
    void getPageIdRealID() {
        int pid= personalReportFull.getPageId();
        assertNotNull(pid);
    }

    @Test
    void getPageIdZero() {
        int pid= personalReportNotFull.getPageId();
        assertEquals(0, pid);
    }

    @Test
    void setPageIdNotZero() {
        int pid = 2;
        personalReportNotFull.setPageId(pid);
        assertEquals(pid, personalReportNotFull.getPageId());
    }

    @Test
    void setPageIdZero() {
        int pid = 0;
        boolean isSet = personalReportNotFull.setPageId(pid);
        assertFalse(isSet);
    }

    @Test
    void getDescrNotNull() {
        String des= personalReportFull.getDescr();
        assertNotNull(des);
    }

    @Test
    void getDescrNull() {
        String des= personalReportNotFull.getDescr();
        assertNull(des);
    }

    @Test
    void setDescrNotNull() {
        String des="Report of a mistake in the team manager's name";
        personalReportNotFull.setDescr(des);
        assertEquals(des, personalReportNotFull.getDescr());
    }

    @Test
    void setDescrtNull() {
        String des=null;
        personalReportFull.setDescr(des);
        assertNotNull(personalReportFull.getDescr());
    }

    @Test
    void getUserIdNotZero() {
        int id = personalReportFull.getUserId();
        int zeroId = 0;
        assertNotEquals(zeroId, id);
    }

    @Test
    void getUserIdZero() {
        int id = personalReportNotFull.getUserId();
        int zeroId = 0;
        assertEquals(zeroId, id);
    }

    @Test
    void setUserIdNotZero() {
        int id = 3;
        boolean isSet = personalReportNotFull.setUserId(id);
        assertEquals(id, personalReportNotFull.getUserId());
    }

    @Test
    void setUserIdZero() {
        int zeroId = 0;
        boolean isSet = personalReportFull.setUserId(zeroId);
        assertNotEquals(zeroId, personalReportFull.getUserId());
    }
}