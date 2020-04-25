package com.SAS.LeagueManagement;

import com.SAS.League.*;
import com.SAS.User.Referee;

import com.SAS.crudoperations.CRUD;

import com.SAS.User.User;

import com.SAS.crudoperations.LeagueManagementCRUD;
import com.SAS.team.Team;

import java.util.LinkedList;
import java.util.List;

public class LeagueManagementController {

    private LinkedList<LeagueRankPolicy> rankPolicies;
    private LinkedList<PointsPolicy> pointsPolicies;
    private LinkedList<GamesPolicy> gamesPolicies;
    private CRUD crud = new CRUD();

    /**
     * Constructor
     */
    public LeagueManagementController() {
        this.rankPolicies = new LinkedList<>();
        this.pointsPolicies = new LinkedList<>();
        this.gamesPolicies = new LinkedList<>();
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

    public League initLeague(String name) {
        League league= new League(name);
        if (!crud.isLeagueExist(league)) {
            if (crud.addLeague(league)) {
                return league;
            }
        }
        return null;
    }

    public void addSeasonToALeague(Season season, League league) {
        if (crud.isLeagueExist(league) && crud.isSeasonExist(season)) {
            crud.addLeagueToSeason(season, league, null, null, null);
            crud.addSeasonToLeague(league, season, null, null, null);
        }
    }

    public void assignAndRemoveRefereesFromLeague(League league, List<Referee> referees) {
        if (crud.isLeagueExist(league)) {
            crud.addAndRemoveRefereesFromLeague(league, referees);
        }
    }

    public boolean assignRefereesToLeagueInSpecificSeason(League league, Season season, List<Referee> referees) {
        if (crud.isLeagueExist(league) && crud.isSeasonExist(season)) {
            if (crud.addRefereesToLeagueInSeason(league, season, referees)) {
                return true;
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

}