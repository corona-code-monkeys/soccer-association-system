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
import org.junit.jupiter.api.AfterEach;
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
    private Transaction t;
    LocalDate date;


    @BeforeEach
    public void setUp() {
        sys = new SystemController();
        sys.initializeDB();
        date = LocalDate.now();
        t = new Transaction(1000, TransactionType.INCOME, date , team, "bought arnon", (TeamOwner) teamOwner);
        userController = new UserController();
        TeamCRUD.postTeam("hap");
        teamOwner = userController.createUser("VladimirI", "Vladi123", "Vladimir Ivich", UserType.TEAM_OWNER, true, null);
        TeamCRUD.addOwnerToTeam("VladimirI", "hap");
        team = new Team("hap", (TeamOwner) teamOwner);
        ((TeamOwner) teamOwner).setTeam(team);
    }

    @AfterEach
    public void tearDown(){
        TransactionCRUD.deleteTransaction("VladimirI");
        UsersCRUD.deleteUser("VladimirI");
    }


    @Test
    public void addTransactoin() {
        assertTrue(TransactionCRUD.addTransactionToDB("hap", 1000.0, t.getType().toString() , ((TeamOwner) teamOwner).getUserName(), date.toString(), t.getDescription()));
    }

    @Test
    public void getAllTransactions(){
        TransactionCRUD.addTransactionToDB("hap", 1000.0, t.getType().toString() , ((TeamOwner) teamOwner).getUserName(), date.toString(), t.getDescription());
        TransactionCRUD.addTransactionToDB("hap", 10000.0, t.getType().toString() , ((TeamOwner) teamOwner).getUserName(), date.toString(), t.getDescription());
        assertEquals(2, TransactionCRUD.getAllTransaction("hap").size());
    }
}