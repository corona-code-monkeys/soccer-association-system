package com.SAS.game_event_logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameEventLogger {
    private String gameID;
    private LocalDate gameDate;
    private List<GameEvent> eventList;

    public GameEventLogger(String gameID, LocalDate gameDate) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.eventList = new ArrayList<>();
    }

    public void addNewEvent(GameEvent event) {
        eventList.add(event);
    }

    //when game end
    public void sort() {
        eventList.sort(Comparator.comparing(GameEvent::getGameMinute));
    }
}
