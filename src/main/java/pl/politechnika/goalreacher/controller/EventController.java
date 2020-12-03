package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.dto.EventDTO;
import pl.politechnika.goalreacher.entity.Event;
import pl.politechnika.goalreacher.service.EventService;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO newEventDTO)
    {
        Event saved = eventService.createEvent(newEventDTO);
        if(saved == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody EventDTO newEventDTO)
    {
        Event changed = eventService.updateEvent(newEventDTO);
        if(changed == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(changed, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Event> deleteEvent(@RequestParam long eventId)
    {
        if(eventService.deleteEvent(eventId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/{groupId}/{month}/{year}")
    public ResponseEntity<List<Event>> getFromMonth(@PathVariable Long groupId, @PathVariable int month, @PathVariable int year)
    {
        return new ResponseEntity<>(eventService.getAllByGroupIdAndMonthAndYear(groupId, month, year), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<Event>> getAll(@PathVariable Long groupId) {
        return new ResponseEntity<>(eventService.getAllByGroupId(groupId), HttpStatus.OK);
    }
}
