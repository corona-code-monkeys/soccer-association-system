package com.SAS.game_event_logger;

import java.time.LocalDate;

public class YellowTicket extends Ticket {
    public YellowTicket(String gameID, LocalDate gameDate, Integer gameMinute, Player againstPlayer, Referee refereePulled) {
        super(gameID, gameDate, gameMinute, againstPlayer, refereePulled);
    }
}
