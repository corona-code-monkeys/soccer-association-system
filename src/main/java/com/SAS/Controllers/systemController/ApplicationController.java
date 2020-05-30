package com.SAS.Controllers.systemController;

import com.SAS.User.UserController;
import com.SAS.crudoperations.SystemCRUD;
import com.SAS.externalSystems.AccountSystem;
import com.SAS.externalSystems.IAccountSystem;
import com.SAS.externalSystems.ITaxSystem;
import com.SAS.externalSystems.TaxSystem;
import com.SAS.systemLoggers.LoggerFactory;
import java.util.List;


public class ApplicationController {

    private LoggerFactory logger;
    private UserController userController;
    private IAccountSystem accountSystem;
    private ITaxSystem taxSystem;



    /**
     * Constructor
     */
    public ApplicationController() {
        this.userController = new UserController();
        this.logger = LoggerFactory.getInstance();
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
                    accountSystem = new AccountSystem();
                    SystemCRUD.connectToSystem("Accounting");
                    logger.logEvent("User: System. The system connected to the accounting system");
                    return true;
                }

            case "Tax":
                if (!searchSystem(name)) {
                    taxSystem = new TaxSystem();
                    SystemCRUD.connectToSystem("Tax");
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
        boolean res = SystemCRUD.isSystemConnected(name);
        if(res){
            return true;
        }

        logger.logError("Fault: system already connected");
        return false;
    }

    /**
     * The function present the external systems available to connect
     *
     * @return
     */
    public List<String> showAvailableExternalSystem() {
        return SystemCRUD.getAllExternalSystems();
    }

    /**
     * The function receives userName and password from the user and creates admin user for the system
     *
     * @param userName
     * @param password
     * @param fullName
     * @return
     */
    public boolean createSystemAdmin(String userName, String password, String fullName, String email) {
        if (!validateParams(userName, password, fullName, email)) {
            logger.logError("Fault: unable to create admin user");
            return false;
        }

        logger.logEvent("User: System. Admin user created.");
        userController.createUser(userName, password, fullName, email, "SYSTEM_ADMIN", true);
        return true;
    }

    /**
     * The method receives userName, password and full name as string and checks that they are valid
     *  @param userName
     * @param password
     * @param fullName
     * @param email
     */
    private boolean validateParams(String userName, String password, String fullName, String email) {
        if (userName == null || password == null || fullName == null || email==null) {
            logger.logError("Fault: wrong parameters values");
            return false;
        }

        if (userName.length() == 0 || password.length() == 0 || fullName.length() == 0 || email.length()==0) {
            logger.logError("Fault: wrong parameters values");
            return false;
        }

        return true;
    }

    public boolean activateSystem() {
        return SystemCRUD.activateSystem();
    }
}