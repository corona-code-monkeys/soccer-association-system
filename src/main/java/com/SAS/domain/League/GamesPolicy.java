package com.SAS.domain.League;

/**
 * abstract class who which the games policies extends
 */
public abstract class GamesPolicy {
    protected String name;
    protected Season season;
    protected League league;
    /**
     *
     * @return how many games against the other teams each team will play.
     */
    public abstract int howManyRounds();

    /**
     *
     * @return the name of the policy
     */
    public abstract String getName();

    /**
     *
     * @return the season object
     */
    public abstract Season getSeason();

    /**
     *
     * @return the league object
     */
    public abstract League getLeague();

}
