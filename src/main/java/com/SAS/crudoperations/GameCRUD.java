package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.facility.Facility;
import com.SAS.game.Game;
import com.SAS.game_event_logger.*;
import com.SAS.team.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class GameCRUD {

    private static JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * function that add game
     *
     * @param season     the season the game happen in
     * @param league     the league the game happen in
     * @param hostScore  the host goals
     * @param guestScore the guest goals
     * @param date       the date of the game
     * @param host       the host team of the game
     * @param guest      the guest team of the game
     * @param stadium    the stadium the game happen in
     * @return true if worked, false otherwise
     */
    public static boolean addGame(Season season, League league, int hostScore, int guestScore, LocalDate date, Team host, Team guest, Facility stadium) {
        try {
            int seasonYear = season.getYear();
            String leagueName = league.getName();
            String hostTeamName = host.getName();
            String guestTeamName = guest.getName();
            String stadiumName = stadium.getName();
            String query = String.format("INSERT INTO game (date, season_year, league_name, host_team_name, guest_team_name, host_score, guest_score, stadium_name)"
                    + "values (\"%s\",\"%d\",\"%s\",\"%s\",\"%s\",\"%d\",\"%d\",\"%s\");", date.toString(), seasonYear, leagueName, hostTeamName, guestTeamName, hostScore, guestScore, stadiumName);
            jdbcTemplate.update(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addGame(Game game) {
        return addGame(game.getSeason(), game.getLeague(), game.getHostScore(), game.getGuestScore(), game.getDate(), game.getHost(), game.getGuest(), game.getStadium());
    }

    public static boolean removeGame(Game game) {
        try {
            String query = String.format("DELETE FROM game WHERE host_team_name = \"%s\" AND guest_team_name = \"%s\" AND date = \"%s\";",
                    game.getHost().getName(), game.getGuest().getName(), game.getDate());
            jdbcTemplate.update(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean addGoalToGameHost(String gameId) {
        if (gameId == null) {
            return false;
        } else {
            try {
                String getNumOfGoalQuery = String.format("SELECT host_score FROM game WHERE game_id=%s;", gameId);
                Integer numOfGoal = jdbcTemplate.queryForObject(getNumOfGoalQuery, Integer.class);
                if (numOfGoal == null) {
                    return false;
                }
                String updateQuery = String.format("UPDATE game SET host_score=\"%d\" WHERE game_id = \"%s\";", numOfGoal + 1, gameId);
                jdbcTemplate.update(updateQuery);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private static boolean addGoalToGameGuest(String gameId) {
        if (gameId == null) {
            return false;
        } else {
            try {
                String getNumOfGoalQuery = String.format("SELECT guest_score FROM game WHERE game_id=%s;", gameId);
                Integer numOfGoal = jdbcTemplate.queryForObject(getNumOfGoalQuery, Integer.class);
                if (numOfGoal == null) {
                    return false;
                }
                String updateQuery = String.format("UPDATE game SET guest_score=\"%d\" WHERE game_id = \"%s\";", numOfGoal + 1, gameId);
                jdbcTemplate.update(updateQuery);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static int getGameId(Game game) {
        try {
            String date = game.getDate().toString();
            String hostTeamName = game.getHost().getName();
            String guestTeamName = game.getGuest().getName();
            String query = String.format("SELECT game_id FROM game WHERE date=\"%s\" AND host_team_name=\"%s\"" + " AND guest_team_name= \"%s\";",
                    date, hostTeamName, guestTeamName);
            Integer id = jdbcTemplate.queryForObject(query, Integer.class);
            if (id == null) {
                return -1;
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param gameID
     * @param events
     * @return
     */
    public static boolean insertGameEvents(String gameID, List<GameEvent> events) {
        if (gameID == null || events == null) {
            return false;
        } else {
            boolean answer = true;
            for (GameEvent event : events) {
                answer &= addGameEvent(gameID, event, event.getGameDate());
            }
            return answer;
        }
    }

    public static String getHostByGameID(String gameID) {
        String query = String.format("SELECT host_team_name FROM game WHERE game_id = %s", gameID);
        return jdbcTemplate.queryForObject(query, String.class);
    }

    /**
     * @param gameId
     * @param event
     * @param date
     * @return
     */
    public static boolean addGameEvent(String gameId, GameEvent event, LocalDate date) {
        if (event == null) {
            return false;
        }
        int gameID = Integer.parseInt(gameId);
        if (gameID == -1) {
            return false;
        } else {
            try {
                String queryForInsertEvent = String.format("INSERT INTO game_game_event (game_id,game_minute) values (\"%d\", \"%d\");", gameID, event.getGameMinute());
                jdbcTemplate.update(queryForInsertEvent);
                String queryForEventID = String.format("SELECT event_id FROM game_game_event WHERE (game_id=%d AND game_minute=%d);", gameID, event.getGameMinute());
                Integer eventID = jdbcTemplate.queryForObject(queryForEventID, Integer.class);
                if (eventID == null) {
                    return false;
                }
                if (event instanceof Goal) {
                    Goal goal = (Goal) event;
                    boolean goalAdded;
                    if (goal.getScoringTeam().getName().equals(getHostByGameID(gameId))) {
                        goalAdded = addGoalToGameHost(gameId);
                    } else {
                        goalAdded = addGoalToGameGuest(gameId);
                    }
                    if (!goalAdded) {
                        return false;
                    }
                    int userId = UsersCRUD.getUserIdByUserName(goal.getScoringPlayer().getUserName());
                    if (userId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO goal (game_id, game_date, game_event_id, game_minute, player_user_id, team_name) values " +
                            "(\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\")", gameID, date.toString(), eventID, event.getGameMinute(), userId, goal.getScoringTeam().getName());
                    jdbcTemplate.update(query);
                } else if (event instanceof Injury) {
                    Injury injury = (Injury) event;
                    int userId = UsersCRUD.getUserIdByUserName(injury.getInjuredPlayer().getUserName());
                    if (userId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO injury (game_id, game_date, game_event_id, game_minute, player_user_id, description) values" +
                            "(\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, date.toString(), eventID, event.getGameMinute(), userId, injury.getDescription());
                    jdbcTemplate.update(query);
                } else if (event instanceof PlayerSubstitution) {
                    PlayerSubstitution playerSubstitution = (PlayerSubstitution) event;
                    int playerInId = UsersCRUD.getUserIdByUserName(playerSubstitution.getIn().getUserName());
                    int playerOutId = UsersCRUD.getUserIdByUserName(playerSubstitution.getOut().getUserName());
                    if (playerInId == -1 || playerOutId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO player_substitution (game_id, game_event_id, game_date, game_minute, player_in_user_id, player_out_user_id) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\");", gameID, eventID, date.toString(), playerSubstitution.getGameMinute(), playerInId, playerOutId);
                    jdbcTemplate.update(query);
                } else if (event instanceof Offence) {
                    Offence offence = (Offence) event;
                    int playerCommittedID = UsersCRUD.getUserIdByUserName(offence.getCommitted().getUserName());
                    int playerAgainstId = UsersCRUD.getUserIdByUserName(offence.getCommitted().getUserName());
                    if (playerCommittedID == -1 || playerAgainstId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO offence (game_id, game_event_id, game_date, game_minute, player_committed_user_id, player_againt_user_id, description) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, eventID, date.toString(), offence.getGameMinute(), playerCommittedID, playerAgainstId, offence.getDescription());
                    jdbcTemplate.update(query);
                } else if (event instanceof Offside) {
                    Offside offside = (Offside) event;
                    int playerId = UsersCRUD.getUserIdByUserName(offside.getPlayerInOffside().getUserName());
                    String teamName = offside.getTeamInFavor().getName();
                    String query = String.format("INSERT INTO offside (game_id, game_event_id, game_date, game_minute, player_in_offside_user_id, team_in_favor) values" +
                            "(\"%d\", \"%s\", \"%s\",\"%d\",\"%d\",\"%s\" );", gameID, eventID, date.toString(), offside.getGameMinute(), playerId, teamName);
                    jdbcTemplate.update(query);
                } else if (event instanceof Ticket) {
                    Ticket ticket = (Ticket) event;
                    String type = (ticket instanceof RedTicket) ? "red" : "yellow";
                    int playerAgainstId = UsersCRUD.getUserIdByUserName(ticket.getAgainstPlayer().getUserName());
                    int refereePulledId = UsersCRUD.getUserIdByUserName(ticket.getRefereePulled().getUserName());
                    String query = String.format("INSERT INTO ticket (game_id, game_event_id, game_date, game_minute, player_against_user_id, referee_pulled_user_id, type) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, eventID, date.toString(), ticket.getGameMinute(), playerAgainstId, refereePulledId, type);
                    jdbcTemplate.update(query);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    /**
     * @param gameId
     * @param oldEvent
     * @param newEvent
     * @return
     */
    public static boolean editGameEvent(String gameId, GameEvent oldEvent, GameEvent newEvent) {
        if (removeGameEvent(gameId, oldEvent)) {
            return addGameEvent(gameId, newEvent, newEvent.getGameDate());
        } else {
            return false;
        }
    }

    /**
     * @param gameID
     * @param event
     * @return
     */
    public static boolean removeGameEvent(String gameID, GameEvent event) {
        if (gameID == null || event == null) {
            return false;
        } else {
            try {
                String query = String.format("DELETE FROM game_game_event WHERE (game_id = %s AND game_minute = %d)", gameID, event.getGameMinute());
                jdbcTemplate.update(query);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static JSONObject getGameEvents(String gameID) {
        String queryForGoal = String.format("SELECT * FROM goal WHERE game_id = %s", gameID);
        String queryForInjury = String.format("SELECT * FROM injury WHERE game_id = %s", gameID);
        String queryForOffence = String.format("SELECT * FROM offence WHERE game_id = %s", gameID);
        String queryForOffside = String.format("SELECT * FROM offside WHERE game_id = %s", gameID);
        String queryForPlayerSub = String.format("SELECT * FROM player_substitution WHERE game_id = %s", gameID);
        String queryForTicket = String.format("SELECT * FROM ticket WHERE game_id = %s", gameID);
        JSONObject events = new JSONObject();
        try {
            List<Map<String, Object>> goals = jdbcTemplate.queryForList(queryForGoal);
            List<Map<String, Object>> injuries = jdbcTemplate.queryForList(queryForInjury);
            List<Map<String, Object>> offences = jdbcTemplate.queryForList(queryForOffence);
            List<Map<String, Object>> offsides = jdbcTemplate.queryForList(queryForOffside);
            List<Map<String, Object>> subs = jdbcTemplate.queryForList(queryForPlayerSub);
            List<Map<String, Object>> tickets = jdbcTemplate.queryForList(queryForTicket);


            JSONArray goalsArr = new JSONArray();
            for (Map<String, Object> goal : goals) {
                JSONObject record = new JSONObject();
                record.put("game_minute", goal.get("game_minute"));
                String playerName = UsersCRUD.getUserNameById((int) goal.get("player_user_id"));
                record.put("player", playerName);
                record.put("team_name", goal.get("team_name"));
                goalsArr.put(record);
            }
            events.put("goals", goalsArr);

            JSONArray injuryArr = new JSONArray();
            for (Map<String, Object> injury : injuries) {
                JSONObject record = new JSONObject();
                record.put("game_minute", injury.get("game_minute"));
                String playerName = UsersCRUD.getUserNameById((int) injury.get("player_user_id"));
                record.put("player", playerName);
                record.put("description", injury.get("description"));
                injuryArr.put(record);
            }
            events.put("injuries", injuryArr);

            JSONArray offenceArr = new JSONArray();
            for (Map<String, Object> offence : offences) {
                JSONObject record = new JSONObject();
                record.put("game_minute", offence.get("game_minute"));
                String playerCommitted = UsersCRUD.getUserNameById((int) offence.get("player_committed_user_id"));
                String playerAgainst = UsersCRUD.getUserNameById((int) offence.get("player_againt_user_id"));
                record.put("player_committed", playerCommitted);
                record.put("player_against", playerAgainst);
                record.put("description", offence.get("description"));
                offenceArr.put(record);
            }
            events.put("offences", offenceArr);

            JSONArray offsideArr = new JSONArray();
            for (Map<String, Object> offside : offsides) {
                JSONObject record = new JSONObject();
                record.put("game_minute", offside.get("game_minute"));
                String playerInOffside = UsersCRUD.getUserNameById((int) offside.get("player_in_offside_user_id"));
                record.put("player_committed", playerInOffside);
                record.put("team_in_favor", offside.get("team_in_favor"));
                offsideArr.put(record);
            }
            events.put("offsides", offsideArr);

            JSONArray subsArr = new JSONArray();
            for (Map<String, Object> sub : subs) {
                JSONObject record = new JSONObject();
                record.put("game_minute", sub.get("game_minute"));
                String playerIn = UsersCRUD.getUserNameById((int) sub.get("player_in_user_id"));
                String playerOut = UsersCRUD.getUserNameById((int) sub.get("player_out_user_id"));
                record.put("player_in", playerIn);
                record.put("player_out", playerOut);
                subsArr.put(record);
            }
            events.put("subs", subsArr);

            JSONArray ticketsArr = new JSONArray();
            for (Map<String, Object> ticket : tickets) {
                JSONObject record = new JSONObject();
                record.put("game_minute", ticket.get("game_minute"));
                String playerAgainst = UsersCRUD.getUserNameById((int) ticket.get("player_against_user_id"));
                String refereePulled = UsersCRUD.getUserNameById((int) ticket.get("referee_pulled_user_id"));
                record.put("player_against", playerAgainst);
                record.put("referee_pulled", refereePulled);
                record.put("type", ticket.get("type"));
                ticketsArr.put(record);
            }
            events.put("tickets", ticketsArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public static JSONObject getAllGames() {
        String query = String.format("SELECT * FROM game");
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> row : rows) {
            JSONObject record = new JSONObject();
            record.put("game_id", row.get("game_id"));
            record.put("date", row.get("date"));
            record.put("season_year", row.get("season_year"));
            record.put("league_name", row.get("league_name"));
            record.put("host_team_name", row.get("host_team_name"));
            record.put("guest_team_name", row.get("guest_team_name"));
            record.put("host_score", row.get("host_score"));
            record.put("guest_score", row.get("guest_score"));
            record.put("stadium_name", row.get("stadium_name"));
            jsonArray.put(record);
        }
        jsonObject.put("games", jsonArray);
        return jsonObject;
    }

}
