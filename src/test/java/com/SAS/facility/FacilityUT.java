package com.SAS.facility;

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
        String name = "Camp Nou";
        facility.setName("Camp Nou");
        assertEquals(name, facility.getName());
    }

    @Test
    void setNullNameTest() {
        facility.setName("Camp Nou");
        String name = null;
        facility.setName(name);
        assertEquals("Camp Nou", facility.getName());
    }

    @Test
    void setZeroLenthNameTest() {
        facility.setName("Camp Nou");
        String name = "";
        facility.setName(name);
        assertEquals("Camp Nou", facility.getName());
    }

    @Test
    void setValidLocationTest() {
        String location = "Barcelona";
        facility.setLocation("Barcelona");
        assertEquals(location, facility.getLocation());
    }

    @Test
    void setNullLocationTest() {
        facility.setLocation("Barcelona");
        String location = null;
        facility.setLocation(location);
        assertEquals("Barcelona", facility.getLocation());
    }

    @Test
    void setZeroLengthLocationTest() {
        facility.setLocation("Barcelona");
        String location = "";
        facility.setLocation(location);
        assertEquals("Barcelona", facility.getLocation());
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