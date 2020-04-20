package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.team.Team;

import java.util.HashSet;

public class LeagueManagementController {

    private LeagueManagementController leagueManagement;

    public LeagueManagementController() {
        this.leagueManagement = new LeagueManagementController();
    }

    public League initLeague(String name) {
        //check on DB if league exist controller.leagueExist
        League league= new League(name);
        return league;
    }

    public League addSeasonToALeague(String name, int year) {
        //check on DB if league exist controller.leagueExist
        League league= crud.getLEague(name);
        HashSet<Team> teams= new HashSet<>();
        HashSet<League> leagues= new HashSet<>();
        leagues.add(league);
        Season season= new Season(year,teams,leagues);
        league.addSeason(season);
        return league;
    }
}