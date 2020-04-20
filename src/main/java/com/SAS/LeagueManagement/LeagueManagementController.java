package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.crudoperations.LeagueManagementCRUD;
import com.SAS.team.Team;

import java.util.HashSet;

public class LeagueManagementController {

    private LeagueManagementController leagueManagement;
    private LeagueManagementCRUD crud= new LeagueManagementCRUD();

    public LeagueManagementController() {
        this.leagueManagement = new LeagueManagementController();
    }

    public void initLeague(String name) {
        if (crud.getLeague(name)!=-1) {
            League league = new League(name);
            crud.setLeague(league);
        }
    }

    public void addSeasonToALeague(String name, int year) {
        //check on DB if league exist controller.leagueExist
        int league = crud.getLeague(name);
        HashSet<Team> teams = new HashSet<>();
        HashSet<League> leagues = new HashSet<>();
        leagues.add(league);
        Season season = new Season(year, teams, leagues);
        crud.addSeasonToLeague(league,season);
    }
}
