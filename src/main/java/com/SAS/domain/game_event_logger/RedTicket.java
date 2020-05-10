package com.SAS.domain.game_event_logger;

import com.SAS.domain.User.Player;
import com.SAS.domain.User.Referee;

import java.time.LocalDate;

public class RedTicket extends Ticket{

    public RedTicket(String gameID, LocalDate gameDate, Integer gameMinute, Player againstPlayer, Referee refereePulled) {
        super(gameID, gameDate, gameMinute, againstPlayer, refereePulled);
    }
}
