package com.SAS.game_event_logger;

import java.time.LocalDate;

public class PlayerSubstitution extends GameEvent {

    private Player in;
    private Player out;

    public PlayerSubstitution(String gameID, LocalDate gameDate, Integer gameMinute, Player in, Player out) {
        super(gameID, gameDate, gameMinute);
        this.in = in;
        this.out = out;
    }

    public Player getIn() {
        return in;
    }

    public void setIn(Player in) {
        this.in = in;
    }

    public Player getOut() {
        return out;
    }

    public void setOut(Player out) {
        this.out = out;
    }
}
