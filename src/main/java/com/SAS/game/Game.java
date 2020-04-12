package com.SAS.game;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private Season season;
    private League league;
    private LocalDate date;
    private Team host;
    private Team guest;
    private int hostScore;
    private int guestScore;
    private Stadium stadium;
    private GameEventslogger events;
    private GameReport gameReport;
    private List<Referee> referees;

    /**
     * Empty constructor
     */
    public Game() {
        referees = new LinkedList<Referee>();
    }

    public Game(Season season, League league, LocalDate date, Team host, Team guest, int hostScore, int guestScore, Stadium stadium, GameEventslogger events, GameReport gameReport, List<Referee> referees) {
        this.season = season;
        this.league = league;
        this.date = date;
        this.host = host;
        this.guest = guest;
        this.hostScore = hostScore;
        this.guestScore = guestScore;
        this.stadium = stadium;
        this.events = events;
        this.gameReport = gameReport;
        this.referees = referees;
    }

    /**
     * The function returns the winning team name. else, return draw.
     *
     * @return
     */
    public String getWinningTeam() {
        if (hostScore > guestScore) {
            return hostScore.name;
        }

        if (hostScore < guestScore) {
            return guest.name;
        }

        return "draw";
    }

    /**
     * The function starts the match
     */
    public void startGame() {
        System.out.println("Game Started");
    }

    //TODO: implement endGame method
    public void endGame() {
    }

    //TODO: implement editReport
    public void editReport(GameEvent toChange, GameEvent newEvent) {
    }

    //TODO: complete the method after merge with game event logger
    public boolean addGameEvent() {
        return false;
    }

    /**
     * The function returns the current season
     *
     * @return
     */
    public Season getSeason() {
        return season;
    }

    /**
     * The function sets the season
     *
     * @param season
     */
    public void setSeason(Season season) {
        this.season = season;
    }

    /**
     * The function returns the date
     *
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * The function sets the date
     *
     * @return
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * The function returns the host team of the game
     *
     * @return
     */
    public Team getHost() {
        return host;
    }

    /**
     * The function sets the host team of the game
     *
     * @return
     */
    public void setHost(Team host) {
        this.host = host;
    }

    /**
     * The function returns the guest team of the game
     *
     * @return
     */
    public Team getGuest() {
        return guest;
    }

    /**
     * The function returns the guest team of the game
     *
     * @return
     */
    public void setGuest(Team guest) {
        this.guest = guest;
    }

    /**
     * The function return the score of host team
     *
     * @return
     */
    public int getHostScore() {
        return hostScore;
    }

    /**
     * The function adds 1 goal to host team
     *
     * @return
     */
    public void addGoalToHost() {
        this.hostScore++;
    }

    /**
     * The function return the score of guest team
     *
     * @return
     */
    public int getGuestScore() {
        return guestScore;
    }

    /**
     * The function adds 1 goal to guest team
     *
     * @return
     */
    public void addGoalToGuest() {
        this.guestScore++;
    }

    /**
     * The function return the stadium the game take place in
     *
     * @return
     */
    public Stadium getStadium() {
        return stadium;
    }

    /**
     * The function sets the stadium the game take place in
     *
     * @return
     */
    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    /**
     * The functions returns the game events
     *
     * @return
     */
    public GameEventslogger getEvents() {
        return events;
    }

    public void setEvents(GameEventslogger events) {
        this.events = events;
    }

    /**
     * The function return the game report
     *
     * @return
     */
    public GameReport getGameReport() {
        return gameReport;
    }

    /**
     * The function sets the game report
     *
     * @return
     */
    public void setGameReport(GameReport gameReport) {
        this.gameReport = gameReport;
    }

    /**
     * The function return the game referees
     *
     * @return
     */
    public List<Referee> getReferees() {
        return referees;
    }

    /**
     * The function sets the game referees
     *
     * @return
     */
    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }
}


