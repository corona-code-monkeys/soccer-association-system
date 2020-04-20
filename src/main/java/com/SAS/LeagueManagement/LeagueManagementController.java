package com.SAS.LeagueManagement;

import com.SAS.League.*;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class LeagueManagementController {

    private LinkedList<LeagueRankPolicy> rankPolicies;
    private LinkedList<PointsPolicy> pointsPolicies;
    private LinkedList<GamesPolicy> gamesPolicies;

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
     * @return
     */
    public String showRankPolicies(){
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (LeagueRankPolicy policy : rankPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
        }

        policies.setLength(policies.length() - 1);
        return policies.toString();
    }

    /**
     * The function returns all the available points policies in our system
     * @return
     */
    public String showPointsPolicies(){
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (PointsPolicy policy : pointsPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
        }

        policies.setLength(policies.length() - 1);
        return policies.toString();
    }

    /**
     * The function returns all the available game policies in our system
     * @return
     */
    public String showGamePolicies(){
        int counter = 1;
        StringBuilder policies = new StringBuilder();

        for (GamesPolicy policy : gamesPolicies) {
            policies.append(counter + ". " + policy.getName() + "\n");
        }

        policies.setLength(policies.length() - 1);
        return policies.toString();
    }

    /**
     * The function receives league, season and rank policy id and set the rank policy to the league according to the season
     * @param league
     * @param season
     * @param rankPolicyId
     * @return
     */
    public boolean addRankPolicy(League league, Season season, String rankPolicyId){
        if (league == null || season == null) {
            return false;
        }

        if (rankPolicyId.length() == 0) {
            return false;
        }

        LeagueRankPolicy rankPolicy;

        //TODO: check permissions
        switch (rankPolicyId) {
            case "1":
                rankPolicy = new NumberOfWins(league,season);
                league.addRankPolicy(season,rankPolicy);
                season.addRankPolicy(league,rankPolicy);
                break;

            case "2":
                rankPolicy = new GoalDifference(league,season);
                league.addRankPolicy(season,rankPolicy);
                season.addRankPolicy(league,rankPolicy);
                break;

            default:
                System.out.println("No policy was found with this id");
                return false;
        }

        System.out.println("The policy was added successfully");
        return true;
    }

    /**
     * The function receives league, season and points policy id and set the points policy to the league according to the season
     * @param league
     * @param season
     * @param pointsPolicyId
     * @return
     */
    public boolean addPointsPolicy(League league, Season season, String pointsPolicyId){
        if (league == null || season == null) {
            return false;
        }

        if (pointsPolicyId.length() == 0) {
            return false;
        }

        PointsPolicy pointsPolicy;

        //TODO: check permissions
        switch (pointsPolicyId) {
            case "1":
                pointsPolicy = new OnePointForWinAndNoneForDraw(league,season);
                league.addPointsPolicy(season,pointsPolicy);
                season.addPointsPolicy(league,pointsPolicy);
                break;

            case "2":
                pointsPolicy = new TwoForWinOneForDraw(league,season);
                league.addPointsPolicy(season,pointsPolicy);
                season.addPointsPolicy(league,pointsPolicy);
                break;

            case "3":
                pointsPolicy = new ThreeForWinOneForDrawPolicy(league,season);
                league.addPointsPolicy(season,pointsPolicy);
                season.addPointsPolicy(league,pointsPolicy);
                break;

            default:
                System.out.println("No policy was found with this id");
                return false;
        }

        System.out.println("The policy was added successfully");
        return true;
    }

    /**
     * The function receives league, season and game policy id and set the game policy to the league according to the season
     * @param league
     * @param season
     * @param gamePolicyId
     * @return
     */
    public boolean addGamePolicy(League league, Season season, String gamePolicyId){
        if (league == null || season == null) {
            return false;
        }

        if (gamePolicyId.length() == 0) {
            return false;
        }

        GamesPolicy gamePolicy;

        //TODO: check permissions
        switch (gamePolicyId) {
            case "1":
                gamePolicy = new OneRoundLeague(league,season);
                league.addGamePolicy(season,gamePolicy);
                season.addGamePolicy(league,gamePolicy);
                break;

            case "2":
                gamePolicy = new TwoRoundsLeague(league,season);
                league.addGamePolicy(season,gamePolicy);
                season.addGamePolicy(league,gamePolicy);
                break;

            case "3":
                gamePolicy = new ThreeRoundsLeague(league,season);
                league.addGamePolicy(season,gamePolicy);
                season.addGamePolicy(league,gamePolicy);
                break;

            default:
                System.out.println("No policy was found with this id");
                return false;
        }

        System.out.println("The policy was added successfully");
        return true;
    }

}