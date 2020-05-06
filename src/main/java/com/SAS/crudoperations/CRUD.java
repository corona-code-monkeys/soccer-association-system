package com.SAS.crudoperations;

import com.SAS.League.*;
import com.SAS.User.*;
import com.SAS.facility.Facility;
import com.SAS.game.Game;
import com.SAS.game_event_logger.GameEvent;
import com.SAS.game_event_logger.GameEventLogger;
import com.SAS.team.Team;
import com.SAS.transaction.Transaction;
import com.SAS.transaction.TransactionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class CRUD {

    //user

    /**
     * will return false for null in any parameter, or username equals to "wrong", will return the user you wanted otherwise
     *
     * @param userName
     * @param password
     * @param fullName
     * @param type
     * @return
     */
    public static User createUser(String userName, String password, String fullName, UserType type) {
        if (userName == null || password == null || fullName == null) {
            return null;
        } else if (userName.equals("wrong")) {
            return null;
        } else {
            UserController c = new UserController();
            return c.createUser(userName, password, fullName, type, true, null);
        }
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param fieldRole
     * @param dateOfBirth
     * @param team
     * @return
     */
    public static boolean setPlayer(int userID, FieldRole fieldRole, LocalDate dateOfBirth, Team team) {
        return userID >= 0;
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param level
     * @param fieldRole
     * @param team
     * @return
     */
    public static boolean setCoach(int userID, int level, FieldRole fieldRole, Team team) {
        return userID >= 0;
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param level
     * @return
     */
    public static boolean setReferee(int userID, int level) {
        return userID >= 0;
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param team
     * @param userIDNominatedBy
     * @return
     */
    public static boolean setTeamOwner(int userID, Team team, int userIDNominatedBy) {
        return userID >= 0;
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param team
     * @return
     */
    public static boolean setTeamManager(int userID, Team team) {
        return userID >= 0;
    }

    /**
     * will return false for null full name or "wrong", will return true in any other case.
     *
     * @param fullName
     * @param type
     * @return
     */
    public static boolean verifyInDB(String fullName, UserType type) {
        if (fullName == null || fullName.equals("wrong")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param type
     * @return
     */
    public static boolean addRole(int userID, UserType type) {
        return userID >= 0;
    }

    /**
     * will return false for userID smaller then 0
     *
     * @param userID
     * @param type
     * @return
     */
    public static boolean removeRole(int userID, UserType type) {
        return userID >= 0;
    }

    /**
     * will return false for null inputs, or inputs with password == wrong, else, will return a Fan named
     *
     * @param userName
     * @param password
     * @return
     */
    public static User getUser(String userName, String password) {
        if (userName == null || password == null) {
            return null;
        } else if (password.equals("wrong")) {
            return null;
        } else {
            UserController controller = new UserController();
            return controller.createUser(userName, password, "matan anavi", UserType.FAN, true, null);
        }
    }


    /**
     * This function return the team from the DB
     * @param teamName
     * @return
     */
    public static Team getTeamByName(String teamName){
        if (teamName == null)
            return null;
        //TODO: return team from DB
        Team team = null;
        return team;
    }

    //league

    /**
     * will return false for null leagues or leagues called "matan"
     *
     * @param name
     * @return
     */
    public static boolean createLeague(String name) {
        if (name == null || name.equals("matan")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * will return true for years that are bigger or grater then zero
     *
     * @param year
     * @return
     */
    public static boolean createSeason(int year) {
        return year >= 0;
    }

    /**
     * to hard to moc, if will be needed, will find a way
     *
     * @param league
     * @param season
     * @param leagueRankPolicy
     * @param pointsPolicy
     * @param gamesPolicy
     * @return
     */
    public static boolean addSeasonToLeague(League league, Season season, LeagueRankPolicy leagueRankPolicy,
                                            PointsPolicy pointsPolicy, GamesPolicy gamesPolicy) {
        return true;
    }

    /**
     * to hard to moc, if will be needed, will find a way
     *
     * @param league
     * @param season
     * @param leagueRankPolicy
     * @param pointsPolicy
     * @param gamesPolicy
     * @return
     */
    public static boolean addLeagueToSeason(Season season, League league, LeagueRankPolicy leagueRankPolicy,
                                            PointsPolicy pointsPolicy, GamesPolicy gamesPolicy) {
        return true;
    }


    /**
     * too hard to moc, if will be needed, will find a way
     *
     * @param league
     * @param season
     * @return
     */
    public static boolean isSeasonPartOfLeague(League league, Season season) {
        return true;
    }

    /**
     * send null for wrong input, not null for real input (will return list of three policies, as mentioned bellow)
     *
     * @param league
     * @param season
     * @return
     */
    public static String[] getPolicies(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            String[] arr = new String[3];
            arr[0] = "The bigger goal difference";
            arr[1] = "Number of wins";
            arr[2] = "Two rounds league";
            return arr;
        }
    }

    /**
     * send null for wrong input, not null for real input (will return NumberOfWins)
     *
     * @param league
     * @param season
     * @return
     */
    public static LeagueRankPolicy getLeagueRankPolicy(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            return new NumberOfWins();
        }
    }

    /**
     * send null for wrong input, not null for real input (will return ThreeForWinOneForDrawPolicy)
     *
     * @param league
     * @param season
     * @return
     */
    public static PointsPolicy getPointsPolicy(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            return new ThreeForWinOneForDrawPolicy();
        }
    }

    /**
     * send null for wrong input, not null for real input (will return oneRoundLeague)
     *
     * @param league
     * @param season
     * @return
     */
    public static GamesPolicy getGamesPolicy(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            return new TwoRoundsLeague();
        }
    }

    /**
     * to hard to moc, if will be needed, will find a way
     *
     * @param league
     * @param season
     * @return
     */
    public static Object getTable(League league, Season season) {
        return null;
    }

    /**
     * send null for wrong input, not null for real input (will send a list of 4 empty games)
     *
     * @param league
     * @param season
     * @return
     */
    public static List<Game> getGamesList(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            List<Game> list = new ArrayList<>();
            list.add(new Game());
            list.add(new Game());
            list.add(new Game());
            list.add(new Game());
            return list;
        }
    }

    /**
     * send null for wrong input, not null for real input
     *
     * @param league
     * @param season
     * @param referees
     * @return
     */
    public static boolean addRefereesToLeagueInSeason(League league, Season season, HashSet<Referee> referees) {
        if (league == null || season == null || referees == null) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * add or remove referees from a league
     *
     * @param league
     * @param referees
     * @return
     */
    public static boolean addAndRemoveRefereesFromLeague(League league, HashSet<Referee> referees) {
        if (league == null || referees == null || referees.size() == 0) {
            return false;
        } else {
            return league.getName().equals("Ligat Ha'al");
        }
    }


    /**
     * send null for wrong input, not null for real input (u'll get a list of three referees)
     *
     * @param league
     * @param season
     * @return
     */
    public static List<Referee> getRefereesList(League league, Season season) {
        if (league == null || season == null) {
            return null;
        } else {
            List<Referee> list = new ArrayList<>();
            UserController c = new UserController();
            list.add(new Referee(c.createUser("name1", "password1", "fullname1", UserType.REFEREE, true, null), "fullname1"));
            list.add(new Referee(c.createUser("name2", "password2", "fullname2", UserType.REFEREE, true, null), "fullname2"));
            list.add(new Referee(c.createUser("name3", "password3", "fullname3", UserType.REFEREE, true, null), "fullname3"));
            return list;
        }
    }

    /**
     * send null for wrong input, not null for real input
     *
     * @param team
     * @param season
     * @param amount
     * @return
     */
    public static boolean addBudget(Team team, Season season, double amount) {
        if (team == null || season == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * send null for wrong input, not null for real input
     *
     * @param team
     * @param season
     * @return
     */
    public static double getBudget(Team team, Season season) {
        if (team == null || season == null) {
            return -1;
        } else {
            return 10000;
        }

    }

    //game

    /**
     * to hard to moc, if will be needed, will find a way
     *
     * @param season
     * @param league
     * @param date
     * @param host
     * @param guest
     * @param stadium
     * @return
     */
    public static boolean createGame(Season season, League league, LocalDate date, Team host, Team guest, Facility stadium) {
        return true;
    }

    /**
     * the actual implementation of this function
     *
     * @param game
     * @param events
     * @return
     */
    public static boolean insertGameEvents(Game game, List<GameEvent> events) {
        if (game == null || events == null) {
            return false;
        } else {
            boolean answer = true;
            for (GameEvent event : events) {
                answer &= addGameEvent(game, event);
            }
            return answer;
        }
    }

    /**
     * on the moc just send null for false and real game for true.
     *
     * @param game
     * @param event
     * @return
     */
    public static boolean addGameEvent(Game game, GameEvent event) {
        if (game == null || event == null) {
            return false;
        } else {
            game.getEvents().addNewEvent(event);
            return true;
        }
    }

    /**
     * the actual implementation of this function
     *
     * @param game
     * @param oldEvent
     * @param newEvent
     * @return
     */
    public static boolean editGameEvent(Game game, GameEvent oldEvent, GameEvent newEvent) {
        if (removeGameEvent(game, oldEvent)) {
            return addGameEvent(game, newEvent);
        } else {
            return false;
        }
    }

    /**
     * on the moc just send null for false and real game for true, if the event not exist in the game event logger will return false as well
     *
     * @param game
     * @param event
     * @return
     */
    public static boolean removeGameEvent(Game game, GameEvent event) {
        if (game == null || event == null) {
            return false;
        } else {
            return game.getEvents().removeEvent(event);
        }
    }

    /**
     * on the moc just send null for false and real game for true
     *
     * @param game
     * @return
     */
    public static List<GameEvent> getGameEvents(Game game) {
        if (game == null) {
            return null;
        } else {
            GameEventLogger logger = game.getEvents();
            return logger.getEventList();
        }
    }

    /**
     * on the moc just send null or matan as the team name to get a false
     *
     * @param name
     * @param players
     * @param owner
     * @param manager
     * @param teamFacilities
     * @return
     */
    public static boolean createTeam(String name, List<Player> players, TeamOwner owner, TeamManager manager, List<Facility> teamFacilities) {
        if (name == null || name.equals("matan")) {
            return false;
        }
        return true;
    }

    public static boolean addTeamOwner(Team team, TeamOwner teamOwner) {
        return true;
    }

    /**
     * @param team        on the mock send null for wrong team, and real team for good team
     * @param teamManager on the mock send null for wrong teamManager, and real teamManager for good teamManager
     * @return true if the insert successes, false otherwise
     */
    public static boolean addTeamManager(Team team, TeamManager teamManager) {
        if (team == null || teamManager == null) {
            return false;
        } else {
            team.setTeamManager(teamManager);
            return true;
        }
    }


    /**
     * @param team        on the mock send null for wrong team, and real team for good team
     * @param transaction on the mock send null for wrong transaction, and real transaction for good transaction
     * @return true if the insert successes, false otherwise
     */
    public static boolean addTransaction(Team team, Transaction transaction) {
        if (team == null || transaction == null) {
            return false;
        } else {
            team.addTransactionToTeam(transaction);
            return true;
        }
    }

    /**
     * @param team     on the mock send null for wrong team, and real team for good team
     * @param facility on the mock send null for wrong facility, and real facility for good team
     * @return true if the insert successes, false otherwise
     */
    public static boolean addFacility(Team team, Facility facility) {
        if (team == null || facility == null) {
            return false;
        } else {
            team.addFacility(facility);
            return true;
        }
    }


    /**
     * @param team on the mock send null for wrong team, and real team for good team
     * @return list of Facilities that this team has
     */
    public static List<Facility> getFacilities(Team team) {
        if (team == null) {
            return null;
        } else {
            List<Facility> list = new ArrayList<>();
            list.add(new Facility());
            list.add(new Facility());
            list.add(new Facility());
            return list;
        }
    }

    /**
     * @param team on the mock send null for wrong team, and real team for good team
     * @return list of transactions that this team has
     */
    public static List<Transaction> getTransactions(Team team) {
        if (team == null) {
            return null;
        } else {
            List<Transaction> list = new ArrayList<>();
            list.add(new Transaction(100, TransactionType.EXPENSE, LocalDate.now(), team, "description1", null));
            list.add(new Transaction(5000, TransactionType.INCOME, LocalDate.now(), team, "description2", null));
            list.add(new Transaction(1400, TransactionType.EXPENSE, LocalDate.now(), team, "description3", null));
            list.add(new Transaction(30, TransactionType.INCOME, LocalDate.now(), team, "description4", null));
            return list;
        }
    }

    /**
     * @param path on the mock should equals "good path"
     * @return if the database connection good or not
     */
    public static boolean init(String path) {
        return path != null && path.equals("good path");
    }

    /**
     * return false if the league is unknown and true if its known
     *
     * @param league
     * @return
     */
    public static boolean isLeagueExist(League league) {
        if (league.getName().equals("Ligat Ha'al")) {
            return true;
        }
        return false;
    }

    /**
     * opposite to isLeagueExist
     *
     * @param league
     * @return
     */
    public static boolean addLeague(League league) {
        return (!isLeagueExist(league));
    }

    /**
     * return false if the league is unknown and true if its known
     *
     * @param season
     * @return
     */
    public static boolean isSeasonExist(Season season) {
        if (season == null) {
            return false;
        }
        return true;
    }

    /**
     * The function return true if there's a this userName exists in the DB, otherwise returns false
     *
     * @param userName
     * @return true or false
     */
    public static boolean isUserNameExist(String userName) {
        return userName.equals("shaharf");
    }

    /**
     * This function retrieves all the AssociationRepresentative from the DB
     * @return
     */
    public static List<AssociationRepresentative> getAssociationRepresentatives(){
        List<AssociationRepresentative> allRepresentatives = new LinkedList<>();
        //ToDO: get all the AssociationRepresentative from DB
        return allRepresentatives;
    }

    /**
     * This function removes a team from the DB
     * @param team
     */
    public static void removeTeam(Team team) {
        //TODO: remove team from DB
    }
  
    /**
     * The function receives a userName and returns the full name of the user
     * @param userName
     * @return
     */
    //TODO: add implementation
    public static String getFullNameByUserName(String userName) {
        return "";
    }
}
