package com.SAS.crudoperations;

import com.SAS.Controllers.systemController.SystemController;
import com.SAS.User.TeamOwner;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import com.SAS.teamManagenemt.TeamManagement;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCRUDTest {

    private UserController userController;
    private Team team;
    private User teamOwner;
    private SystemController sys;


    @BeforeEach
    public void setUp() {
        sys = new SystemController();
        sys.initializeDB();
        userController = new UserController();
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        team = new Team("hap", (TeamOwner) teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
    }


    @Test
    public void addTransactoin() {
        LocalDate date = LocalDate.now();
        Transaction t = new Transaction(1000, TransactionType.INCOME, date, team, "bought arnon", (TeamOwner) teamOwner);
        assertTrue(TransactionCRUD.addTransactionToDB(t));
    }

    @Test
    public void getAllTransactions(){
        assertEquals(2, TransactionCRUD.getAllTransaction("macabi").size());
    }
}