package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Referee;
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
        String query = String.format("insert into league values (\"%s\");", league.getName());
        return jdbcTemplate.update(query);
    }

    public void addLeagueToSeason(League league, Season season) {
        String query = String.format("insert into league values (\"%s\",%d);", league.getName(), season.getYear());//not sure if correct
        jdbcTemplate.update(query);
    }

    public boolean isRefExist(Referee ref) {
        try {
            String query = String.format("SELECT * FROM referee WHERE referee_id=" + ref.getUserID());
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void removeRefFromLeague(Referee ref) {
        if(isRefExist(ref)){
            String query = String.format("DELETE FROM referees_in_duty WHERE referee_id=" + ref.getUser().getUserID());
            jdbcTemplate.update(query, int.class);
        }
    }

    public void addRefToLeagueInSeason(League league, Season season, Referee ref) {
        if(!isRefExistInLeagueInSeason(ref)){
            String query = String.format("insert into referees_in_duty values (\"%s\",%d, %d, %d);",league.getName(),season.getYear(), ref.getUser().getUserID(),ref.getLevel() );
            jdbcTemplate.update(query, int.class);
        }

    }

    public boolean isRefExistInLeagueInSeason(Referee ref) {
        if(isRefExist(ref)){
            try {
                String query = String.format("SELECT * FROM referees_in_duty WHERE referee_id=" + ref.getUserID());
                jdbcTemplate.queryForObject(query, int.class);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
          return false;
    }


}
