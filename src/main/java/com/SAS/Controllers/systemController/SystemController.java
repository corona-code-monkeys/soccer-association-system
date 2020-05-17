package com.SAS.Controllers.systemController;

import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.systemLoggers.LoggerFactory;

import java.util.LinkedList;


public class SystemController {

    private LoggerFactory logger;
    private LinkedList<String> externalSystemsAvailable;
    private LinkedList<ExternalSystem> connectedExternalSystems;
    private UserController userController;
    private User admin;



    /**
     * Constructor
     */
    public SystemController() {
        this.connectedExternalSystems = new LinkedList<>();
        this.externalSystemsAvailable = new LinkedList<>();
        this.userController = new UserController();
        initExternalSystemsAvailable();
        this.logger = LoggerFactory.getInstance();
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
     * The function starts the first setup of the system
     */
    public String openAssociationSystem() {
        String welcome = "Welcome to the setup of the soccer association system";
        return welcome;
    }

    /**
     * The function receives an external systems name and try to connect to this system
     *
     * @param name
     */
    public boolean addExternalSystem(String name) {
        if (name == null) {
            logger.logError("Fault: a null external system tried to be added");
            return false;
        }

        if (name.length() == 0) {
            logger.logError("Fault: an external system with no name tried to be added");
            return false;
        }

        switch (name) {
            case "Accounting":
                if (!searchSystem(name)) {
                    AccountingSystem accountingSystem = new AccountingSystem();
                    accountingSystem.connectSystem();
                    connectedExternalSystems.add(accountingSystem);
                    logger.logEvent("User: System. The system connected to the accounting system");
                    return true;
                }

            case "Tax":
                if (!searchSystem(name)) {
                    TaxSystem taxSystem = new TaxSystem();
                    taxSystem.connectSystem();
                    connectedExternalSystems.add(taxSystem);
                    logger.logEvent("User: System. The system connected to the tax system");
                    return true;
                }

            default:
                logger.logError("Fault: a none Tax or Accounting external system tried to be added");
                return false;
        }
    }

    /**
     * The function receives system name and check if our system already connect to it
     * @param name
     * @return
     */
    private boolean searchSystem(String name) {
        for (ExternalSystem system : connectedExternalSystems) {
            if (system.getSystemName().equals(name)){
                return true;
            }
        }
        logger.logError("Fault: system search failed");
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
     * @param fullName
     * @return
     */
    public boolean createSystemAdmin(String userName, String password, String fullName) {
        if (!validateParams(userName, password, fullName)) {
            logger.logError("Fault: unable to create admin user");
            return false;
        }

        logger.logEvent("User: System. Admin user created.");
        admin = userController.createUser(userName, password, fullName, UserType.SYSTEM_ADMIN, true, null);
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
            logger.logError("Fault: wrong parameters values");
            return false;
        }

        if (userName.length() == 0 || password.length() == 0 || fullName.length() == 0) {
            logger.logError("Fault: wrong parameters values");
            return false;
        }

        return true;
    }

}