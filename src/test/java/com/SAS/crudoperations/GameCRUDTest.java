package com.SAS.crudoperations;

import com.SAS.League.League;
import com.SAS.League.Season;
import com.SAS.User.Player;
import com.SAS.User.Referee;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.facility.Facility;
import com.SAS.facility.facilityType;
import com.SAS.game.Game;
import com.SAS.game_event_logger.*;
import com.SAS.report.GameReport;
import com.SAS.team.Team;
import org.junit.jupiter.api.AfterEach;
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
        player1 = (Player) uc.createUser("TheKing", "123465", "Avi Cohen", "rami@gmail.com","PLAYER", true);
        player2 = (Player) uc.createUser("TheQueen", "123465", "Limur Asayag", "rami@gmail.com","PLAYER", true);
        referee = (Referee) uc.createUser("NotSoCool", "123456", "Adva Polak", "rami@gmail.com","REFEREE", true);
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
        assertTrue(GameCRUD.removeGameEvent(game, goal));
        assertTrue(GameCRUD.removeGameEvent(game, injury));
        assertTrue(GameCRUD.removeGameEvent(game, playerSubstitution));
        assertTrue(GameCRUD.removeGameEvent(game, offence));
        assertTrue(GameCRUD.removeGameEvent(game, offside));
        assertTrue(GameCRUD.removeGameEvent(game, redTicket));
        assertTrue(GameCRUD.removeGameEvent(game, yellowTicket));
        GameCRUD.removeGame(game);
    }

    private Goal addGoal(int gameId) {
        Goal goal = new Goal(Integer.toString(gameId), LocalDate.now(), 1, game.getHost(), player1);
        assertTrue(GameCRUD.addGameEvent(game, goal));
        return goal;
    }


    private Injury addInjury(int gameId) {
        Injury injury = new Injury(Integer.toString(gameId), LocalDate.now(), 2, player1, "this is a description for the injury");
        assertTrue(GameCRUD.addGameEvent(game, injury));
        return injury;
    }

    private PlayerSubstitution addPlayerSubstitution(int gameId) {
        PlayerSubstitution playerSubstitution = new PlayerSubstitution(Integer.toString(gameId), LocalDate.now(), 3, player1, player2);
        assertTrue(GameCRUD.addGameEvent(game, playerSubstitution));
        return playerSubstitution;
    }

    private Offence addOffence(int gameId) {
        Offence offence = new Offence(Integer.toString(gameId), LocalDate.now(), 4, player1, player2, "this is the description for player sub");
        assertTrue(GameCRUD.addGameEvent(game, offence));
        return offence;
    }

    private Offside addOffside(int gameId) {
        Offside offside = new Offside(Integer.toString(gameId), LocalDate.now(), 5, game.getHost(), player2);
        assertTrue(GameCRUD.addGameEvent(game, offside));
        return offside;
    }

    private RedTicket addRedTicket(int gameId) {
        RedTicket redTicket = new RedTicket(Integer.toString(gameId), LocalDate.now(), 6, player1, referee);
        assertTrue(GameCRUD.addGameEvent(game, redTicket));
        return redTicket;
    }

    private YellowTicket addYellowTicket(int gameId) {
        YellowTicket yellowTicket = new YellowTicket(Integer.toString(gameId), LocalDate.now(), 7, player1, referee);
        assertTrue(GameCRUD.addGameEvent(game, yellowTicket));
        return yellowTicket;
    }

    @Test
    void insertGameEvents() {
        GameCRUD.addGame(game);
        int gameId = GameCRUD.getGameId(game);
        Goal goal = addGoal(gameId);
        GameCRUD.removeGameEvent(game, goal);
        Injury injury = addInjury(gameId);
        GameCRUD.removeGameEvent(game, injury);
        PlayerSubstitution playerSubstitution = addPlayerSubstitution(gameId);
        GameCRUD.removeGameEvent(game, playerSubstitution);
        Offence offence = addOffence(gameId);
        GameCRUD.removeGameEvent(game, offence);
        Offside offside = addOffside(gameId);
        GameCRUD.removeGameEvent(game, offside);
        RedTicket redTicket = addRedTicket(gameId);
        GameCRUD.removeGameEvent(game, redTicket);
        YellowTicket yellowTicket = addYellowTicket(gameId);
        GameCRUD.removeGameEvent(game, yellowTicket);
        List<GameEvent> list = new ArrayList<>();
        list.add(goal);
        list.add(injury);
        list.add(playerSubstitution);
        list.add(offence);
        list.add(offside);
        list.add(redTicket);
        list.add(yellowTicket);
        assertTrue(GameCRUD.insertGameEvents(game, list));
        GameCRUD.removeGame(game);
    }

    @Test
    void editGameEvent() {
        GameCRUD.addGame(game);
        int gameId = GameCRUD.getGameId(game);
        Injury injury = addInjury(gameId);
        Injury injury1 = new Injury(Integer.toString(gameId), LocalDate.now(), 30, player1, "this is the altered event description");
        assertTrue(GameCRUD.editGameEvent(game, injury, injury1));
        assertTrue(GameCRUD.removeGameEvent(game, injury1));
        GameCRUD.removeGame(game);
    }


    @Test
    void getGameEvents() {
        //TBD
    }
}
