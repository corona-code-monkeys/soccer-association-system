package com.SAS.League;

import java.util.ArrayList;

/**
 * class league that create a league according to the features in the black class diagram
 */
public class League {
    private String name;
    ArrayList<Season> seasonList;
    private ArrayList <GamesList> gamesList;

    /**
     * replace default constructor
     */
    public League() {
        String name = "default";
        seasonList = new ArrayList<Season>();
        gamesList = new ArrayList<GamesList>();
    }

    /**
     * league constructor with attributes
     *
     * @param name       - the name of the league
     * @param seasonList - the season list of the league
     */
    public League(String name, ArrayList seasonList) {
        this.name = name;
        this.seasonList = seasonList;
        this.gamesList = new ArrayList<>();
    }

    /**
     * @return the name of the league
     */
    public String getName() {
        return name;
    }

    /**
     * @param name: the name of the league
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the games list for this league in specific season
     */
    public ArrayList<GamesList> getGamesList() {
        return gamesList;
    }

    /**
     * function that adds a games list for a specific season to this league
     * @param season: in what year the season started
     * @param games: the list of games in this league for this specific season
     */
    public void addGamesList(int season, ArrayList<Game> games){
        gamesList.add(new GamesList(this.name, season, games));
        for(int i=0; i<seasonList.size();i++){
            if(seasonList.get(i).getYear()==season){
                seasonList.get(i).addGamesList(this.name,games);
            }
        }
    }

}
