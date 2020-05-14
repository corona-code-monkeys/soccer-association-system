package com.SAS.transaction;

import com.SAS.User.TeamOwner;
import com.SAS.team.Team;

import java.time.LocalDate;

/**
 * The class represent a transaction that can be made by a team owner or manger
 */
public class Transaction {

    private double amount;
    private TransactionType type;
    private LocalDate date;
    private Team team;
    private String description;
    private TeamOwner reportedBy;

    /**
     * Params constructor
     * @param amount
     * @param type
     * @param date
     * @param team
     */
    public Transaction(double amount, TransactionType type, LocalDate date, Team team, String description, TeamOwner reportedBy) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
        this.team = team;
        this.reportedBy = reportedBy;
    }

    /**
     * constructor from sql
     * @param amount
     * @param type
     * @param date
     * @param teamName
     */
    public Transaction(double amount, String type, LocalDate date, String teamName, String description) {
        this.amount = amount;
        this.description = description;
        this.type = TransactionType.valueOf(type);
        this.date = date;
    }

    /**
     * The function returns the amount of the transactions
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     * The function sets the amount of the transaction
     * @param amount
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * The function returns the type the transaction
     * @return
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * The function returns the description of the transaction
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * The function sets the type of the transaction
     * @param type
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * The function returns the date the transaction has been made
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * The function sets the date the transaction has been made
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * The function returns the team that made the transaction
     * @return
     */
    public Team getTeam() {
        return team;
    }

    /**
     * The function sets the team that made the transaction
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * The function returns the team owner that reported this transaction
     * @return
     */
    public TeamOwner getReportedBy() {
        return reportedBy;
    }
}
