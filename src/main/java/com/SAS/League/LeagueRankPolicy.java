package com.SAS.League;
/**
 * abstract class who which the league rank policies extends
 */
public abstract class LeagueRankPolicy {
    protected String name;

    /**
     *
     * @return which column in table breaks the tie in points
     */
    public abstract String tieBreaker();
}
