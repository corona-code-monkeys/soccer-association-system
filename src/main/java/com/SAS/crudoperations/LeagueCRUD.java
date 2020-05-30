package com.SAS.crudoperations;

import com.SAS.League.*;
import com.SAS.User.Referee;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static boolean addLeagueToSeason(String name, int year) {
        if (!isPaired(name, year)) {
            try {
                String query = String.format("insert into policies (league_name,season_year) values (\"%s\",\"%d\")", name, year);
                jdbcTemplate.update(query);
                return true;
            } catch (Exception e) {
                throw e;
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

    public static boolean removeRefFromLeague(String username, String leagueName) {
        try {
            String query = String.format("DELETE FROM referees_in_duty WHERE user_id=%d AND league_name=\"%s\"", UsersCRUD.getUserIdByUserName(username), leagueName);
            if (jdbcTemplate.update(query) == 0 || UsersCRUD.getUserIdByUserName(username) == -1) {
                return false;
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addRefToLeagueInSeason(String leagueName, int year, String referee, int level) {
        if (!isRefExistInLeagueInSeason(referee)) {
            String query = String.format("insert into referees_in_duty values (\"%s\",%d, %d, %d);", leagueName, year, UsersCRUD.getUserIdByUserName(referee), level);
            jdbcTemplate.update(query);
        } else {
            removeRefFromLeague(referee, leagueName);
        }
        return true;
    }

    public static boolean isRefExistInLeagueInSeason(String username) {
        if (isRefExist(username)) {
            try {
                String query = String.format("SELECT * FROM referees_in_duty WHERE user_id=\"%d\"", UsersCRUD.getUserIdByUserName(username));
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
        if (isPaired(name, year)) {
            try {
                String query = String.format("UPDATE policies SET league_rank_policy=\"%s\", points_policy=\"%s\" ,games_policy=\"%s\" WHERE season_year=%d AND league_name=\"%s\"", rankPolicy, pointsPolicy, gamePolicy, year, name);
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    private static boolean isPaired(String name, int year) {
        if (LeagueCRUD.isLeagueExist(name) && LeagueCRUD.isSeasonExist(year)) {
            try {
                String query = String.format("SELECT season_year FROM policies WHERE season_year=\"%d\" AND league_name=\"%s\"", year, name);
                jdbcTemplate.queryForObject(query, int.class);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addReferee(int user_id, int level) {
        try {
            String query = String.format("insert into referee (user_id,level) values (\"%s\",\"%d\")", user_id, level);
            if (user_id == -1) {
                return false;
            }
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean removeReferee(int userid) {
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

    public static void removePolicies(String name, int year) {
        try {
            String query = String.format("DELETE FROM policies WHERE season_year=%d AND league_name=\"%s\"", year, name);
            jdbcTemplate.update(query);
        } catch (EmptyResultDataAccessException e) {
        }
    }

    public static List<String> getLeagues() {
        String query = String.format("SELECT league_name FROM league");
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<String> leagues = new LinkedList<>();
        for (Map<String, Object> row : rows) {
            leagues.add((String) row.get("league_name"));
        }
        return leagues;
    }
}
