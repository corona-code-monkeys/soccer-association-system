package com.SAS.soccer_association_system.game;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private Season season;
    private LocalDate date;
    private Team host;
    private Team guest;
    private int hostScore;
    private int guestScore;
    private Stadium stadium;
    private GameEventslogger events;
    private GameReport gameReport;
    private List<Referee> referees;

    public Game() {
        referees = new LinkedList<Referee>();
    }

    /**
     * The function returns the winning team of the match
     * @return
     */
    //TODO: discuss what arg to return
    public Team getWinningTeam();{
        if(hostScore > guestScore){
            return host;
        }

        if(hostScore < guestScore){
            return guest;
        }
        //TODO: what to return if draw
        return null;
    }

    /**
     * The function starts the match
     */
    public void startGame(){
        System.out.println("Game Started");
    }

    //TODO: implement endGame method
    public void endGame(){
    }

    //TODO: complete the method after merge with game event logger
    public boolean addGameEvent(){
        return false;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getHost() {
        return host;
    }

    public void setHost(Team host) {
        this.host = host;
    }

    public Team getGuest() {
        return guest;
    }

    public void setGuest(Team guest) {
        this.guest = guest;
    }

    public int getHostScore() {
        return hostScore;
    }

    public void setHostScore(int hostScore) {
        this.hostScore = hostScore;
    }

    public int getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(int guestScore) {
        this.guestScore = guestScore;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public GameEventslogger getEvents() {
        return events;
    }

    public void setEvents(GameEventslogger events) {
        this.events = events;
    }

    public GameReport getGameReport() {
        return gameReport;
    }

    public void setGameReport(GameReport gameReport) {
        this.gameReport = gameReport;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }
}


