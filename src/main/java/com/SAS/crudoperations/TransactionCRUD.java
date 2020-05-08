package com.SAS.crudoperations;

import com.SAS.User.TeamOwner;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.LimitExceededException;
import java.util.LinkedList;
import java.util.List;

/**
 * The class represents all the transactions operations with the db
 */
public class TransactionCRUD {
    private static JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * The function receives transaction and add it to the db
     * @param newTransaction
     * @return
     */
    public static int addTransactionToDB(Transaction newTransaction){
        String team_name = newTransaction.getTeam().getName();
        Double amount = newTransaction.getAmount();
        TransactionType type = newTransaction.getType();
        int teamOwnerId = UsersCRUD.getUserIdByUserName(newTransaction.getReportedBy().getUserName());
        String date = newTransaction.getDate().toString();
        String description = newTransaction.getDescription();

        String query = String.format("insert into transactions (team_name, amount, type, team_owner_reported_id, date, description) values ( \"%s\", \"%f\", \"%s\", \"%d\", \"%s\", \"%s\");", team_name, amount, type, teamOwnerId , date, description);
        return jdbcTemplate.update(query);
    }


    public static LinkedList<Integer> getTeamOwners(String teamName){


    }

    public static LinkedList<Transaction> getAllTransaction(String teamName){
        LinkedList<Transaction> teamTransactions = new LinkedList<>();
        try {
            for (TeamOwner teamOwner : owners) {

                String query = String.format("SELECT * FROM transaction WHERE team_owner_reported_id = \"%d\";", teamOwner.getUserID());

                teamTransactions.addAll(jdbcTemplate.query(
                        query,
                        (rs, rowNum) ->
                                new Transaction(
                                        rs.getDouble("amount"),
                                        rs.getString("type"),
                                        rs.getDate("created_date").toLocalDate(),
                                        rs.getString("team_name"),
                                        rs.getString("description")
                                )
                ));
            }
        }
        catch (Exception e){
        }

        return teamTransactions;
    }
}