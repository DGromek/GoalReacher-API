package pl.politechnika.goalreacher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.service.GroupService;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController
{
    private final GroupService groupService;
    private final UserService userService;

    public GroupController(GroupService groupService, UserService userService)
    {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/{guid}")
    public ResponseEntity<AppGroup> getGroupByGuid(@PathVariable String guid)
    {
        AppGroup ret = groupService.findByGuid(guid);
        if (ret == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/all") // DEV
    public ResponseEntity<Iterable<AppGroup>> getAll()
    {
        return new ResponseEntity<>(groupService.findAllGroups(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppGroup> newGroup(@RequestBody AppGroup newGroup, Authentication authentication)
    {
        return new ResponseEntity<>(groupService.saveGroup(newGroup, authentication), HttpStatus.CREATED);
    }

    @DeleteMapping("/{guid}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String guid, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        UserGroup deleted = groupService.deleteGroup(guid, user);
        if (user == null || deleted != null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
