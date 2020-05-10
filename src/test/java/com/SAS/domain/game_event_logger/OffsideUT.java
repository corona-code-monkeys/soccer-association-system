package com.SAS.domain.game_event_logger;

import com.SAS.domain.User.Player;
import com.SAS.domain.User.UserController;
import com.SAS.domain.User.UserType;
import com.SAS.domain.team.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OffsideUT {

    @Test
    void getTeamInFavor() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, p);
        assertEquals(t.getName(), offside.getTeamInFavor().getName());
    }

    @Test
    void setTeamInFavor() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, null, p);
        assertNull(offside.getTeamInFavor());
        offside.setTeamInFavor(t);
        assertEquals(t.getName(), offside.getTeamInFavor().getName());
    }

    @Test
    void getPlayerInOffside() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, p);
        assertEquals(p.getFullName(), offside.getPlayerInOffside().getFullName());
    }

    @Test
    void setPlayerInOffside() {
        UserController u = new UserController();
        String gameID = "1";
        Team t = new Team();
        t.setName("BGU");
        Player p = (Player) u.createUser("matan", "123456", "matan anavi", UserType.PLAYER, true,null);
        Offside offside = new Offside(gameID, LocalDate.now(), 0, t, null);
        assertNull(offside.getPlayerInOffside());
        offside.setPlayerInOffside(p);
        assertEquals(p.getFullName(), offside.getPlayerInOffside().getFullName());
    }
}