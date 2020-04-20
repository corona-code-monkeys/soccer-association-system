package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class LeagueManagementCRUD {

    private JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int getLeague(String name) {
        int id;
        try {
            String query = String.format("SELECT * FROM league WHERE league_name='"+name+"'");
            id= jdbcTemplate.queryForObject(query, int.class);
            return id;
        }
        catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int setLeague(League league) {
        int id=123456;
        String query = String.format("insert into league values (%d, \"%s\");",league.getName(),id);//does i need to insert id or it will give it id automatticly
        return jdbcTemplate.update(query);
    }

    public void addSeasonToLeague(int league, Season season) {

    }
}
