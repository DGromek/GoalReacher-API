package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.dto.InvitationDTO;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Invitation;
import pl.politechnika.goalreacher.service.InvitationService;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/invitations")
public class InvitationController
{
    private final InvitationService invitationService;
    private final UserService userService;

    @Autowired
    public InvitationController(InvitationService invitationService, UserService userService)
    {
        this.invitationService = invitationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Invitation> inviteUser(@RequestBody InvitationDTO invitationDTO, Authentication authentication)
    {
        try
        {
            return new ResponseEntity<>(invitationService.createInvitation(invitationDTO, authentication), HttpStatus.OK);
        } catch (NotAuthorizedException e)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
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
