package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.dto.NoteDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Note;
import pl.politechnika.goalreacher.service.GroupService;
import pl.politechnika.goalreacher.service.NoteService;
import pl.politechnika.goalreacher.service.UserGroupService;
import pl.politechnika.goalreacher.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final UserService userService;
    private final GroupService groupService;
    private final NoteService noteService;
    private final UserGroupService userGroupService;

    @Autowired
    public NoteController(UserService userService, GroupService groupService, NoteService noteService, UserGroupService userGroupService) {
        this.userService = userService;
        this.groupService = groupService;
        this.noteService = noteService;
        this.userGroupService = userGroupService;
    }

    @GetMapping("/{guid}")
    public ResponseEntity<List<Note>> getForGroup(@PathVariable String guid, Authentication authentication) {
        AppGroup appGroup = groupService.findByGuid(guid);
        AppUser appUser = userService.findByEmail(authentication.getPrincipal().toString());

        if (userGroupService.findByUserAndGroup(appUser, appGroup) == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(noteService.getAllByGroup(appGroup), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Note> save(@RequestBody NoteDTO noteDTO, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        AppGroup group = groupService.findByGuid(noteDTO.getGuid());
        if(user == null || group == null)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (userGroupService.findByUserAndGroup(user, group) == null)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(noteService.createNote(noteDTO, user, group), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> delete(@PathVariable long id, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        if(user == null || !noteService.deleteNote(id, user))
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@RequestBody NoteDTO noteDTO, @PathVariable long id, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        Note note = noteService.updateNote(noteDTO, user, id);
        if(note == null)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(note, HttpStatus.OK);
    }
}
