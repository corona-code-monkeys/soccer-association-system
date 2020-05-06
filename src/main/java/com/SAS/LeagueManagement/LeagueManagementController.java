package com.SAS.LeagueManagement;

import com.SAS.League.*;
import com.SAS.User.*;
import com.SAS.crudoperations.CRUD;
import com.SAS.User.User;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LeagueManagementController {

    private LinkedList<LeagueRankPolicy> rankPolicies;
    private LinkedList<PointsPolicy> pointsPolicies;
    private LinkedList<GamesPolicy> gamesPolicies;
    private UserController userController;
    private List<Referee> referees;
    private List<League> leagues;

    /**
     * Constructor
     */
    public LeagueManagementController() {
        this.rankPolicies = new LinkedList<>();
        this.pointsPolicies = new LinkedList<>();
        this.gamesPolicies = new LinkedList<>();
        this.userController = new UserController();
        referees = new LinkedList<>();
        leagues = new LinkedList<>();
        initPolicies();
    }

    /**
     * The functions initialize the available policies in our system
     */
    private void initPolicies() {
        //add rank policies
        rankPolicies.add(new NumberOfWins());
        rankPolicies.add(new GoalDifference());

        //add points policies
        pointsPolicies.add(new OnePointForWinAndNoneForDraw());
        pointsPolicies.add(new TwoForWinOneForDraw());
        pointsPolicies.add(new ThreeForWinOneForDrawPolicy());

        //add games policies
        gamesPolicies.add(new OneRoundLeague());
        gamesPolicies.add(new TwoRoundsLeague());
        gamesPolicies.add(new ThreeRoundsLeague());
    }

    /**
     * The function returns all the available rank policies in our system
     *
     * @return
     */
    public String showRankPolicies() {
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (LeagueRankPolicy policy : rankPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
            counter++;
        }
        return policies.toString();
    }

    public boolean initLeague(String name) {
        League league= new League(name);
        if (!CRUD.isLeagueExist(league)) {
            if (CRUD.addLeague(league)) {
                return true;
            }
            return false;
        }
        return false;
    }


    public boolean addSeasonToALeague(Season season, League league) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            CRUD.addLeagueToSeason(season, league, null, null, null);
            CRUD.addSeasonToLeague(league, season, null, null, null);
        }
        return false;
    }

    public boolean assignAndRemoveRefereesFromLeague(League league, HashSet<Referee> referees) {
        if (CRUD.isLeagueExist(league)) {
            CRUD.addAndRemoveRefereesFromLeague(league, referees);
        }
        return false;
    }

    public boolean assignRefereesToLeagueInSpecificSeason(League league, Season season, HashSet<Referee> referees) {
        if (CRUD.isLeagueExist(league) && CRUD.isSeasonExist(season)) {
            if (CRUD.addRefereesToLeagueInSeason(league, season, referees)) {
                boolean isNew = true;
                //add referees
                for (Referee referee : referees) {
                    if (!league.isRefereeInSeason(season, referee)) {
                        league.addReferee(season, referee);
                    }

                    else {
                        isNew = false;
                    }
                }

                return isNew;
            }
        }
        return false;
    }

    /**
     * The function returns all the available points policies in our system
     *
     * @return
     */
    public String showPointsPolicies() {
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (PointsPolicy policy : pointsPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
            counter++;
        }

        policies.setLength(policies.length() - 1);
        return policies.toString();
    }

    /**
     * The function returns all the available game policies in our system
     *
     * @return
     */
    public String showGamePolicies() {
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (GamesPolicy policy : gamesPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
            counter++;
        }

        policies.setLength(policies.length() - 1);
        return policies.toString();
    }

    /**
     * The function receives league, season and rank policy id and set the rank policy to the league according to the season
     *
     * @param league
     * @param season
     * @param rankPolicyId
     * @return
     */
    public boolean addRankPolicy(League league, Season season, String rankPolicyId, User user) {
        if (! canSetPolicy(user)){
            return false;
        }

        if (league == null || season == null) {
            return false;
        }

        if (rankPolicyId.length() == 0) {
            return false;
        }

        LeagueRankPolicy rankPolicy;

        switch (rankPolicyId) {
            case "1":
                rankPolicy = new NumberOfWins(league, season);
                league.addRankPolicy(season, rankPolicy);
                season.addRankPolicy(league, rankPolicy);
                break;

            case "2":
                rankPolicy = new GoalDifference(league, season);
                league.addRankPolicy(season, rankPolicy);
                season.addRankPolicy(league, rankPolicy);
                break;

            default:
                return false;
        }

        return true;
    }

    /**
     * The function receives league, season and points policy id and set the points policy to the league according to the season
     *
     * @param league
     * @param season
     * @param pointsPolicyId
     * @return
     */
    public boolean addPointsPolicy(League league, Season season, String pointsPolicyId, User user) {
        if (! canSetPolicy(user)){
            return false;
        }

        if (league == null || season == null) {
            return false;
        }

        if (pointsPolicyId.length() == 0) {
            return false;
        }

        PointsPolicy pointsPolicy;

        switch (pointsPolicyId) {
            case "1":
                pointsPolicy = new OnePointForWinAndNoneForDraw(league, season);
                league.addPointsPolicy(season, pointsPolicy);
                season.addPointsPolicy(league, pointsPolicy);
                break;

            case "2":
                pointsPolicy = new TwoForWinOneForDraw(league, season);
                league.addPointsPolicy(season, pointsPolicy);
                season.addPointsPolicy(league, pointsPolicy);
                break;

            case "3":
                pointsPolicy = new ThreeForWinOneForDrawPolicy(league, season);
                league.addPointsPolicy(season, pointsPolicy);
                season.addPointsPolicy(league, pointsPolicy);
                break;

            default:
                return false;
        }

        return true;
    }

    /**
     * The function receives league, season and game policy id and set the game policy to the league according to the season
     *
     * @param league
     * @param season
     * @param gamePolicyId
     * @return
     */
    public boolean addGamePolicy(League league, Season season, String gamePolicyId, User user) {
        if (! canSetPolicy(user)){
            return false;
        }

        if (league == null || season == null) {
            return false;
        }

        if (gamePolicyId.length() == 0) {
            return false;
        }

        GamesPolicy gamePolicy;

        switch (gamePolicyId) {
            case "1":
                gamePolicy = new OneRoundLeague(league, season);
                league.addGamePolicy(season, gamePolicy);
                season.addGamePolicy(league, gamePolicy);
                break;

            case "2":
                gamePolicy = new TwoRoundsLeague(league, season);
                league.addGamePolicy(season, gamePolicy);
                season.addGamePolicy(league, gamePolicy);
                break;

            case "3":
                gamePolicy = new ThreeRoundsLeague(league, season);
                league.addGamePolicy(season, gamePolicy);
                season.addGamePolicy(league, gamePolicy);
                break;

            default:
                return false;
        }

        return true;
    }

    /**
     * This function checks if the received user is authorized to set league policies
     * @param user
     * @return true or false
     */
    public boolean canSetPolicy(User user){
        return user.getMyPrivileges().contains("define/changePolicy");
    }

    /**
     * The fenuction returns true if the user can access the association settings page
     * @param user
     * @return
     */
    public boolean canAccessSettingsPage(User user) {
        return user instanceof AssociationRepresentative;
    }

    /**
     * The function receives parameters and returns the referee if the user created, otherwise returns null
     * @param details
     * @return user
     */
    public User addNewReferee(List<String> details) {
        if (details == null || details.size() != 4) {
            return null;
        }

        //first userName, second password, third fullName and last level
        try {
            String userName = details.get(0);
            String pass = details.get(1);
            String fullName = details.get(2);
            int level = Integer.parseInt(details.get(3));

            User referee = userController.createUser(userName, pass, fullName, UserType.REFEREE, true, null);
            ((Referee)referee).setLevel(level);
            referees.add((Referee) referee);
            return referee;

        }
        catch (Exception e) {

        }

        return null;
    }

    /**
     * The function sends an invitation to the referee
     */
    public String sendInvitationToReferee(User referee) {
        return ((Referee)referee).sendInvitation();
    }

    /**
     * The function returns all the referees in the system
     * @return
     */
    public String showAllReferees() {
        StringBuilder builder = new StringBuilder();
        if (referees.size() > 0) {
            builder.append("Referees in the association system:\n");
            for (Referee referee : referees) {
                builder.append(referee.getFullName());
            }
        }

        return  builder.toString();
    }

    /**
     * The function receives a name and return the referee if exists, otherwise returns null
     * @return user
     */
    public User getRefereeByName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return null;
        }

        for (Referee referee: referees) {
            if (referee.getFullName().equals(fullName)) {
                return referee;
            }
        }

        return null;
    }

    /**
     * The function returns true if the referee removed, otherwise returns false
     * @param referee
     * @return
     */
    public boolean removeReferee(User referee) {
        if (referee != null) {
            referees.remove((Referee) referee);
            //remove from DB
            return true;
        }

        return false;
    }

    /**
     * The function receives a name and creates new league if it's valid and returns true,
     * otherwise returns false
     * @param name
     * @return true or false
     */
    public boolean addNewLeague(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        leagues.add(new League(name));
        return true;
    }

    /**
     * The function returns the leagues
     * @return
     */
    public String showLeagues() {
        StringBuilder builder = new StringBuilder();
        if (leagues.size() > 0) {
            builder.append("The leagues in the association system:\n");
            for (League league : leagues) {
                builder.append(league.getName() + "\n");
            }
        }

        return builder.toString();
    }

    /**
     * The function returns the leauge by name if exists, otherwise returns null
     * @param name
     * @return league
     */
    public League getLeagueByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        for (League league: leagues) {
            if (league.getName().equals(name)) {
                return league;
            }
        }

        return null;
    }

    /**
     * The function receives the league and the name of the season and adds it and returns true if added,
     * otherwise returns false
     * @param yearStr
     * @param league
     * @return
     */
    public boolean addNewSeasonToLeague(String yearStr, League league) {
        if (league == null || yearStr == null && yearStr.trim().isEmpty()) {
            return false;
        }
        try {
            int year = Integer.parseInt(yearStr);
            league.addSeason(new Season(year, new HashSet<>(), new HashSet<>()));
            return true;
        }

        catch (Exception e) {

        }

        return false;
    }

    /**
     * The function returns the season of the league according the year
     * @param league
     * @return season
     */
    public Season getSeasonByYearInLeague(League league, String yearStr) {
        if (league == null || yearStr == null || yearStr.trim().isEmpty()) {
            return null;
        }
        Set<Season> seasons = league.getSeasonList();
        try {
            int year = Integer.parseInt(yearStr);
            for (Season season: seasons) {
                if (season.getYear() == year) {
                    return season;
                }
            }
        }
        catch (Exception e) {

        }

        return null;

    }

    /**
     * The function returns the referee by the user name
     * @param userName
     * @return
     */
    public User getRefereeByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return null;
        }

        for (Referee referee: referees) {
            if (referee.getUserName().equals(userName)) {
                return referee;
            }
        }

        return null;
    }
}