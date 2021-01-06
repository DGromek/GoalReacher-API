package pl.politechnika.goalreacher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.dto.ChangeStatusDTO;
import pl.politechnika.goalreacher.dto.JoinGroupDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.service.GroupService;
import pl.politechnika.goalreacher.service.UserGroupService;

@Controller
@RequestMapping()
public class UserGroupController
{
    private final UserGroupService userGroupRepository;
    private final GroupService groupService;

    public UserGroupController(UserGroupService userGroupRepository, GroupService groupService)
    {
        this.userGroupRepository = userGroupRepository;
        this.groupService = groupService;
    }

    @PostMapping("/joinGroup")
    public ResponseEntity<AppUser> joinGroup(@RequestBody JoinGroupDTO joinGroupDTO)
    {
        AppGroup group = groupService.findByGuid(joinGroupDTO.getTargetGroupGuid());
        if (group.isLocked())
        {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

        UserGroup joined = userGroupRepository.joinGroup(joinGroupDTO);

        if (joined == null)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(joined.getUser(), HttpStatus.OK);
    }

    @PutMapping("/users/changeStatus")
    public ResponseEntity<UserGroup> changeUserStatus(@RequestBody ChangeStatusDTO changeStatusDTO, Authentication authentication)
    {
        UserGroup toChange = userGroupRepository.changeStatus(changeStatusDTO, authentication.getPrincipal().toString());
        if (toChange == null)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(toChange, HttpStatus.OK);
    }

    @DeleteMapping("users/leaveGroup")
    public ResponseEntity<AppUser> leaveGroup(@RequestBody ChangeStatusDTO changeStatusDTO, Authentication authentication)
    {
        try
        {
            userGroupRepository.leaveGroup(changeStatusDTO, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/joinFromInvitation")
    public ResponseEntity<UserGroup> joinFromInvitation(@RequestParam long invitationId, Authentication authentication)
    {
        try
        {
            UserGroup userGroup = userGroupRepository.joinFromInvitation(invitationId, authentication);
            if (userGroup == null)
            {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(userGroup, HttpStatus.OK);
        } catch (NotAuthorizedException e)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
