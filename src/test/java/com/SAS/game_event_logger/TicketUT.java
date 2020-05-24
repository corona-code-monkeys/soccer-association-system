package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.Referee;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.dbstub.dbStub;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TicketUT {

    @Test
    void getAgainstPlayer() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "matan@gmail.com", "PLAYER", true);
        Referee r = (Referee) u.createUser("Rami", "123456", "Rami Levi", "rami@gmail.com","REFEREE", true);
        Ticket ticket = new RedTicket(gameID, LocalDate.now(),0,p,r);
        assertEquals(p.getFullName(),ticket.getAgainstPlayer().getFullName());
        u.deleteUSer(p.getUserName());
        u.deleteUSer(r.getUserName());
    }

    @Test
    void setAgainstPlayer() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "matan@gmail.com", "PLAYER", true);
        Referee r = (Referee) u.createUser("Rami", "123456", "Rami Levi", "rami@gmail.com","REFEREE", true);
        Ticket ticket = new RedTicket(gameID, LocalDate.now(),0,null,r);
        assertNull(ticket.getAgainstPlayer());
        ticket.setAgainstPlayer(p);
        assertEquals(p.getFullName(),ticket.getAgainstPlayer().getFullName());
        u.deleteUSer(p.getUserName());
        u.deleteUSer(r.getUserName());
    }

    @Test
    void getRefereePulled() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "matan@gmail.com", "PLAYER", true);
        Referee r = (Referee) u.createUser("Rami", "123456", "Rami Levi", "rami@gmail.com","REFEREE", true);
        Ticket ticket = new YellowTicket(gameID, LocalDate.now(),0,p,r);
        assertEquals(r.getFullName(),ticket.getRefereePulled().getFullName());
        u.deleteUSer(p.getUserName());
        u.deleteUSer(r.getUserName());
    }

    @Test
    void setRefereePulled() {
        dbStub db = new dbStub();
        dbStub.initializeDB();
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", "matan@gmail.com", "PLAYER", true);
        Referee r = (Referee) u.createUser("Rami", "123456", "Rami Levi", "rami@gmail.com","REFEREE", true);
        Ticket ticket = new YellowTicket(gameID, LocalDate.now(),0,p,null);
        assertNull(ticket.getRefereePulled());
        ticket.setRefereePulled(r);
        assertEquals(r.getFullName(),ticket.getRefereePulled().getFullName());
        u.deleteUSer(p.getUserName());
        u.deleteUSer(r.getUserName());
    }
}