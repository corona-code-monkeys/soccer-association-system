package com.SAS.domain.systemController;

/**
 * The class represents an abstraction of external system connection interface
 */
public abstract class ExternalSystem {

    /**
     * The function connects the accounting system
     */
    public abstract void connectSystem();

    /**
     * The function returns the system name
     * @return
     */
    public abstract String getSystemName();

}
