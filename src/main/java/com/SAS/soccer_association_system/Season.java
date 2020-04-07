package com.SAS.soccer_association_system;

import java.util.ArrayList;

public class Season {
    int year;
    ArrayList<Game> gamesList;
    ArrayList<Team> teamsList;
    ArrayList<League> leaguesList;

    public Season() {
        this.gamesList=new ArrayList<>();
        this.leaguesList=new ArrayList<>();
        this.teamsList=new ArrayList<Team>();
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
