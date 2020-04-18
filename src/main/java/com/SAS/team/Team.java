package com.SAS.team;

import com.SAS.League.Budget;
import com.SAS.League.Season;
import com.SAS.User.Player;
import com.SAS.User.TeamManager;
import com.SAS.User.TeamOwner;
import com.SAS.stadium.Stadium;
import com.SAS.transaction.Transaction;

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
    private List<TeamOwner> owners;
    private TeamManager manager;
    private HashMap<Season, Budget> budgets;
    private HashMap<String, Double> quartersBalance;
    private List<Transaction> transactionList;
    private Stadium homeStadium;
    private boolean active;

    /**
     * Empty constructor
     */
    public Team() {
        players = new LinkedList<Player>();
        transactionList = new LinkedList<Transaction>();
        budgets = new HashMap<Season, Budget>();
        teamFacilities = new LinkedList<Stadium>();
        owners = new LinkedList<>();
        active = true;
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
    public Team(String name, Stadium homeStadium, List<Player> players, TeamOwner owner, TeamManager manager, List<Transaction> transactionList, HashMap<Season, Budget> budgets, List<Stadium> teamFacilities, HashMap<String, Double> quartersBalance) {
        this.name = name;
        this.homeStadium = homeStadium;
        this.players = players;
        this.owners.add(owner);
        this.manager = manager;
        this.transactionList = transactionList;
        this.budgets = budgets;
        this.teamFacilities = teamFacilities;
        active = true;
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
     * The function return the manager of the team
     * @return
     */
    public TeamManager getManager() {
        return manager;
    }

    /**
     * The function sets the new manager to the team
     * @param newManager
     * @return
     */
    public void setTeamManager(TeamManager newManager) {
        this.manager = newManager;
    }

    /**
     * The function returns the team owners
     * @return
     */
    public List<TeamOwner> getOwners() {
        return this.owners;
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

    /**
     * The function adds another team owner
     * @param teamOwner
     */
    public void addTeamOwner(TeamOwner teamOwner) {
        this.owners.add(teamOwner);
    }

    public void addBudget(Season season, Budget budget) {
    }

    /**
     * The function sets the team to be inactive
     */
    public void inactivateTeam() {
        this.active = false;
    }

    /**
     * The function sets the team to be active
     */
    public void reactivateTeam() {
        this.active = true;
    }

    /**
     * The function returns true if the team is active, otherwise returns false
     * @return treu or false
     */
    public boolean isActive() {
        return active;
    }
}
