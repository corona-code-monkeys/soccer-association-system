package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Player;
import com.SAS.User.Referee;
import com.SAS.User.UserController;
import com.SAS.dbstub.dbStub;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.game.Game;
import com.SAS.game_event_logger.*;
import com.SAS.report.GameReport;
import com.SAS.team.Team;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameCRUDTest {

    private dbStub db;
    private Game game;
    private Player player1;
    private Player player2;
    private Referee referee;


    @BeforeEach
    void setUp() {
        UserController uc = new UserController();
        db = new dbStub();
        db.initializeDB();
        Season season = new Season(1992, new HashSet<>(), new HashSet<>());
        League league = new League("testLeague");
        Team host = new Team();
        host.setName("Macabi");
        Facility f = new Facility();
        f.setName("Macabi Stadium");
        f.setFacilityType(facilityType.STADIUM);
        host.addFacility(f);
        Team guest = new Team();
        guest.setName("Hapoel");
        game = new Game(season, league, LocalDate.now(), host, guest, 0, 0, f, new GameEventLogger(), new GameReport(), new LinkedList<Referee>());
        player1 = (Player) uc.createUser("TheKing", "123465", "Avi Cohen", "rami@gmail.com", "PLAYER", true);
        player2 = (Player) uc.createUser("TheQueen", "123465", "Limur Asayag", "rami@gmail.com", "PLAYER", true);
        referee = (Referee) uc.createUser("NotSoCool", "123456", "Adva Polak", "rami@gmail.com", "REFEREE", true);
        game.getReferees().add(referee);
    }

    @AfterEach
    void tearDown() {
        UsersCRUD.deleteUser("TheKing");
        UsersCRUD.deleteUser("TheQueen");
        UsersCRUD.deleteUser("NotSoCool");
    }

    @Test
    void addAndRemoveGame() {
        assertTrue(GameCRUD.addGame(game));
        assertNotEquals(-1, GameCRUD.getGameId(game));
        assertTrue(GameCRUD.removeGame(game));
        assertEquals(-1, GameCRUD.getGameId(game));
    }


    @Test
    void addAndRemoveGameEvent() {
        GameCRUD.addGame(game);
        int gameId = GameCRUD.getGameId(game);
        Goal goal = addGoal(gameId);
        Injury injury = addInjury(gameId);
        PlayerSubstitution playerSubstitution = addPlayerSubstitution(gameId);
        Offence offence = addOffence(gameId);
        Offside offside = addOffside(gameId);
        RedTicket redTicket = addRedTicket(gameId);
        YellowTicket yellowTicket = addYellowTicket(gameId);
        assertTrue(GameCRUD.removeGameEvent(gameId+"", goal));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", injury));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", playerSubstitution));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", offence));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", offside));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", redTicket));
        assertTrue(GameCRUD.removeGameEvent(gameId+"", yellowTicket));
        GameCRUD.removeGame(game);
    }

    private Goal addGoal(int gameId) {
        Goal goal = new Goal(Integer.toString(gameId), LocalDate.now(), 1, game.getHost(), player1);
        assertTrue(GameCRUD.addGameEvent(gameId + "", goal, goal.getGameDate()));
        return goal;
    }


    private Injury addInjury(int gameId) {
        Injury injury = new Injury(Integer.toString(gameId), LocalDate.now(), 2, player1, "this is a description for the injury");
        assertTrue(GameCRUD.addGameEvent(gameId + "", injury, injury.getGameDate()));
        return injury;
    }

    private PlayerSubstitution addPlayerSubstitution(int gameId) {
        PlayerSubstitution playerSubstitution = new PlayerSubstitution(Integer.toString(gameId), LocalDate.now(), 3, player1, player2);
        assertTrue(GameCRUD.addGameEvent(gameId + "", playerSubstitution, playerSubstitution.getGameDate()));
        return playerSubstitution;
    }

    private Offence addOffence(int gameId) {
        Offence offence = new Offence(Integer.toString(gameId), LocalDate.now(), 4, player1, player2, "this is the description for player sub");
        assertTrue(GameCRUD.addGameEvent(gameId + "", offence, offence.getGameDate()));
        return offence;
    }

    private Offside addOffside(int gameId) {
        Offside offside = new Offside(Integer.toString(gameId), LocalDate.now(), 5, game.getHost(), player2);
        assertTrue(GameCRUD.addGameEvent(gameId + "", offside, offside.getGameDate()));
        return offside;
    }

    private RedTicket addRedTicket(int gameId) {
        RedTicket redTicket = new RedTicket(Integer.toString(gameId), LocalDate.now(), 6, player1, referee);
        assertTrue(GameCRUD.addGameEvent(gameId + "", redTicket, redTicket.getGameDate()));
        return redTicket;
    }

    private YellowTicket addYellowTicket(int gameId) {
        YellowTicket yellowTicket = new YellowTicket(Integer.toString(gameId), LocalDate.now(), 7, player1, referee);
        assertTrue(GameCRUD.addGameEvent(gameId + "", yellowTicket, yellowTicket.getGameDate()));
        return yellowTicket;
    }

    @Test
    void insertGameEvents() {
        GameCRUD.addGame(game);
        int gameId = GameCRUD.getGameId(game);
        Goal goal = addGoal(gameId);
        GameCRUD.removeGameEvent(gameId + "", goal);
        Injury injury = addInjury(gameId);
        GameCRUD.removeGameEvent(gameId + "", injury);
        PlayerSubstitution playerSubstitution = addPlayerSubstitution(gameId);
        GameCRUD.removeGameEvent(gameId + "", playerSubstitution);
        Offence offence = addOffence(gameId);
        GameCRUD.removeGameEvent(gameId + "", offence);
        Offside offside = addOffside(gameId);
        GameCRUD.removeGameEvent(gameId + "", offside);
        RedTicket redTicket = addRedTicket(gameId);
        GameCRUD.removeGameEvent(gameId + "", redTicket);
        YellowTicket yellowTicket = addYellowTicket(gameId);
        GameCRUD.removeGameEvent(gameId + "", yellowTicket);
        List<GameEvent> list = new ArrayList<>();
        list.add(goal);
        list.add(injury);
        list.add(playerSubstitution);
        list.add(offence);
        list.add(offside);
        list.add(redTicket);
        list.add(yellowTicket);
        assertTrue(GameCRUD.insertGameEvents(gameId + "", list));
        GameCRUD.removeGame(game);
    }

    @Test
    void editGameEvent() {
        GameCRUD.addGame(game);
        int gameId = GameCRUD.getGameId(game);
        Injury injury = addInjury(gameId);
        Injury injury1 = new Injury(Integer.toString(gameId), LocalDate.now(), 30, player1, "this is the altered event description");
        assertTrue(GameCRUD.editGameEvent(gameId + "", injury, injury1));
        assertTrue(GameCRUD.removeGameEvent(gameId + "", injury1));
        GameCRUD.removeGame(game);
    }

    @Test
    void getAllGames() {
        JSONObject jsonObject1 = GameCRUD.getAllGames();
        int length = jsonObject1.getJSONArray("games").length();
        GameCRUD.addGame(game);
        JSONObject jsonObject2 = GameCRUD.getAllGames();
        Assertions.assertEquals(length+1, jsonObject2.getJSONArray("games").length());
        GameCRUD.removeGame(game);
        JSONObject jsonObject3 = GameCRUD.getAllGames();
        Assertions.assertEquals(length, jsonObject3.getJSONArray("games").length());
        Assertions.assertEquals(jsonObject1.toString(), jsonObject3.toString());

    }

    @Test
    void getGameEvents() {
        //TBD
    }
}
