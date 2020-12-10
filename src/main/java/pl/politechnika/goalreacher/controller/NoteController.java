package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
