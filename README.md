# Event Registration API

Laboratory Work 6: Introduction to Spring Boot and Dependency Injection.

A small Spring Boot backend for the Event Registration scenario. The application works fully in memory: it has no database, no authentication and no frontend. It exposes simple GET endpoints that return JSON.

**Author:** [Full name], group [Group]

## Requirements

| Tool | Version |
| --- | --- |
| JDK | 17 or later (the Gradle toolchain is set to Java 17) |
| Gradle | Not required to install, the Gradle Wrapper (9.8.2) is included |
| Spring Boot | 4.1.2 (Spring Web MVC starter) |

An internet connection is needed on the first run so that Gradle can download its dependencies.

## How to run

Clone the repository and run the application from the project root.

macOS / Linux:

```bash
./gradlew bootRun
```

Windows:

```bat
gradlew.bat bootRun
```

If `./gradlew` gives a permission error on macOS or Linux, run `chmod +x gradlew` first.

You can also open the project in IntelliJ IDEA and run the main class `SpringProjectApplication`. When the application starts, the console shows that Tomcat started on port **8080**.

Run the tests:

```bash
./gradlew test
```

## Endpoints

Base URL: `http://localhost:8080`

| Method | URL | Description |
| --- | --- | --- |
| GET | `/api/hello` | Welcome message returned by `EventService` |
| GET | `/api/events` | List of all three events |
| GET | `/api/info` | Application name, course and semester from `application.properties` |
| GET | `/api/events/{id}` | Bonus: one event by id, or a clear JSON message with status 404 if the id does not exist |

### Example responses

`GET /api/hello`

```json
{
  "message": "Welcome to the Event Registration API"
}
```

`GET /api/events`

```json
[
  { "id": 1, "title": "Web Design Workshop", "location": "Room 101" },
  { "id": 2, "title": "JavaScript Quiz", "location": "Room 202" },
  { "id": 3, "title": "IT Career Talk", "location": "Confrence Hall" }
]
```

`GET /api/info`

```json
{
  "applicationName": "event-registration-api",
  "semester": 3,
  "course": "Web Technologies"
}
```

`GET /api/events/99` (status 404)

```json
{
  "id": 99,
  "message": "Event not found"
}
```

## Configuration

Settings are stored in `src/main/resources/application.properties`:

```properties
spring.application.name=event-registration-api
app.course = Web Technologies
app.semester = 3
server.port=8080
```

`RegistrationController` reads `spring.application.name`, `app.course` and `app.semester` with `@Value` and returns them from `/api/info`.

## Project structure

```
src/main/java/kz/iitu/registration/
├── SpringProjectApplication.java      # entry point, @SpringBootApplication
├── controller/
│   └── RegistrationController.java    # REST endpoints under /api
├── service/
│   └── EventService.java              # welcome message and in-memory event list
└── model/
    └── EventInfo.java                 # id, title, location
src/main/resources/
└── application.properties             # application settings
```

## How it works

**Model (`EventInfo`).** A simple class with the fields `id`, `title` and `location`, a constructor and getters. Spring Boot uses the getters to convert `EventInfo` objects to JSON.

**Service (`EventService`).** Marked with `@Service`, so Spring creates it as a bean. It stores the in-memory `List<EventInfo>` with three events, returns the welcome message, and finds an event by id. It contains the data and logic, but knows nothing about HTTP.

**Controller (`RegistrationController`).** Marked with `@RestController` with the base path `/api`. Each `@GetMapping` method handles one URL, calls the service and returns a result. `@RestController` means that returned objects are written directly to the HTTP response and converted to JSON, so there is no HTML page.

**Dependency injection.** The controller declares `EventService` as a `private final` field and receives it through its constructor. Spring finds the `EventService` bean and passes it in when it creates the controller, so the controller never calls `new EventService()` and does not hard-code the event list.

```java
private final EventService eventService;

public RegistrationController(EventService eventService) {
    this.eventService = eventService;
}
```

## Scope

This is a separate backend project for the first Spring Boot laboratory work. It does not use PostgreSQL, Spring Data JPA, entities, repositories, authentication or full CRUD operations, and it is not connected to a frontend. These parts are planned for later laboratories.
