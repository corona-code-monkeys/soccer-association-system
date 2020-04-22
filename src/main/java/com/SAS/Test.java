package com.SAS;

import com.SAS.team.Team;
import com.SAS.transaction.Transaction;
import com.SAS.crudoperations.TransactionCRUD;
import com.SAS.transaction.TransactionType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        TransactionCRUD tadao = (TransactionCRUD)ctx.getBean("tadao");
        LocalDate date = LocalDate.now();
        Team team = new Team();
        int status = tadao.saveTransaction(new Transaction(1000, TransactionType.INCOME, date, team, "bought arnon",null));
        System.out.println(status);

    /*int status=dao.updateEmployee(new Employee(102,"Sonoo",15000));
    System.out.println(status);
    */

    /*Employee e=new Employee();
    e.setId(102);
    int status=dao.deleteEmployee(e);
    System.out.println(status);*/

    }
}
