package com.SAS.Controllers.systemController;

import com.SAS.User.User;
import com.SAS.User.UserController;
import java.util.LinkedList;


public class systemController {

    private LinkedList<String> externalSystemsAvailable;
    private LinkedList<externalSystem> connectedExternalSystems;
    private UserController userController;
    private User admin;

    /**
     * Constructor
     */
    public systemController() {
        this.connectedExternalSystems = new LinkedList<>();
        this.externalSystemsAvailable = new LinkedList<>();
        this.userController = new UserController();
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
    public boolean insertDateToDB() {
        return false;
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
     *
     * @param name
     * @return
     */
    private boolean searchSystem(String name) {
        for (externalSystem system : connectedExternalSystems) {
            if (system.getSystemName().equals(name)) {
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

    //TODO create user

    /**
     * The function receives userName and password from the user and creates admin user for the system
     *
     * @param userName
     * @param password
     * @param fullName
     * @return
     */
    public boolean createSystemAdmin(String userName, String password, String fullName) {
        if (!validateParams(userName, password, fullName)) {
            return false;
        }

        admin = userController.createUser(userName, password, fullName, UserType.SYSTEM_ADMIN, true);
        return true;
    }

    /**
     * The method receives userName, password and full name as string and checks that they are valid
     *
     * @param userName
     * @param password
     * @param fullName
     */
    private boolean validateParams(String userName, String password, String fullName) {
        if (userName == null || password == null || fullName == null) {
            return false;
        }

        if (userName.length() == 0 || password.length() == 0 || fullName.length() == 0) {
            return false;
        }

        return true;
    }
}
