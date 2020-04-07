package com.SAS.game_event_logger;

import java.time.LocalDate;

class Player{}//to be removed
class Referee{}//to be removed


public abstract class Ticket extends GameEvent {
    private Player againstPlayer;
    private Referee refereePulled;

    public Ticket(String gameID, LocalDate gameDate, Integer gameMinute, Player againstPlayer, Referee refereePulled) {
        super(gameID, gameDate, gameMinute);
        this.againstPlayer = againstPlayer;
        this.refereePulled = refereePulled;
    }

    public Player getAgainstPlayer() {
        return againstPlayer;
    }

    public void setAgainstPlayer(Player againstPlayer) {
        this.againstPlayer = againstPlayer;
    }

    public Referee getRefereePulled() {
        return refereePulled;
    }

    public void setRefereePulled(Referee refereePulled) {
        this.refereePulled = refereePulled;
    }
}
