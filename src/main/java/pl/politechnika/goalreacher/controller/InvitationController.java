package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.dto.InvitationDTO;
import pl.politechnika.goalreacher.entity.Invitation;
import pl.politechnika.goalreacher.service.InvitationService;

@Controller
public class InvitationController {
    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/invitations")
    public ResponseEntity<Invitation> inviteUser(@RequestBody InvitationDTO invitationDTO, Authentication authentication) {
        try {
            Invitation invitation = invitationService.createInvitation(invitationDTO, authentication);
            if (invitation == null) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(invitation, HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
