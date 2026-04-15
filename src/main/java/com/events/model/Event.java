package com.events.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.UUID;

public class Event {
    private String id;
    private String name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String location;
    private String description;

    public Event() {
        this.id = UUID.randomUUID().toString();
    }

    public Event(String name, LocalDate date, String location, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
