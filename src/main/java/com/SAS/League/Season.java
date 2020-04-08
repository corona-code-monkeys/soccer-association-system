package com.SAS.League;

import java.util.ArrayList;

class Team {
}//to be removed

public class Season {
    int year;
    ArrayList<Game> gamesList;
    ArrayList<Team> teamsList;
    ArrayList<League> leaguesList;

    public Season() {
        this.gamesList = new ArrayList<>();
        this.leaguesList = new ArrayList<>();
        this.teamsList = new ArrayList<Team>();
    }

    public Season(int year, ArrayList<Game> gamesList, ArrayList<Team> teamsList, ArrayList<League> leaguesList) {
        this.year = year;
        this.gamesList = gamesList;
        this.teamsList = teamsList;
        this.leaguesList = leaguesList;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
