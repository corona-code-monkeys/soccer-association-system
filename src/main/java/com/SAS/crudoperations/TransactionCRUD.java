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
    public static boolean addTransactionToDB(Transaction newTransaction){
        try {
            String team_name = newTransaction.getTeam().getName();
            Double amount = newTransaction.getAmount();
            TransactionType type = newTransaction.getType();
            int teamOwnerId = UsersCRUD.getUserIdByUserName(newTransaction.getReportedBy().getUserName());
            String date = newTransaction.getDate().toString();
            String description = newTransaction.getDescription();

            String query = String.format("insert into transactions (team_name, amount, type, team_owner_reported_id, date, description) values ( \"%s\", \"%f\", \"%s\", \"%d\", \"%s\", \"%s\");", team_name, amount, type, teamOwnerId, date, description);
            jdbcTemplate.update(query);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public static LinkedList<Integer> getTeamOwners(String teamName){
        LinkedList<Integer> teamOwners = new LinkedList<>();

        try{
            String query = String.format("SELECT owner_user_id FROM team_owners WHERE team_name = \"%s\";", teamName);
            teamOwners.addAll(jdbcTemplate.queryForList(query, Integer.class));
        }
        catch (Exception e){

        }
        return teamOwners;
    }

    public static LinkedList<Transaction> getAllTransaction(String teamName){
        LinkedList<Transaction> teamTransactions = new LinkedList<>();
        LinkedList<Integer> teamOwnersId = getTeamOwners(teamName);
        try {
            for (Integer ownerId : teamOwnersId) {

                String query = String.format("SELECT * FROM transactions WHERE team_owner_reported_id = \"%d\";", ownerId);

                teamTransactions.addAll(jdbcTemplate.query(
                        query,
                        (rs, rowNum) ->
                                new Transaction(
                                        rs.getDouble("amount"),
                                        rs.getString("type"),
                                        rs.getDate("date").toLocalDate(),
                                        rs.getString("team_name"),
                                        rs.getString("description")
                                )
                ));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return teamTransactions;
    }
}