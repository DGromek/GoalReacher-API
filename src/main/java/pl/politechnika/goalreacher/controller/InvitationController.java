package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.dto.InvitationDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Invitation;
import pl.politechnika.goalreacher.service.GroupService;
import pl.politechnika.goalreacher.service.InvitationService;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/invitations")
public class InvitationController
{
    private final InvitationService invitationService;
    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public InvitationController(InvitationService invitationService, UserService userService, GroupService groupService)
    {
        this.invitationService = invitationService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Invitation> inviteUser(@RequestBody InvitationDTO invitationDTO, Authentication authentication)
    {
        AppGroup group = groupService.findByGuid(invitationDTO.getGuid());
        if(group.isLocked())
        {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

        try
        {
            Invitation invitation = invitationService.createInvitation(invitationDTO, authentication);
            if (invitation == null)
            {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(invitation, HttpStatus.OK);
        } catch (NotAuthorizedException e)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invitation> deleteInvitation(@PathVariable long id, Authentication authentiaction)
    {
        AppUser user = userService.findByEmail(authentiaction.getPrincipal().toString());
        if (invitationService.deleteInvitation(user, id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
