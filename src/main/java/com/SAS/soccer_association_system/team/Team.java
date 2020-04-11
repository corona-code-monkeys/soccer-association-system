package com.SAS.soccer_association_system.team;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The class represent a team in the football association system
 */
public class Team {

    private String name;
    private List<Stadium> teamFacilities;
    private List<Player> players;
    private TeamOwner owner;
    private List<TeamManager> managers;
    private HashMap<Season, Budget> budgets;
    private HashMap<String, Double> quartersBalance;
    private List<Transaction> transactionList;

    /**
     * Empty constructor
     */
    public Team() {
        players = new LinkedList<Player>();
        managers = new LinkedList<TeamManager>();
        transactionList = new LinkedList<Transaction>();
        budgets = new HashMap<Season, Budget>();
        teamFacilities = new LinkedList<Stadium>();
        initializeFinanceYear();
    }

    /**
     * Params constructor
     * @param name
     * @param homeStadium
     * @param players
     * @param owner
     * @param manager
     * @param transactionList
     * @param budgets
     */
    public Team(String name, Stadium homeStadium, List<Player> players, List<TeamManager> managers, TeamOwner owner, TeamManager manager, List<Transaction> transactionList, HashMap<Season, Budget> budgets, List<Stadium> teamFacilities, HashMap<String, Double> quartersBalance) {
        this.name = name;
        this.homeStadium = homeStadium;
        this.players = players;
        this.owner = owner;
        this.managers = managers;
        this.transactionList = transactionList;
        this.budgets = budgets;
        this.teamFacilities = teamFacilities;
        initializeFinanceYear();

    }

    //TODO: set the starting budget for the season as sum of income in Q1

    /**
     * The function initialize the quarters map with the keys and zero balance
     */
    private void initializeFinanceYear() {
        this.quartersBalance  = new HashMap<String, Double>() {{
            put("1", 0.0);
            put("2", 0.0);
            put("3", 0.0);
            put("4", 0.0);
        }};
    }

    /**
     * The function adds a new player to the team
     * @param newPlayer
     * @return
     */
    public boolean addPlayerToTeam(Player newPlayer) {
        if (newPlayer == null) {
            return false;
        }

        players.add(newPlayer);
        return true;
    }

  //TODO: remove player from team

    /**
     * The function add a transaction that made by the team
     * @param newTransaction
     * @return
     */
    public boolean addTransactionToTeam(Transaction newTransaction) {
        if (newTransaction == null) {
            return false;
        }

        transactionList.add(newTransaction);
        updateBudget(newTransaction);
        return true;
    }

    //TODO: to be implement later
    private void updateBudget(Transaction newTransaction) {

    }

    //TODO: remove transaction from team

    /**
     * The function returns the name of the team
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * The function sets the name of the team
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The function return the home stadium of the team
     * @return
     */
    public Stadium getHomeStadium() {
        return homeStadium;
    }

    /**
     * The function sets the home stadium of the team
     * @param homeStadium
     */
    public void setHomeStadium(Stadium homeStadium) {
        this.homeStadium = homeStadium;
    }

    /**
     * The function returns a list of players of the team
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * The function sets a list of players of the team
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * The function return the managers of the team
     * @return
     */
    public List<TeamManager> getManagers() {
        return managers;
    }

    /**
     * The function adds a new manager to the team
     * @param newManager
     * @return
     */
    public boolean addManagerToTeam(TeamManager newManager) {
        if (newManager == null) {
            return false;
        }

        managers.add(newManager);
        return true;
    }

    /**
     * The function return the team owner
     * @return
     */
    public TeamOwner getOwner() {
        return owner;
    }

    /**
     * The function sets the team owner
     * @param owner
     */
    public void setOwner(TeamOwner owner) {
        this.owner = owner;
    }

    /**
     * The function return a list of transactions made by the team
     * @return
     */
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    /**
     * The function sets the transaction list
     * @param transactionList
     */
    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
