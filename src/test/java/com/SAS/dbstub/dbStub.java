package com.SAS.dbstub;

import com.SAS.crudoperations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dbStub {

    private static ApplicationContext ctx;
    public static TransactionCRUD transDao;
    public static UsersCRUD usersDao;
    public static GameCRUD gameDao;
    public static TeamCRUD teamDao;
    public static LeagueCRUD leagueCRUD;


    /**
     * The function initialize all the schemas in the DB
     *
     * @return
     */
    public static void initializeDB() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        transDao = (TransactionCRUD) ctx.getBean("transDao");
        usersDao = (UsersCRUD) ctx.getBean("usersDao");
        gameDao = (GameCRUD) ctx.getBean("gameDao");
        teamDao = (TeamCRUD) ctx.getBean("teamDao");
        leagueCRUD = (LeagueCRUD) ctx.getBean("leagueDao");
    }
}
