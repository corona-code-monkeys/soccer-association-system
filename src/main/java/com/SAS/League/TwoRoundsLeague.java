package com.SAS.League;

/**
 * class for two rounds league
 */
public class TwoRoundsLeague extends GamesPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public TwoRoundsLeague() {
        this.name="Two rounds league";
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 2;
    }
}
