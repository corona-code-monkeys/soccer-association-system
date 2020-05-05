package com.SAS.team;

import com.SAS.League.Budget;
import com.SAS.League.Season;
import com.SAS.User.*;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.teamManagenemt.TeamAsset;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private Coach coach;
    private boolean active;
    private PersonalPage personalPage;
    private  boolean isRegistered;


    /**
     * Empty constructor
     */
    public Team() {
        this.players = new LinkedList<>();
        this.transactionList = new LinkedList<>();
        this.budgets = new HashMap<>();
        this.teamFacilities = new LinkedList<>();
        this.owners = new LinkedList<>();
        this.active = true;
        initializeFinanceYear();
    }

    /**
     * Params constructor
     *
     * @param name
     * @param owner
     */
    public Team(String name, TeamOwner owner) {

        this.name = name;
        this.players = new LinkedList<>();
        this.owners = new LinkedList<>();
        this.owners.add(owner);
        this.transactionList = new LinkedList<>();
        this.budgets = new HashMap<>();
        this.teamFacilities = new LinkedList<>();
        this.active = true;
        initializeFinanceYear();
        personalPage = new PersonalPage("Team name: " + name + ", Team owner: " + owner.getFullName());
    }

    /**
     * The function initialize the quarters map with the keys and zero balance
     */
    private void initializeFinanceYear() {
        this.quartersBalance = new HashMap<Integer, Double>() {{
            put(1, 0.0);
            put(2, 0.0);
            put(3, 0.0);
            put(4, 0.0);
        }};
    }

    /**
     * The function adds a new player to the team
     *
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
     * The function adds new season to team
     *
     * @param season
     * @return
     */
    public boolean addSeason(Season season) {
        if (!budgets.containsKey(season)) {
            budgets.put(season, new Budget(this, season, 0.0));
            return true;
        }

        return false;
    }


    /**
     * The function add a transaction that made by the team owner and updates the team budget according to a new transaction
     *
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
        transactionAmount = (newTransaction.getType() == TransactionType.INCOME) ? newTransaction.getAmount() : -newTransaction.getAmount();
        quarterBalance = quartersBalance.get(quarter);

        if (quarterBalance + transactionAmount >= 0) {
            transactionList.add(newTransaction);
            quartersBalance.put(quarter, quarterBalance + transactionAmount);
            addTransactionToBudget(transactionDate, transactionAmount);
            return true;
        }

        //the quarter balance is less then zero so we cant approve the transaction
        return false;

    }

    /**
     * The function receives the transaction date and the amount and add it to the season budget
     *
     * @param transactionDate
     * @param transactionAmount
     */
    private void addTransactionToBudget(LocalDate transactionDate, double transactionAmount) {
        int currYear = transactionDate.getYear();
        for (Map.Entry<Season, Budget> pair : budgets.entrySet()) {
            if (pair.getKey().getYear() == currYear) {
                pair.getValue().addToBudget(transactionAmount);
            }
        }
    }

    /**
     * The function returns the name of the team
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * The function sets the name of the team
     *
     * @param name
     */
    public boolean setName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * The function returns a list of all the team facilities
     *
     * @return
     */
    public List<Facility> getTeamFacilities() {
        return teamFacilities;
    }

    /**
     * The function return the home stadium of the team
     *
     * @return
     */
    public Facility getHomeStadium() {

        for (Facility facility : teamFacilities) {
            if (facility.getFacilityType() == facilityType.STADIUM) {
                return facility;
            }
        }
        return null;
    }

    /**
     * The function adds new facility to team facilities list
     *
     * @param newFacility
     */


    public boolean addFacility(Facility newFacility) {
        if (newFacility == null) {
            return false;
        }
        teamFacilities.add(newFacility);
        return true;
    }


    /**
     * The function returns a list of players of the team
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }


    /**
     * The function return the manager of the teaצ
     * @return
     */
    public TeamManager getManager() {
        return manager;
    }


    /**
     * The sets the team manager
     *
     * @param newManager
     * @return
     */
    public boolean setTeamManager(TeamManager newManager) {
        if (newManager == null) {
            return false;
        }
        this.manager = newManager;
        return true;
    }

    /**
     * The function returns the team owners
     *
     * @return
     */
    public List<TeamOwner> getOwners() {
        return this.owners;
    }

    /**
     * The function return a list of transactions made by the team
     *
     * @return
     */
    public List<Transaction> getTransactionList() {
        return transactionList;
    }


    /**
     * The function adds another team owner
     *
     * @param teamOwner
     */
    public boolean addTeamOwner(TeamOwner teamOwner) {
        if (teamOwner == null) {
            return false;
        }
        this.owners.add(teamOwner);
        return true;
    }

    /**
     * The function removes a team owner from the owners of the team
     *
     * @param teamOwner
     */
    public void removeTeamOwner(TeamOwner teamOwner) {
        this.owners.remove(teamOwner);
    }


    /**
     * This function returns the team's coach
     *
     * @return coach
     */
    public Coach getCoach() {
        return coach;
    }

    /**
     * This function sets the team's coach
     *
     * @param coach
     */
    public boolean setCoach(Coach coach) {
        if (coach == null) {
            return false;
        }

        this.coach = coach;
        return true;
    }

    /**
     * This function removes the player from the team players
     *
     * @param player
     */
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    /**
     * This function removes the coach from being the coach of the team
     *
     * @param coach
     */
    public void removeCoach(Coach coach) {
        if (this.coach == coach) {
            this.coach = null;
        }
    }

    /**
     * This function removes the facility from the facilities list
     *
     * @param facility
     */
    public void removeFacility(Facility facility) {
        this.teamFacilities.remove(facility);
    }


    public void removeTeamManager(TeamManager teamManager) {
        this.manager = null;
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
     *
     * @return treu or false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This function sets the personal page of the team.
     * @param personalPage
     * @return true if was set, otherwise false
     */
    public boolean setPersonalPage(PersonalPage personalPage) {
        if (personalPage != null){
            this.personalPage = personalPage;
            return true;
        }
        return false;
    }

    /**
     * This function returns the personal page of the team
     * @return PersonalPage
     */
    public PersonalPage getPersonalPage() {
        return this.personalPage;
    }


    /**
     * This function returns the team facilities
     * @return
     */
    public List<Facility> getFacilities() {
        return this.teamFacilities;
    }

    /**
     * This function returns all the team assets
     * @return List<TeamAsset>
     */
    public List<TeamAsset> getAllAssets(){
        List<TeamAsset> assets = new LinkedList<>();
        assets.addAll(players);
        assets.addAll(teamFacilities);
        if (coach!=null)
            assets.add(coach);
        return assets;
    }

    /**
     * This function returns the asset by type and name
     * @param type
     * @param name
     * @return TeamAsset
     */
    public TeamAsset getAssetByNameAndType(String type, String name) {
        if (type == null || name == null || name.trim().isEmpty())
            return null;
        switch (type) {
            case "Coach":
                if (coach!=null && coach.getFullName().equals(name))
                    return this.coach;
            case "Player":
                for (Player player : this.players) {
                    if (player.getFullName().equals(name))
                        return player;
                }
                return null;
            case "Facility":
                for (Facility facility: this.teamFacilities) {
                    if (facility.getName().equals(name))
                        return facility;
                }
                return null;
            default:
                return null;
        }
    }

     /**
     * The function returns the optional nominees for team owner - coach, team manager and players
     * @return
     */
    public List<User> getOptionalNomineesForTeamOwner() {
        List<User> optionalNominees = new LinkedList<User>();
        if (this.manager != null) {
            optionalNominees.add(getManager());
        }

        if (this.coach != null) {
            optionalNominees.add(getCoach());
        }

        if (this.players.size() > 0) {
            optionalNominees.addAll(getPlayers());
        }

        return optionalNominees;
    }

    /**
     * The function receives a full name of a user and returns the user if it's an optional for team owner nominee,
     * otherwise return null
     * @param fullName
     * @return
     */
    public User getUserForTeamOwner(String fullName) {
        if (this.manager != null && this.manager.getFullName().equals(fullName)) {
            return manager;
        }
        else if (this.coach != null && this.coach.getFullName().equals(fullName)) {
            return coach;
        }
        else {
            for (Player player : this.players) {
                if (player.getFullName().equals(fullName)) {
                    return player;
                }
            }
        }
        return null;
    }

    /**
     * This function returns the TeamOwner by its name
     * @param fullName
     * @return
     */
    public User getTeamOwnerByFullName(String fullName) {
        for (TeamOwner owner : this.owners) {
            if (owner.getFullName().equals(fullName)) {
                return owner;
            }
        }
        return null;
    }

    /**
     * This function returns the optional team managers
     * @return List<User>
     */
    public List<User> getOptionalNomineesForTeamManager() {
        List<User> nominees = new LinkedList<>();
        if (this.players.isEmpty() == false) {
            nominees.addAll(getPlayers());
        }
       if (this.coach != null) {
           nominees.add(getCoach());
        }
        return nominees;
    }


    /**
     * This function return the coach/player whose full name if name
     * @param name
     * @return User
     */
    public User getUserForTeamManager(String name) {
        if (name!=null) {
            //coach
            if (this.coach != null && this.coach.getFullName().equals(name))
                return this.coach;
            //player
            for (Player player : this.players) {
                if (player.getFullName().equals(name))
                    return player;
            }
        }
        return null;
    }
}
