package com.SAS.crudoperations;

import com.SAS.League.*;
import com.SAS.User.Referee;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;

public class LeagueCRUD {

    private static JdbcTemplate jdbcTemplate;


    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public static boolean isLeagueExist(String name) {
        try {
            String query = String.format("SELECT league_name FROM league WHERE league_name=\"%s\"", name);
            jdbcTemplate.queryForObject(query, String.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addLeague(String name) {
        if (!isLeagueExist(name)) {
            try {
                String query = String.format("insert into league values (\"%s\")", name);
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addSeason(int year) {
        if (!isSeasonExist(year)) {
            try {
                String query = String.format("insert into season (season_year) values (\"%d\")", year);
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addLeagueToSeason(String name, int  year) {
        if (LeagueCRUD.isLeagueExist(name) && LeagueCRUD.isSeasonExist(year)) {
            try {
                String query = String.format("UPDATE season SET league_name =(\"%s\") WHERE season_year = \"%d\";", name, year);
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean removeLeague(String name) {
        try {
            String query = String.format("DELETE FROM league WHERE league_name=\"%s\"", name);
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean removeSeason(int year) {
        try {
            String query = String.format("DELETE FROM season WHERE season_year=\"%d\"", year);
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean isRefExist(String username) {
        try {
            String query = String.format("SELECT user_id FROM referee WHERE user_id=\"%d\"", UsersCRUD.getUserIdByUserName(username));
            if (UsersCRUD.getUserIdByUserName(username) == -1) {
                return false;
            }
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean removeRefFromLeague(String username, String name) {
        try {
            String query = String.format("DELETE FROM referees_in_duty WHERE user_id=%d AND league_name=\"%s\"", username, name);
            if(jdbcTemplate.update(query)==0||UsersCRUD.getUserIdByUserName(username)==-1){
                return false;
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addRefToLeagueInSeason(String name, int year, HashSet<Referee> referees) {
        for (Referee ref : referees) {
            if (!isRefExistInLeagueInSeason(ref.getUserName())) {
                String query = String.format("insert into referees_in_duty values (\"%s\",%d, %d, %d);", name, year, ref.getUserID(), ref.getLevel());
                jdbcTemplate.update(query);
            } else {
                removeRefFromLeague(ref.getUserName(), name);
            }
        }
        return true;
    }

    public static boolean isRefExistInLeagueInSeason(String username) {
        if (isRefExist(username)) {
            try {
                String query = String.format("SELECT * FROM referees_in_duty WHERE user_id=\"%d\"", username);
                jdbcTemplate.queryForObject(query, int.class);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }


    public static boolean isSeasonExist(int year) {
        try {
            String query = String.format("SELECT season_year FROM season WHERE season_year=\"%d\"", year);
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addPoliciesToLeagueInSeason(String name, int year, String rankPolicy, String pointsPolicy, String gamePolicy) {
        try {
            String query = String.format("insert into policies values (%d,\"%s\", \"%s\", \"%s\",\"%s\");", year,name, rankPolicy, pointsPolicy, gamePolicy);
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addReferee(Referee referee) {
        try {
            String query = String.format("insert into referee (user_id,level) values (\"%d\",\"%d\")", UsersCRUD.getUserIdByUserName(referee.getUserName()), referee.getLevel());
            if (UsersCRUD.getUserIdByUserName(referee.getUserName()) == -1) {
                return false;
            }
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean removeReferee(int  userid) {
        try {
            String query = String.format("DELETE FROM referee WHERE user_id=%d", userid);
            if (userid == -1) {
                return false;
            }
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
