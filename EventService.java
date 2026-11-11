package kz.iitu.registration.service;

import kz.iitu.registration.model.EventInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final List<EventInfo> events = List.of(
            new EventInfo(1L, "Web Design Workshop", "Room 101"),
            new EventInfo(2L, "JavaScript Quiz", "Room 202"),
            new EventInfo(3L, "IT Career Talk", "Confrence Hall")
    );

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    public List<EventInfo> getEvents() {
        return events;
    }

    public EventInfo getEventById(Long id) {
        for (int i = 0; i < events.size(); i++) {
            EventInfo eventI = events.get(i);

            if (eventI.getId().equals(id))
                return eventI;
        }

        return null;
    }
}
