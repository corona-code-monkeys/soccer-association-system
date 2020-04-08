package com.SAS.soccer_association_system.team;

import java.util.LinkedList;
import java.util.List;

/**
 * The class represent a team in the football association system
 */
public class Team {

    private String name;
    private Stadium homeStadium;
    private List<Player> players;
    private TeamOwner owner;
    private TeamManager manager;
    private List<Transaction> transactionList;

    public Team() {
        players = new LinkedList<Player>();
        transactionList = new LinkedList<Transaction>();
    }

    public Team(String name, Stadium homeStadium, List<Player> players, TeamOwner owner, TeamManager manager, List<Transaction> transactionList) {
        this.name = name;
        this.homeStadium = homeStadium;
        this.players = players;
        this.owner = owner;
        this.manager = manager;
        this.transactionList = transactionList;
    }

    public boolean addPlayerToTeam(Player newPlayer) {
        if (newPlayer == null) {
            return false;
        }

        players.add(newPlayer);
        return true;
    }

  //TODO: remove player from team

    public boolean addTransactionToTeam(Transaction newTransaction) {
        if (newTransaction == null) {
            return false;
        }

        transactionList.add(newTransaction);
        return true;
    }

    //TODO: remove transaction from team

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stadium getHomeStadium() {
        return homeStadium;
    }

    public void setHomeStadium(Stadium homeStadium) {
        this.homeStadium = homeStadium;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public TeamOwner getOwner() {
        return owner;
    }

    public void setOwner(TeamOwner owner) {
        this.owner = owner;
    }

    public TeamManager getManager() {
        return manager;
    }

    public void setManager(TeamManager manager) {
        this.manager = manager;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
