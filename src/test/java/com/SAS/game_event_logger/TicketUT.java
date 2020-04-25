package com.SAS.game_event_logger;

import com.SAS.User.Player;
import com.SAS.User.Referee;
import com.SAS.User.UserController;
import com.SAS.User.UserType;
import com.SAS.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TicketUT {

    @Test
    void getAgainstPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Referee r = (Referee) u.createUser("Chen", "123456", "Chen Gelad", UserType.REFEREE, true,null);
        Ticket ticket = new RedTicket(gameID, LocalDate.now(),0,p,r);
        assertEquals(p.getFullName(),ticket.getAgainstPlayer().getFullName());
    }

    @Test
    void setAgainstPlayer() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Referee r = (Referee) u.createUser("Chen", "123456", "Chen Gelad", UserType.REFEREE, true,null);
        Ticket ticket = new RedTicket(gameID, LocalDate.now(),0,null,r);
        assertNull(ticket.getAgainstPlayer());
        ticket.setAgainstPlayer(p);
        assertEquals(p.getFullName(),ticket.getAgainstPlayer().getFullName());
    }

    @Test
    void getRefereePulled() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Referee r = (Referee) u.createUser("Chen", "123456", "Chen Gelad", UserType.REFEREE, true,null);
        Ticket ticket = new YellowTicket(gameID, LocalDate.now(),0,p,r);
        assertEquals(r.getFullName(),ticket.getRefereePulled().getFullName());
    }

    @Test
    void setRefereePulled() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Referee r = (Referee) u.createUser("Chen", "123456", "Chen Gelad", UserType.REFEREE, true,null);
        Ticket ticket = new YellowTicket(gameID, LocalDate.now(),0,p,null);
        assertNull(ticket.getRefereePulled());
        ticket.setRefereePulled(r);
        assertEquals(r.getFullName(),ticket.getRefereePulled().getFullName());
    }
}