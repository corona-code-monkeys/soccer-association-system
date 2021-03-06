package com.SAS.game;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.game_event_logger.GameEvent;
import com.SAS.game_event_logger.GameEventLogger;
import com.SAS.report.GameReport;
import com.SAS.facility.Facility;
import com.SAS.team.Team;

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
    private Facility stadium;
    private GameEventLogger events;
    private GameReport gameReport;
    private List<Referee> referees;

    /**
     * Empty constructor
     */
    public Game() {
        referees = new LinkedList<Referee>();
    }

    public Game(Season season, League league, LocalDate date, Team host, Team guest, int hostScore, int guestScore, Facility stadium, GameEventLogger events, GameReport gameReport, List<Referee> referees) {
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
            return host.getName();
        }

        if (hostScore < guestScore) {
            return guest.getName();
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
    public boolean setSeason(Season season) {
        if (season == null) {
            return false;
        }

        this.season = season;
        return true;
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
    public boolean setDate(LocalDate date) {
        if (date == null) {
            return false;
        }

        this.date = date;
        return true;
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
    public boolean setHost(Team host) {
        if (host == null) {
            return false;
        }

        this.host = host;
        return true;
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
    public boolean setGuest(Team guest) {
        if (guest == null) {
            return false;
        }

        this.guest = guest;
        return true;
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
    public Facility getStadium() {
        return stadium;
    }

    /**
     * The function sets the stadium the game take place in
     *
     * @return
     */
    public boolean setStadium(Facility stadium) {
        if (stadium == null || stadium.getFacilityType() != facilityType.STADIUM) {
            return false;
        }

        this.stadium = stadium;
        return true;
    }

    /**
     * The functions returns the game events
     *
     * @return
     */
    public GameEventLogger getEvents() {
        return events;
    }

    public boolean setEvents(GameEventLogger events) {
        if (events == null) {
            return false;
        }

        this.events = events;
        return true;
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
    public boolean setGameReport(GameReport gameReport) {
        if (gameReport == null) {
            return false;
        }

        this.gameReport = gameReport;
        return true;
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
    public boolean setReferees(List<Referee> referees) {
        if (referees == null || referees.size() < 2) {
            return false;
        }

        for (Referee referee : referees) {
            if (referee == null) {
                return false;
            }
        }

        this.referees = referees;
        return true;
    }
}


