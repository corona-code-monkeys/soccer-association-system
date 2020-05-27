/**
 * The class represents the api of game in the sas application
 */
package com.SAS.soccer_association_system;

import com.SAS.Controllers.sasApplication.SASApplication;
import com.SAS.User.Player;
import com.SAS.User.Referee;
import com.SAS.User.User;
import com.SAS.crudoperations.GameCRUD;
import com.SAS.crudoperations.TeamCRUD;
import com.SAS.crudoperations.UsersCRUD;
import com.SAS.game.Game;
import com.SAS.game_event_logger.*;
import com.SAS.team.Team;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RequestMapping(value = "/games")
@RestController
public class GamesAPIController {

    @Autowired
    private static SASApplication app = new SASApplication();

    @GetMapping(value = "/get")
    public String getAllGames() {
        return GameCRUD.getAllGames().toString();
    }

    @GetMapping(value = "/events/{gameID}")
    public String getGameEvents(@PathVariable String gameID) {
        return GameCRUD.getGameEvents(gameID).toString();
    }

    @GetMapping(value = "/referee/{gameID}")
    public String getRefereeForGame(@PathVariable String gameID) {
        return GameCRUD.getRefereesName(gameID).toString();//TBD fix it so its works as Yaar explained
    }

    @PostMapping(value = "/events/add")
    public String addEvent(@RequestBody String credentials) {
        try {
            JSONObject json = new JSONObject(credentials);
            String type = json.getString("type");
            String gameID = json.getString("game_id");
            LocalDate date = LocalDate.parse(json.getString("date"));
            int gameMinute = json.getInt("minute");
            switch (type) {
                case "goal":
                    String teamName = json.getString("team_name");
                    Team team = TeamCRUD.getTeamByName(teamName);
                    String playerName = json.getString("player_name");
                    Player player = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerName));
                    Goal goal = new Goal(gameID, date, gameMinute, team, player);
                    GameCRUD.addGameEvent(gameID, goal, date);
                    break;
                case "injury":
                    playerName = json.getString("player_name");
                    player = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerName));
                    String desc = json.getString("description");
                    Injury injury = new Injury(gameID, date, gameMinute, player, desc);
                    GameCRUD.addGameEvent(gameID, injury, date);
                    break;
                case "offence":
                    desc = json.getString("description");
                    String playerCommittedName = json.getString("player_committed");
                    Player playerCommitted = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerCommittedName));
                    String playerAgainstName = json.getString("player_against");
                    Player playerAgainst = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerAgainstName));
                    Offence offence = new Offence(gameID, date, gameMinute, playerCommitted, playerAgainst, desc);
                    GameCRUD.addGameEvent(gameID, offence, date);
                    break;
                case "offside":
                    playerName = json.getString("player_name");
                    player = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerName));
                    teamName = json.getString("team_name");
                    team = TeamCRUD.getTeamByName(teamName);
                    Offside offside = new Offside(gameID, date, gameMinute, team, player);
                    GameCRUD.addGameEvent(gameID, offside, date);
                    break;
                case "sub":
                    String playerInName = json.getString("player_in");
                    Player playerIn = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerInName));
                    String playerOutName = json.getString("player_out");
                    Player playerOut = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerOutName));
                    PlayerSubstitution playerSubstitution = new PlayerSubstitution(gameID, date, gameMinute, playerIn, playerOut);
                    GameCRUD.addGameEvent(gameID, playerSubstitution, date);
                    break;
                case "ticket":
                    String ticketType = json.getString("ticket_type");
                    playerAgainstName = json.getString("player_against");
                    playerAgainst = (Player) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(playerAgainstName));
                    String refereeName = json.getString("referee_pulled");
                    Referee referee = (Referee) UsersCRUD.getRegisteredUserByID(UsersCRUD.getUserIdByUserName(refereeName));
                    Ticket ticket;
                    if (ticketType.equalsIgnoreCase("yellow")) {
                        ticket = new YellowTicket(gameID, date, gameMinute, playerAgainst, referee);
                    } else {
                        ticket = new RedTicket(gameID, date, gameMinute, playerAgainst, referee);
                    }
                    GameCRUD.addGameEvent(gameID, ticket, date);
                    break;
                default:
                    throw new Exception("Illegal type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
