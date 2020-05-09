package com.SAS.game;

import com.SAS.User.Referee;
import com.SAS.User.User;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.game_event_logger.GameEvent;
import com.SAS.game_event_logger.Offence;
import com.SAS.team.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameAT {

    private UserController userController;
    private Referee referee;
    private Referee referee2;
    private List<Game> gameList;


    @BeforeEach
    void setUp() {
        userController = new UserController();
        referee = (Referee) userController.createUser("AviC", "password", "Avi Cohen", UserType.REFEREE, true, null);
        referee.setLevel(1);
        referee2 = (Referee) userController.createUser("AviC", "password", "Avi Cohen", UserType.REFEREE, true, null);
        referee2.setLevel(2);
        List<Referee> refereeList = new ArrayList<>();
        refereeList.add(referee);
        refereeList.add(referee2);
        Team t1 = new Team();
        t1.setName("team1");
        Team t2 = new Team();
        t2.setName("team2");
        Team t3 = new Team();
        t3.setName("team3");

        Game g1 = new Game();
        g1.setReferees(refereeList);
        g1.setHost(t1);
        g1.setGuest(t2);

        Game g2 = new Game();
        g2.setReferees(refereeList);
        g2.setHost(t2);
        g2.setGuest(t3);

        Game g3 = new Game();
        g3.setReferees(refereeList);
        g3.setHost(t3);
        g3.setGuest(t1);

        gameList = new ArrayList<>();
        gameList.add(g1);
        gameList.add(g2);
        gameList.add(g3);


    }

    private void printGames() {
        System.out.println("This are the game that are currently happening:");
        int i = 0;
        for (Game g : gameList) {
            System.out.println(i + ". Host team: " + g.getHost().getName() + " guestTeam: " + g.getGuest().getName());
            i++;
        }
        System.out.println("What game do you want to edit? (press the game number or " + gameList.size() + " to go back)");
    }

    private Game initializeTestCase() {
        printGames();
        int userChoice = 1;
        Game g = gameList.get(userChoice);
        System.out.println("this is the game you choose");
        System.out.println("Host team: " + g.getHost().getName() + " guestTeam: " + g.getGuest().getName());
        System.out.println("to edit press 0 to exit press 1");
        return g;
    }

    @Test
    void addEventToGameSuccess() {
        Game g = initializeTestCase();
        //user choose 0
        System.out.println("you choose 0");
        System.out.println("what event you want to add?");
        System.out.println("1. Goal");
        System.out.println("2. Injury");
        System.out.println("3. Offence");
        System.out.println("4. Offside");
        System.out.println("5. Player Sub..");
        System.out.println("6. Red Ticket");
        System.out.println("7. Yellow Ticket");
        int userChoice = 3;
        g.addGameEvent(new Offence("", LocalDate.now(), 30, null, null, "offence for test"));
        Assertions.assertEquals(1, g.getEvents().getEventList().size());
        Assertions.assertEquals("offence for test", ((Offence) g.getEvents().getEventList().get(0)).getDescription());

    }

    @Test
    void addEventToGameFail() {
        initializeTestCase();
        //user choose 1
        System.out.println("you choose 1");
        System.out.println("you went back to the main page");
    }

    @Test
    void createReport() {
        Game g = gameList.get(0);
        g.getEvents().sort();
        System.out.println("this is the report:");
        int i=1;
        for (GameEvent event :g.getEvents().getEventList()) {
            System.out.println(i + " " + event.toString());
            i++;
        }
    }


}
