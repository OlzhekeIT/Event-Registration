package kz.iitu.registration.controller;

import kz.iitu.registration.model.EventInfo;
import kz.iitu.registration.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final EventService eventService;

    @Value("${spring.application.name}")
    private String applictaionName;

    @Value("${app.course}")
    private String course;

    @Value("${app.semester}")
    private int semester;

    public RegistrationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", eventService.getWelcomeMessage());
    }

    @GetMapping("/events")
    public List<EventInfo> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        return Map.of("applicationName", applictaionName, "course", course, "semester", semester);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        EventInfo event = eventService.getEventById(id);

        if (event == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Event not found","id", id));

        return ResponseEntity.ok(event);
    }

}
