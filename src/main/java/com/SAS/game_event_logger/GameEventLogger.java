package com.SAS.game_event_logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameEventLogger {
    private String gameID;
    private LocalDate gameDate;
    private List<GameEvent> eventList;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public List<GameEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<GameEvent> eventList) {
        this.eventList = eventList;
    }

    public GameEventLogger(String gameID, LocalDate gameDate) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.eventList = new ArrayList<>();
    }

    public GameEventLogger() {
        this.gameDate = LocalDate.now();
        this.eventList = new ArrayList<>();
    }


    public void addNewEvent(GameEvent event) {
        eventList.add(event);
    }

    //when game end
    public void sort() {
        eventList.sort(Comparator.comparing(GameEvent::getGameMinute));
    }

    public boolean removeEvent(GameEvent event) {
        if (eventList.contains(event)) {
            eventList.remove(event);
            return true;
        } else {
            return false;
        }
    }
}
