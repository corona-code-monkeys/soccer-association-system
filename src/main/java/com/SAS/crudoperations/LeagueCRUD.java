package com.SAS.crudoperations;

import com.SAS.domain.League.League;
import com.SAS.domain.League.Season;
import com.SAS.domain.User.Referee;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeagueCRUD {

    private static JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean isLeagueExist(String name) {
        try {
            String query = String.format("SELECT * FROM league WHERE league_name='" + name + "'");
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public int addLeague(League league) {
        int id = 123456; //how we get id?!
        String query = String.format("insert into league values (%d, \"%s\");", league.getName(), id);//do i need to insert id or it will give it id automatically?
        return jdbcTemplate.update(query);
    }



    public void addLeagueScore(int league, Season season) {

    }

    public int getSeason(int year) {
        try {
            String query = String.format("SELECT season_id FROM season WHERE year=" + year);
            int id = jdbcTemplate.queryForObject(query, int.class);
            return id;
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public boolean isRefExist(int refID) {
        try {
            String query = String.format("SELECT * FROM referee WHERE referee_id=" + refID);
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void removeRefFromLeague(Referee ref) {
        if(isRefExist(ref.getUser().getUserID())){
            String query = String.format("DELETE FROM league_referees WHERE referee_id=" + ref.getUser().getUserID());
            jdbcTemplate.update(query, int.class);
        }
    }

    public void addRefToLeague(Referee ref) {
        if(!isRefExistInLeague(ref.getUser().getUserID())){
            String query = String.format("insert into league_referees values (%d, %d);", ref.getUser().getUserID(),ref.getLevel() );
            jdbcTemplate.update(query, int.class);
        }

    }

    public boolean isRefExistInLeague(int userID) {
        if(isRefExist(userID)){
            try {
                String query = String.format("SELECT * FROM league_referees WHERE referee_id=" + userID);
                jdbcTemplate.queryForObject(query, int.class);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
          return false;
    }


    public boolean addRefereeToLeagueInSeason(int league, int season, int userID, int level) {
        try {
            String query = String.format("insert into season_league_referee values (%d, %d,%d,%d);",league,season, userID, level);
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
