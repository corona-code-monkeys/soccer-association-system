package com.SAS.League;

/**
 * class for three rounds league
 */
public class ThreeRoundsLeague extends GamesPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public ThreeRoundsLeague() {
        this.name="Two rounds league";
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 3;
    }
}
