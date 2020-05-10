package com.SAS.crudoperations;

import com.SAS.domain.User.TeamOwner;
import com.SAS.domain.User.User;
import com.SAS.domain.User.UserController;
import com.SAS.domain.User.UserType;
import com.SAS.domain.team.Team;
import com.SAS.domain.teamManagenemt.TeamManagement;
import com.SAS.domain.transaction.Transaction;
import com.SAS.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

class TransactionCRUDTest {
/*
    private TeamManagement teamManagement;
    private UserController userController;
    private Team team;
    private User teamOwner;

    @BeforeEach
    public void setUp()  {
        userController = new UserController();
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        team = new Team("Maccabi Tel Aviv", (TeamOwner)teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
    }


    @Test
    public void testDB() {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            TransactionCRUD tadao = (TransactionCRUD) ctx.getBean("tadao");
            LocalDate date = LocalDate.now();
            team = new Team();
            teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
            int status = tadao.saveTransaction(new Transaction(1000, TransactionType.INCOME, date, team, "bought arnon", (TeamOwner) teamOwner));
            System.out.println(status);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
*/
}