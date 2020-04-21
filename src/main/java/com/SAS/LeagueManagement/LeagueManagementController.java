package com.SAS.LeagueManagement;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
import com.SAS.crudoperations.LeagueManagementCRUD;
import com.SAS.team.Team;

import java.util.HashSet;

public class LeagueManagementController {

    private LeagueManagementCRUD crud = new LeagueManagementCRUD();

    public LeagueManagementController() {

    }

    public League initLeague(String name) {
        League league = null;
        if (crud.isLeagueExist(name) == false) {
             league = new League(name);
            crud.addLeague(league);
        }
        return league;
    }

    public void addSeasonToALeague(Season season, League league) {
        season.addLeague(league);
        league.addSeason(season);
    }

    public void assignAndRemoveRefereesFromLeague(League league, Referee ref) {
        if (crud.isLeagueExist(league.getName())) {
            if (crud.isRefExist(ref.getUser().getUserID()) == false) {
                crud.removeRefFromLeague(ref);
            }
            else{
                crud.addRefToLeague(ref);
            }
        }
    }
    public boolean assignRefereesToLeagueInSpecificSeason(int league,int season, Referee ref) {
        if(crud.isRefExistInLeague(ref.getUserID())){
            if(crud.addRefereeToLeagueInSeason(league, season, ref.getUserID(),ref.getLevel())) {
                return true;
            }
        }
        return false;
    }
}
