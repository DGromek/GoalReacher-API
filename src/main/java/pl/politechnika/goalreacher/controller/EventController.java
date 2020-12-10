package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.dto.EventDTO;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Event;
import pl.politechnika.goalreacher.service.EventService;
import pl.politechnika.goalreacher.service.UserService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController
{

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService)
    {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO newEventDTO)
    {
        Event saved = eventService.createEvent(newEventDTO);
        if (saved == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody EventDTO newEventDTO)
    {
        Event changed = eventService.updateEvent(newEventDTO);
        if (changed == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity<>(changed, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Event> deleteEvent(@RequestParam long eventId, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        if (eventService.deleteEvent(eventId, user))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/{groupId}/{from}/{to}")
    public ResponseEntity<List<Event>> getFromMonth(
            @PathVariable Long groupId,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date from,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date to)
    {
        return new ResponseEntity<>(eventService.getAllByGroupFromToDate(groupId, from, to), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<Event>> getAll(@PathVariable Long groupId)
    {
        return new ResponseEntity<>(eventService.getAllByGroupId(groupId), HttpStatus.OK);
    }
}
