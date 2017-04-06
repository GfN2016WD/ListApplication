package android.java.gfn.de.listapplication;

import java.util.List;

/**
 * Created by TN77 on 06.04.2017.
 */

public class EventManager {

    private static EventManager instance;
    private List<Event> eventList;
    private Event currentEvent;

    private EventManager() {

    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
}
