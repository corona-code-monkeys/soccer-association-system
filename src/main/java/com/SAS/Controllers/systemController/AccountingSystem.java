package com.SAS.Controllers.systemController;

import java.util.concurrent.TimeUnit;

/**
 * The class represents the soccer association accounting system that our system need to connect to
 */
public class AccountingSystem extends ExternalSystem {

    private String systemName;

    /**
     * Constructor
     */
    public AccountingSystem() {
        this.systemName = "Accounting";
    }

    /**
     * The function connects the accounting system
     */
    public void connectSystem() {
        try {
            System.out.println("Trying to connect accounting system...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Establishing connection...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Accounting system is connected...");
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function returns the system name
     *
     * @return
     */
    public String getSystemName() {
        return systemName;
    }
}
