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

    public static boolean addLeague(League league) {
        if (!isLeagueExist(league.getName())) {
            try {
                String query = String.format("insert into league values (\"%s\")", league.getName());
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addSeason(Season season) {
        if (!isSeasonExist(season.getYear())) {
            try {
                String query = String.format("insert into season (season_year) values (\"%d\")", season.getYear());
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addLeagueToSeason(League league, Season season) {
        if (LeagueCRUD.isLeagueExist(league.getName()) && LeagueCRUD.isSeasonExist(season.getYear())) {
            try {
                String query = String.format("UPDATE season SET league_name =(\"%s\") WHERE season_year = \"%d\";", league.getName(), season.getYear());
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

    public static boolean removeSeason(Season season) {
        try {
            String query = String.format("DELETE FROM season WHERE season_year=\"%d\"", season.getYear());
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean isRefExist(Referee ref) {
        try {
            String query = String.format("SELECT user_id FROM referee WHERE user_id=\"%d\"", UsersCRUD.getUserIdByUserName(ref.getUserName()));
            jdbcTemplate.queryForObject(query, int.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean removeRefFromLeague(Referee ref, League league) {
        if (isRefExist(ref) && isLeagueExist(league.getName())) {
            try {
                String query = String.format("DELETE FROM referees_in_duty WHERE referee_id=\"%d\" AND league_name=\"%s\"", ref.getUser().getUserID(), league.getName());
                jdbcTemplate.update(query, int.class);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean addRefToLeagueInSeason(League league, Season season, HashSet<Referee> referees) {
        for (Referee ref : referees) {
            if (!isRefExistInLeagueInSeason(ref)) {
                String query = String.format("insert into referees_in_duty values (\"%s\",%d, %d, %d);", league.getName(), season.getYear(), ref.getUser().getUserID(), ref.getLevel());
                jdbcTemplate.update(query);
            } else {
                removeRefFromLeague(ref, league);
            }
        }
        return true;
    }

    public static boolean isRefExistInLeagueInSeason(Referee ref) {
        if (isRefExist(ref)) {
            try {
                String query = String.format("SELECT * FROM referees_in_duty WHERE referee_id=\"%d\"", ref.getUserID());
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

    public static boolean addPoliciesToLeagueInSeason(League league, Season season, LeagueRankPolicy rankPolicy, PointsPolicy pointsPolicy, GamesPolicy gamePolicy) {
        try {
            String query = String.format("insert into policies values (%d,\"%s\", \"%s\", \"%s\",\"%s\");", season.getYear(), league.getName(), rankPolicy.getName(), pointsPolicy.getName(), gamePolicy.getName());
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public static boolean addReferee(Referee referee) {
        if (!isRefExist(referee)) {
            try {
                String query = String.format("insert into referee (user_id,level) values (\"%d\",\"%d\")", UsersCRUD.getUserIdByUserName(referee.getUserName()), referee.getLevel());
                jdbcTemplate.update(query);
                return true;
            } catch (EmptyResultDataAccessException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean removeReferee(Referee referee) {
        try {
            String query = String.format("DELETE FROM referee WHERE user_id=%d", UsersCRUD.getUserIdByUserName(referee.getUserName()));
            jdbcTemplate.update(query);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
