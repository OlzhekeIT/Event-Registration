package kz.iitu.registration.model;

public class EventInfo {
    private Long id;
    private String title;
    private String location;


    public EventInfo(Long id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

}
