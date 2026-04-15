package com.events.controller;

import com.events.model.Event;
import com.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String viewEvents(Model model, 
                             @RequestParam(name = "keyword", required = false) String keyword, 
                             @RequestParam(name = "sort", required = false) String sort) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("events", eventService.searchEvents(keyword));
            model.addAttribute("keyword", keyword);
        } else if ("date".equals(sort)) {
            model.addAttribute("events", eventService.getEventsSortedByDate());
        } else {
            model.addAttribute("events", eventService.getAllEvents());
        }
        
        model.addAttribute("newEvent", new Event());
        return "events";
    }

    @PostMapping("/add")
    public String addEvent(@ModelAttribute("newEvent") Event event) {
        Event newEvent = new Event(event.getName(), event.getDate(), event.getLocation(), event.getDescription());
        eventService.addEvent(newEvent);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable("id") String id, @ModelAttribute("event") Event event) {
        eventService.updateEvent(id, event);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") String id) {
        eventService.deleteEvent(id);
        return "redirect:/";
    }
}
