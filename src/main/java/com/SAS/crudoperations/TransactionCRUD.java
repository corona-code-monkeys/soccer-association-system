package com.SAS.crudoperations;

import com.SAS.domain.transaction.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionCRUD {
    private JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveTransaction(Transaction newTransaction){

        //TEST
        String team_name = "macabi";
        String type = "INCOME";
        String date = "2020-03-30";
        String description = "bought arnon";
        int teamOwnerId = 5;
        double amount = 1000.0;
        int id = 9;

        String query = String.format("insert into transactions (team_name, amount, type, team_owner_reported_id, date, description) values ( \"%s\", \"%f\", \"%s\", \"%d\", \"%s\", \"%s\");", team_name, amount, type, teamOwnerId , date, description);
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
