package com.SAS.Controllers.systemController;

import java.util.LinkedList;
import java.util.List;

public class systemController {

    private LinkedList<String> externalSystemsAvailable;
    private LinkedList<externalSystem> connectedExternalSystems;
    private User admin;

    /**
     * Constructor
     */
    public systemController() {
        connectedExternalSystems = new LinkedList<>();
        externalSystemsAvailable = new LinkedList<>();
        initExternalSystemsAvailable();
    }

    /**
     * The function initialize the external systems available to connect
     */
    private void initExternalSystemsAvailable() {
        externalSystemsAvailable.add("Accounting system");
        externalSystemsAvailable.add("Tax system");
    }

    /**
     * The function receives raw data and insert the data to our DB
     */
    //TODO: insert data to DB
    public void insertDateToDB() {
    }

    /**
     * The function initialize all the schemas in the DB
     *
     * @return
     */
    //TODO: initialize DB schemas
    public boolean initializeDB() {
        return false;
    }


    /**
     * The function starts the first setup of the system
     */
    public void openAssociationSystem() {
        System.out.println("Welcome to the setup of the soccer association system");
    }

    /**
     * The function receives an external systems name and try to connect to this system
     *
     * @param name
     */
    public boolean addExternalSystem(String name) {
        if (name == null) {
            return false;
        }

        if (name.length() == 0) {
            return false;
        }

        //TODO: check permissions
        switch (name) {
            case "Accounting":
                if (!searchSystem(name)) {
                    accountingSystem accountingSystem = new accountingSystem();
                    accountingSystem.connectSystem();
                    connectedExternalSystems.add(accountingSystem);
                    return true;
                }

                System.out.println("Accounting system is already connected to the system...");
                return false;

            case "Tax":
                if (!searchSystem(name)) {
                    taxSystem taxSystem = new taxSystem();
                    taxSystem.connectSystem();
                    connectedExternalSystems.add(taxSystem);
                    return true;
                }

                System.out.println("Tax system is already connected to the system...");
                return false;

            default:
                System.out.println("No External System with this name was found");
                return false;
        }
    }

    /**
     * The function receives system name and check if our system already connect to it
     * @param name
     * @return
     */
    private boolean searchSystem(String name) {
        for (externalSystem system : connectedExternalSystems) {
            if (system.getSystemName().equals(name)){
                return true;
            }
        }

        return false;
    }

    /**
     * The function present the external systems available to connect
     *
     * @return
     */
    public String showAvailableExternalSystem() {
        int counter = 1;
        StringBuilder systems = new StringBuilder();

        for (String systemName : externalSystemsAvailable) {
            systems.append(counter + ". " + systemName + "\n");
        }

        systems.setLength(systems.length() - 1);
        return systems.toString();
    }

    /**
     * The function receives userName and password from the user and creates admin user for the system
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean createSystemAdmin(String userName, String password) {
        if (userName == null || password == null) {
            return false;
        }

        if (userName.length() == 0 || password.length() == 0) {
            return false;
        }

        //TODO: create user
        return true;
    }


    public static void main() {

    }


}
