package com.SAS.soccer_association_system.transaction;

import java.time.LocalDate;

/**
 * The class represent a transaction that can be made by a team owner or manger
 */
public class Transaction {

    private float amount;
    private String type;
    private LocalDate date;
    private Team team;

    public Transaction() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
