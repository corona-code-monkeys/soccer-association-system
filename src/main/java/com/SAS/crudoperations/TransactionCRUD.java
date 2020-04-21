package com.SAS.crudoperations;

import com.SAS.transaction.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionCRUD {
    private JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveTransaction(Transaction newTransaction){

        //TEST
        int transaction_id = 6;
        int team_id = 5;
        String type = "INCOME";
        String date = "2020-03-30";
        String description = "bought arnon";
        double amount = 1000.0;

        String query = String.format("insert into transactions values (%d, %d, %f, \"%s\", \"%s\", \"%s\");",transaction_id,team_id,amount,type,date,description);
        return jdbcTemplate.update(query);
    }
    /*
    public int updateEmployee(Employee e){
        String query="update employee set
        name='"+e.getName()+"',salary='"+e.getSalary()+"' where id='"+e.getId()+"' ";
        return jdbcTemplate.update(query);
    }
    public int deleteEmployee(Employee e){
        String query="delete from employee where id='"+e.getId()+"' ";
        return jdbcTemplate.update(query);
    }

     */
}
