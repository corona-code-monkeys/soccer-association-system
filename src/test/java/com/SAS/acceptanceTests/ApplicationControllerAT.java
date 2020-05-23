package com.SAS.acceptanceTests;

import com.SAS.Controllers.systemController.ApplicationController;
import static org.junit.jupiter.api.Assertions.*;

import com.SAS.systemLoggers.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationControllerAT {

    private ApplicationController applicationController;
    private LoggerFactory factory;


    @BeforeEach
    void setUp() {
        applicationController = new ApplicationController();
        factory = LoggerFactory.getInstance();
    }

    /**
     * Test 1.1a
     */
    @Test
    void initializeSystemSuccess() {
        long startTime = System.nanoTime();
        String userChoose;
        String userName;
        String password;
        String fullName;
        String email;
        boolean system1 = false;
        boolean system2 = false;
        boolean createUser = false;

        //OpenAssociationSystem
        System.out.println(applicationController.openAssociationSystem());

        //The user select the external systems he want to connect
        System.out.println("Please select which external systems you want to connect to:");
        System.out.println(applicationController.showAvailableExternalSystem());
        userChoose = "Accounting";
        System.out.println(userChoose);
        if (applicationController.addExternalSystem(userChoose)) {
            system1 = true;
        }
        else{
            System.out.println("No External System with this name was found");
            return;
        }

        assertTrue(system1);

        System.out.println("Please select which external systems you want to connect to:");
        System.out.println(applicationController.showAvailableExternalSystem());
        userChoose = "Tax";
        System.out.println(userChoose);
        if (applicationController.addExternalSystem(userChoose)) {
            system2 = true;
        }
        else {
            System.out.println("No External System with this name was found");
            return;
        }

        assertTrue(system2);

        if (system1 && system2) {
            //Create first admin user
            System.out.println("Please enter the following details to create an admin user");
            System.out.println("User Name:");
            userName = "admin";
            System.out.println(userName);
            System.out.println("Password:");
            password = "admin123";
            System.out.println("********");
            System.out.println("Full Name:");
            fullName = "Admin Admin";
            System.out.println("fullName");
            System.out.println("email: ");
            email = "admin12@gmail.com";
            System.out.println(email);
            createUser = applicationController.createSystemAdmin(userName, password, fullName, email);
            System.out.println("The setup ended successfully");

            assertTrue(system1 && system2 && createUser);
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }

    /**
     * Test 1.1b
     */
    @Test
    void initializeSystemFailure() {
        long startTime = System.nanoTime();
        String userChoose;
        String userName;
        String password;
        String fullName;
        String email;

        boolean system1 = false;
        boolean system2 = false;

        //OpenAssociationSystem
        System.out.println(applicationController.openAssociationSystem());

        //The user select the external systems he want to connect
        System.out.println("Please select which external systems you want to connect to:");
        System.out.println(applicationController.showAvailableExternalSystem());
        userChoose = "Acounting";
        System.out.println(userChoose);
        if (applicationController.addExternalSystem(userChoose)) {
            system1 = true;
        }
        else{
            System.out.println("No External System with this name was found");
            assertFalse(system1);
            return;
        }

        System.out.println("Please select which external systems you want to connect to:");
        System.out.println(applicationController.showAvailableExternalSystem());
        userChoose = "Tax";
        System.out.println(userChoose);
        if (applicationController.addExternalSystem(userChoose)) {
            system2 = true;
        }
        else {
            System.out.println("No External System with this name was found");
            return;
        }

        if (system1 && system2) {
            //Create first admin user
            System.out.println("Please enter the following details to create an admin user");
            System.out.println("User Name:");
            userName = "admin";
            System.out.println(userName);
            System.out.println("Password:");
            password = "admin123";
            System.out.println("********");
            System.out.println("Full Name:");
            fullName = "Admin Admin";
            System.out.println("fullName");
            System.out.println("email: ");
            email = "admin12@gmail.com";
            System.out.println(email);
            applicationController.createSystemAdmin(userName, password, fullName, email);
            System.out.println("The setup ended successfully");
        }
        long endTime = System.nanoTime();
        System.out.println("The time the test took is:" + ((double) (endTime - startTime) / 1000000) + " ms");
    }
}
