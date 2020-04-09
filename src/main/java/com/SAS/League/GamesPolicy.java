package com.SAS.League;

/**
 * abstract class who which the games policies extends
 */
public abstract class GamesPolicy {
    protected String name;

    /**
     *
     * @return how many games against the other teams each team will play.
     */
    public abstract int howManyRounds();
}
