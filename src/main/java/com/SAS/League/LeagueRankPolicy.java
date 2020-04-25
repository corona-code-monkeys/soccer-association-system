package com.SAS.League;
/**
 * abstract class who which the league rank policies extends
 */
public abstract class LeagueRankPolicy {
    protected String name;
    protected League league;
    protected Season season;

    /**
     *
     * @return which column in table breaks the tie in points
     */
    public abstract String tieBreaker();

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
