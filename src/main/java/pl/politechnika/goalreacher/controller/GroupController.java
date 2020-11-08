package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.entity.Group_;
import pl.politechnika.goalreacher.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController
{
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @GetMapping("/{guid}")
    public ResponseEntity<Group_> getGroupByGuid(@PathVariable String guid)
    {
        return new ResponseEntity<>(groupService.findByGuid(guid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group_> newGroup(@RequestBody Group_ newGroup)
    {
        return new ResponseEntity<>(groupService.saveGroup(newGroup), HttpStatus.CREATED);
    }
}
