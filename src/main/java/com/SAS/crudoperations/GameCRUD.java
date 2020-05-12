package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.facility.Facility;
import com.SAS.game.Game;
import com.SAS.game_event_logger.*;
import com.SAS.team.Team;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

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

    private static boolean addGoalToGameHost(Game game) {
        int gameId = getGameId(game);
        if (gameId == -1) {
            return false;
        } else {
            try {
                String getNumOfGoalQuery = String.format("SELECT host_score FROM game WHERE game_id=%d;", gameId);
                Integer numOfGoal = jdbcTemplate.queryForObject(getNumOfGoalQuery, Integer.class);
                if (numOfGoal == null) {
                    return false;
                }
                String updateQuery = String.format("UPDATE game SET host_score=\"%d\" WHERE game_id = \"%d\";", numOfGoal + 1, gameId);
                jdbcTemplate.update(updateQuery);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private static boolean addGoalToGameGuest(Game game) {
        int gameId = getGameId(game);
        if (gameId == -1) {
            return false;
        } else {
            try {
                String getNumOfGoalQuery = String.format("SELECT guest_score FROM game WHERE game_id=%d;", gameId);
                Integer numOfGoal = jdbcTemplate.queryForObject(getNumOfGoalQuery, Integer.class);
                if (numOfGoal == null) {
                    return false;
                }
                String updateQuery = String.format("UPDATE game SET guest_score=\"%d\" WHERE game_id = \"%d\";", numOfGoal + 1, gameId);
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
     * the actual implementation of this function
     *
     * @param game
     * @param events
     * @return
     */
    public static boolean insertGameEvents(Game game, List<GameEvent> events) {
        if (game == null || events == null) {
            return false;
        } else {
            boolean answer = true;
            for (GameEvent event : events) {
                answer &= addGameEvent(game, event);
            }
            return answer;
        }
    }

    /**
     * on the moc just send null for false and real game for true.
     *
     * @param game
     * @param event
     * @return
     */
    public static boolean addGameEvent(Game game, GameEvent event) {
        if (game == null || event == null) {
            return false;
        }
        int gameID = getGameId(game);
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
                    if (goal.getScoringTeam().getName().equals(game.getHost().getName())) {
                        goalAdded = addGoalToGameHost(game);
                    } else {
                        goalAdded = addGoalToGameGuest(game);
                    }
                    if (!goalAdded) {
                        return false;
                    }
                    int userId = UsersCRUD.getUserIdByUserName(goal.getScoringPlayer().getUserName());
                    if (userId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO goal (game_id, game_date, game_event_id, game_minute, player_user_id, team_name) values " +
                            "(\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\")", gameID, game.getDate().toString(), eventID, event.getGameMinute(), userId, goal.getScoringTeam().getName());
                    jdbcTemplate.update(query);
                } else if (event instanceof Injury) {
                    Injury injury = (Injury) event;
                    int userId = UsersCRUD.getUserIdByUserName(injury.getInjuredPlayer().getUserName());
                    if (userId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO injury (game_id, game_date, game_event_id, game_minute, player_user_id, description) values" +
                            "(\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, game.getDate().toString(), eventID, event.getGameMinute(), userId, injury.getDescription());
                    jdbcTemplate.update(query);
                } else if (event instanceof PlayerSubstitution) {
                    PlayerSubstitution playerSubstitution = (PlayerSubstitution) event;
                    int playerInId = UsersCRUD.getUserIdByUserName(playerSubstitution.getIn().getUserName());
                    int playerOutId = UsersCRUD.getUserIdByUserName(playerSubstitution.getOut().getUserName());
                    if (playerInId == -1 || playerOutId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO player_substitution (game_id, game_event_id, game_date, game_minute, player_in_user_id, player_out_user_id) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\");", gameID, eventID, game.getDate().toString(), playerSubstitution.getGameMinute(), playerInId, playerOutId);
                    jdbcTemplate.update(query);
                } else if (event instanceof Offence) {
                    Offence offence = (Offence) event;
                    int playerCommittedID = UsersCRUD.getUserIdByUserName(offence.getCommitted().getUserName());
                    int playerAgainstId = UsersCRUD.getUserIdByUserName(offence.getCommitted().getUserName());
                    if (playerCommittedID == -1 || playerAgainstId == -1) {
                        return false;
                    }
                    String query = String.format("INSERT INTO offence (game_id, game_event_id, game_date, game_minute, player_committed_user_id, player_againt_user_id, description) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, eventID, game.getDate().toString(), offence.getGameMinute(), playerCommittedID, playerAgainstId, offence.getDescription());
                    jdbcTemplate.update(query);
                } else if (event instanceof Offside) {
                    Offside offside = (Offside) event;
                    int playerId = UsersCRUD.getUserIdByUserName(offside.getPlayerInOffside().getUserName());
                    //TBD
                } else if (event instanceof Ticket) {
                    Ticket ticket = (Ticket) event;
                    String type = (ticket instanceof RedTicket) ? "red" : "yellow";
                    int playerAgainstId = UsersCRUD.getUserIdByUserName(ticket.getAgainstPlayer().getUserName());
                    int refereePulledId = UsersCRUD.getUserIdByUserName(ticket.getRefereePulled().getUserName());
                    String query = String.format("INSERT INTO ticket (game_id, game_event_id, game_date, game_minute, player_against_user_id, referee_pulled_user_id, type) values" +
                            "(\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%d\",\"%s\");", gameID, eventID, game.getDate().toString(), ticket.getGameMinute(), playerAgainstId, refereePulledId, type);
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
     * the actual implementation of this function
     *
     * @param game
     * @param oldEvent
     * @param newEvent
     * @return
     */
    public static boolean editGameEvent(Game game, GameEvent oldEvent, GameEvent newEvent) {
        if (removeGameEvent(game, oldEvent)) {
            return addGameEvent(game, newEvent);
        } else {
            return false;
        }
    }

    /**
     * on the moc just send null for false and real game for true, if the event not exist in the game event logger will return false as well
     *
     * @param game
     * @param event
     * @return
     */
    public static boolean removeGameEvent(Game game, GameEvent event) {
        if (game == null || event == null) {
            return false;
        } else {
            int gameId = getGameId(game);
            if (gameId == -1) {
                return false;
            }
            try {
                String query = String.format("DELETE FROM game_game_event WHERE (game_id = %d AND game_minute = %d)", gameId, event.getGameMinute());
                jdbcTemplate.update(query);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * on the moc just send null for false and real game for true
     *
     * @param game
     * @return
     */
    public static List<GameEvent> getGameEvents(Game game) {
        //TBD
        if (game == null) {
            return null;
        } else {
            GameEventLogger logger = game.getEvents();
            return logger.getEventList();
        }
    }

}
