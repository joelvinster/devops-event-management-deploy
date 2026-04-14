package com.events.controller;

import com.events.model.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EventController {
    private final List<Event> events;

    public EventController() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void updateEvent(String id, Event updatedEvent) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                Event existing = events.get(i);
                existing.setName(updatedEvent.getName());
                existing.setDate(updatedEvent.getDate());
                existing.setLocation(updatedEvent.getLocation());
                existing.setDescription(updatedEvent.getDescription());
                return;
            }
        }
    }

    public void deleteEvent(String id) {
        events.removeIf(event -> event.getId().equals(id));
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> getEventsSortedByDate() {
        return events.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toList());
    }

    public List<Event> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEvents();
        }
        String lowerKeyword = keyword.toLowerCase();
        return events.stream()
                .filter(e -> e.getName().toLowerCase().contains(lowerKeyword) || 
                             e.getLocation().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}
