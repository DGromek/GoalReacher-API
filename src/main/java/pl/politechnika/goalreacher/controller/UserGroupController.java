package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.politechnika.goalreacher.dto.JoinGroupDTO;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.service.UserGroupService;

@Controller
@RequestMapping("/joinGroup")
public class UserGroupController
{
    private final UserGroupService userGroupRepository;

    @Autowired
    public UserGroupController(UserGroupService userGroupRepository)
    {
        this.userGroupRepository = userGroupRepository;
    }

    @PostMapping()
    public ResponseEntity<AppUser> joinGroup(@RequestBody JoinGroupDTO joinGroupDTO)
    {
        UserGroup joined = userGroupRepository.joinGroup(joinGroupDTO);

        if(joined == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(joined.getUser(), HttpStatus.OK);
    }

}
