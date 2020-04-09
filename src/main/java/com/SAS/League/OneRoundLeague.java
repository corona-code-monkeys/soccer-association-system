package com.SAS.League;

/**
 * class for one round league
 */
public class OneRoundLeague extends GamesPolicy {
    /**
     * constructor that init the object and gives him name
     */
    public OneRoundLeague() {
        this.name="One round league";
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int howManyRounds() {
        return 1;
    }
}
