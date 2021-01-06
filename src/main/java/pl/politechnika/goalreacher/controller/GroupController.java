package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.service.GroupService;
import pl.politechnika.goalreacher.service.UserGroupService;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController
{
    private final GroupService groupService;
    private final UserService userService;
    private final UserGroupService userGroupService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, UserGroupService userGroupService)
    {
        this.groupService = groupService;
        this.userService = userService;
        this.userGroupService = userGroupService;
    }

    @GetMapping("/{guid}")
    public ResponseEntity<AppGroup> getGroupByGuid(@PathVariable String guid)
    {
        AppGroup ret = groupService.findByGuid(guid);
        if (ret == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/all") // DEV
    public ResponseEntity<Iterable<AppGroup>> getAll()
    {
        return new ResponseEntity<>(groupService.findAllGroups(), HttpStatus.OK);
    }

    @PutMapping("/block/{guid}")
    public ResponseEntity<AppGroup> changeBlockedStatus(@PathVariable String guid, Authentication authentication)
    {
        AppUser appUser = userService.findByEmail(authentication.getPrincipal().toString());
        AppGroup appGroup = groupService.findByGuid(guid);
        UserGroup userGroup = userGroupService.findByUserAndGroup(appUser, appGroup);
        if(userGroup != null && userGroup.getRole().ordinal() > Role.ADMIN.ordinal())
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(groupService.changeBlocked(appGroup), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppGroup> newGroup(@RequestBody AppGroup newGroup, Authentication authentication)
    {
        return new ResponseEntity<>(groupService.saveGroup(newGroup, authentication.getPrincipal().toString()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{guid}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String guid, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());
        boolean deleted = groupService.deleteGroup(guid, user);
        if (deleted)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }
}
