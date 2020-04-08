package com.SAS.League;

import java.util.ArrayList;

/**
 * class league that create a league according to the features in the black class diagram
 */
public class League {
    String name;
    ArrayList<Season> seasonList;
    ArrayList<Game> gamesList;

    /**
     * replace default constructor
     */
    public League() {
        String name="default";
        seasonList= new ArrayList<Season>();
        gamesList= new ArrayList<Game>();
    }

    /**
     * league constructor with attributes
     * @param name - the name of the league
     * @param seasonList - the season list of the league
     * @param gamesList - the game list of the league
     */
    public League (String name, ArrayList seasonList, ArrayList gamesList){
        this.name=name;
        this.seasonList=seasonList;
        this.gamesList=gamesList;
    }

}
