package com.SAS.domain.facility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FacilityUT {

    private Facility facility;

    @BeforeEach
    void setUp() {
        facility = new Facility();
    }

    @Test
    void setValidNameTest() {
        boolean res = facility.setName("Camp Nou");
        assertTrue(res);
    }

    @Test
    void setNullNameTest() {
        String name = null;
        boolean res = facility.setName(name);
        assertFalse(res);
    }

    @Test
    void setZeroLengthNameTest() {
        String name = "";
        boolean res = facility.setName(name);
        assertFalse(res);
    }

    @Test
    void setValidLocationTest() {
        boolean res = facility.setLocation("Barcelona");
        assertTrue(res);
    }

    @Test
    void setNullLocationTest() {
        String location = null;
        boolean res = facility.setLocation(location);
        assertFalse(res);
    }

    @Test
    void setZeroLengthLocationTest() {
        String location = "";
        boolean res = facility.setLocation(location);
        assertFalse(res);
    }

    @Test
    void editValidDetailsBooleanCheckWithTraining() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.STADIUM);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("Training");

        boolean result = facility.editDetails(details);
        assertTrue(result);
    }

    @Test
    void editValidDetailsBooleanCheckWithStadium() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.TRAINING);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("Stadium");

        boolean result = facility.editDetails(details);
        assertTrue(result);
    }

    @Test
    void editInvalidFacilityTypeDetailsParamsCheck() {
        //set first facility details
        facility.setName("facility");
        facility.setLocation("Beer Sheva");
        facility.setFacilityType(facilityType.TRAINING);

        LinkedList<String> details = new LinkedList<>();
        details.add("newFacility");
        details.add("Tel Aviv");
        details.add("field");

        boolean result = facility.editDetails(details);
        assertFalse(result);
    }
}