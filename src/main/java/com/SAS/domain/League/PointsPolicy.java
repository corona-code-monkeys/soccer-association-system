package com.SAS.domain.League;

/**
 * abstract class who which the points policies extends
 */
public abstract class PointsPolicy {
    protected String name;
    protected League league;
    protected Season season;

    /**
     *
     * @return how many point you will get per win in the specific policy
     */
    public abstract int howManyPointsForWin();

    /**
     *
     * @how many point you will get per draw in the specific policy
     */
    public abstract int howManyPointsForDraw();

    /**
     *
      * @return how many point you will get per lose in the specific policy (always 0)
     */
    public int howManyPointsForLose(){return 0;}

    public abstract String getName();

    public abstract League getLeague();

    public abstract Season getSeason();
}

