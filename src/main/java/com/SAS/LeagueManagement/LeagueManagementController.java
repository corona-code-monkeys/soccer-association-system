package com.SAS.LeagueManagement;

import com.SAS.League.League;

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
}