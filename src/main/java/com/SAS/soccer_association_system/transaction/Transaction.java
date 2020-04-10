package com.SAS.soccer_association_system.transaction;

import java.time.LocalDate;

/**
 * The class represent a transaction that can be made by a team owner or manger
 */
public class Transaction {

    private float amount;
    private transactionType type;
    private LocalDate date;
    private Team team;

    /**
     * Empty constructor
     */
    public Transaction() {
    }

    /**
     * Params constructor
     * @param amount
     * @param type
     * @param date
     * @param team
     */
    public Transaction(float amount, transactionType type, LocalDate date, Team team) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.team = team;
    }

    /**
     * The function returns the amount of the transactions
     * @return
     */
    public float getAmount() {
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
    public transactionType getType() {
        return type;
    }

    /**
     * The function sets the type of the transaction
     * @param type
     */
    public void setType(transactionType type) {
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
}
