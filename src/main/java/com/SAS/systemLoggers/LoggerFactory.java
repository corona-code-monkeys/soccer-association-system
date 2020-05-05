package com.SAS.systemLoggers;

/**
 * The class represent the logger factory that contain two seperate loggers - events and errors.
 */

import org.apache.log4j.Logger;

public class LoggerFactory {

    private Logger eventsLog;
    private Logger errorsLog;
    private static LoggerFactory loggerInstance = null;

    private LoggerFactory() {
        eventsLog = Logger.getLogger("eventsLogger");
        errorsLog = Logger.getLogger("errorsLogger");
    }


    public static LoggerFactory getInstance() {
        if (loggerInstance == null) {
            synchronized (LoggerFactory.class) {
                if (loggerInstance == null) {
                    loggerInstance = new LoggerFactory();
                }
            }
        }
        return loggerInstance;
    }

    /**
     * The function used to log events
     * @param msg
     */
    public void logEvent(String msg) {
        eventsLog.info(msg);

    }

    /**
     * The function used to log errors
     * @param msg
     */
    public void logError(String msg) {
        errorsLog.error(msg);
    }
}
