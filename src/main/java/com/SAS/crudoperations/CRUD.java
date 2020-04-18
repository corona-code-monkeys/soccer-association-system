package com.SAS.crudoperations;

import com.SAS.League.*;
import com.SAS.User.*;
import com.SAS.game.Game;
import com.SAS.game_event_logger.GameEvent;
import com.SAS.stadium.Stadium;
import com.SAS.team.Team;
import com.SAS.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

public class CRUD {

    //user
    public int createUser(String userName, String password, String fullName, UserType type) {
        return 0;
    }

    public boolean setPlayer(int userID, FieldRole fieldRole, LocalDate dateOfBirth, Team team) {
        return true;
    }

    public boolean setCoach(int userID, int level, FieldRole fieldRole, Team team) {
        return true;
    }

    public boolean setReferee(int userID, int level) {
        return true;
    }

    public boolean setTeamOwner(int userID, Team team, int userIDNominatedBy) {
        return true;
    }

    public boolean setTeamManager(int userID, Team team) {
        return true;
    }

    public boolean verifyInDB(String fullName, UserType type) {
        return true;
    }

    public boolean addRole(int userID, UserType type) {
        return true;
    }

    public boolean removeRole(int userID, UserType type) {
        return true;
    }

    public int getUserID(String userName, String password) {
        return 0;
    }

    //league

    public boolean createLeague(String name) {
        return true;
    }

    public boolean createSeason(int year) {
        return true;
    }

    public boolean addSSeasonToLeauge(League league, Season season, LeagueRankPolicy leagueRankPolicy,
                                      PointsPolicy pointsPolicy, GamesPolicy gamesPolicy) {
        return true;
    }

    public String[] getPolicies(League league, Season season) {
        return null;
    }

    public LeagueRankPolicy getLeagueRankPolicy(League league, Season season) {
        return null;
    }

    public PointsPolicy getPointsPolicy(League league, Season season) {
        return null;
    }

    public GamesPolicy getGamesPolicy(League league, Season season) {
        return null;
    }

    public Object getTable(League league, Season season) {
        return null;
    }

    public List<Game> getGamesList(League league, Season season) {
        return null;
    }

    public boolean addRefereesToLeague(League league, Season season, List<Referee> referees) {
        return true;
    }

    public List<Referee> getRefereesList(League league, Season season) {
        return null;
    }

    public boolean addBudget(Team team, Season season, double amount) {
        return true;
    }

    public double getBudget(Team team, Season season) {
        return 0;
    }

    //game
    public boolean createGame(Season season, League league, LocalDate date, Team host, Team guest, Stadium stadium) {
        return true;
    }

    public boolean insertGameEvents(Game game, List<GameEvent> events) {
        return true;
    }

    public boolean addGameEvent(Game game, GameEvent event) {
        return true;
    }

    public boolean editGameEvent(Game game, GameEvent oldEvent, GameEvent newEvent) {
        return true;
    }

    public boolean removeGameEvent(Game game, GameEvent event) {
        return true;
    }

    public boolean getGameEvents(Game game) {
        return true;
    }

    public boolean ctreateTeam(String name,List<Player> players, TeamOwner owner, TeamManager manager, List<Stadium> teamFacilities) {
        return true;
    }

    public boolean addTeamOwner(Team team, TeamOwner teamOwner) {
        return true;
    }

    public boolean addTeamManager(Team team, TeamManager teamManager) {
        return true;
    }

    public boolean addTransaction(Team team, Transaction transaction) {
        return true;
    }

    public boolean addFacility(Team team, Stadium facility) {
        return true;
    }

    public Stadium getFacilities(Team team) {
        return null;
    }

    public List<Transaction> getTransactions(Team team) {
        return null;
    }

    public boolean init(String path) {
        return true;
    }
}
