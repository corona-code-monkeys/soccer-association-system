package com.SAS.team;

import com.SAS.League.Budget;
import com.SAS.League.Season;
import com.SAS.User.Player;
import com.SAS.User.TeamManager;
import com.SAS.User.TeamOwner;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.transaction.Transaction;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import static com.SAS.transaction.transactionType.INCOME;

/**
 * The class represent a team in the football association system
 */
public class Team {

    private String name;
    private List<Facility> teamFacilities;
    private List<Player> players;
    private List<TeamOwner> owners;
    private TeamManager manager;
    private HashMap<Season, Budget> budgets;
    private HashMap<Integer, Double> quartersBalance;
    private List<Transaction> transactionList;
    private boolean active;

    /**
     * Empty constructor
     */
    public Team() {
        players = new LinkedList<Player>();
        transactionList = new LinkedList<Transaction>();
        budgets = new HashMap<Season, Budget>();
        teamFacilities = new LinkedList<Facility>();
        owners = new LinkedList<>();
        active = true;
        initializeFinanceYear();
    }

    /**
     * Params constructor
     * @param name
     * @param players
     * @param owner
     * @param manager
     */
    public Team(String name, List<Player> players, TeamOwner owner, TeamManager manager, List<Facility> teamFacilities) {
        this.name = name;
        this.players = players;
        owners = new LinkedList<>();
        this.owners.add(owner);
        this.manager = manager;
        this.transactionList = new LinkedList<>();
        budgets = new HashMap<>();
        this.teamFacilities = teamFacilities;
        active = true;
        initializeFinanceYear();

    }

    //TODO: set the starting budget for the season as sum of income in Q1

    /**
     * The function initialize the quarters map with the keys and zero balance
     */
    private void initializeFinanceYear() {
        this.quartersBalance  = new HashMap<Integer, Double>() {{
            put(1, 0.0);
            put(2, 0.0);
            put(3, 0.0);
            put(4, 0.0);
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


    /**
     * The function add a transaction that made by the team owner and updates the team budget according to a new transaction
     * @param newTransaction
     * @return true if the income is bigger then expense in the quarter, else return false
     */
    public boolean addTransactionToTeam(Transaction newTransaction) {
        if (newTransaction == null) {
            return false;
        }

        double transactionAmount = 0.0, quarterBalance = 0.0;
        int quarter;
        LocalDate transactionDate = newTransaction.getDate();
        //get the year quarter: 1/2/3/4
        quarter = transactionDate.get(IsoFields.QUARTER_OF_YEAR);
        //check if transaction amount is positive or negative
        transactionAmount = (newTransaction.getType() == INCOME) ? newTransaction.getAmount(): -newTransaction.getAmount();
        quarterBalance = quartersBalance.get(quarter);

        if (quarterBalance + transactionAmount >= 0){
            transactionList.add(newTransaction);
            quartersBalance.put(quarter, quarterBalance + transactionAmount);
            return true;
        }

        //the quarter balance is less then zero so we cant approve the transaction
        return false;

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
    public Facility getHomeStadium() {
        for(Facility facility: teamFacilities){
            if (facility.getFacilityType() == facilityType.STADIUM){
                return facility;
            }
        }

        return null;
    }

    /**
     * The function adds new facility to team facilities list
     * @param newFacility
     */
    public void addFacility(Facility newFacility) {
        if (newFacility != null) {
            teamFacilities.add(newFacility);
        }
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

    /**
     * The function removes a team owner from the owners of the team
     * @param teamOwner
     */
    public void removeTeamOwner(TeamOwner teamOwner) {
        this.owners.remove(teamOwner);
    }

    public void addBudget(Season season, Budget budget) {
    }

    public void removeTeamManager(TeamManager teamManager) {
        this.manager = null;

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
