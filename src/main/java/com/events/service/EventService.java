package com.events.service;

import com.events.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final List<Event> events;

    public EventService() {
        this.events = new ArrayList<>();
        // Example initial data
        events.add(new Event("Spring Boot Workshop", LocalDate.now().plusDays(5), "Tech Hub", "Learn Spring Boot fundamentals"));
        events.add(new Event("Tech Meetup", LocalDate.now().plusDays(10), "Convention Center", "Networking event for local developers"));
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
                .sorted(Comparator.comparing(Event::getDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    public List<Event> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEvents();
        }
        String lowerKeyword = keyword.toLowerCase();
        return events.stream()
                .filter(e -> (e.getName() != null && e.getName().toLowerCase().contains(lowerKeyword)) || 
                             (e.getLocation() != null && e.getLocation().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }
}
