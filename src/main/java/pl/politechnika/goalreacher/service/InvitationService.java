package pl.politechnika.goalreacher.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.Exceptions.*;
import pl.politechnika.goalreacher.dto.InvitationDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Invitation;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.repository.GroupRepository;
import pl.politechnika.goalreacher.repository.InvitationRepository;
import pl.politechnika.goalreacher.repository.UserGroupRepository;
import pl.politechnika.goalreacher.repository.UserRepository;

@Service
public class InvitationService
{
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    public InvitationService(InvitationRepository invitationRepository, UserRepository userRepository, GroupRepository groupRepository, UserGroupRepository userGroupRepository)
    {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public Invitation createInvitation(InvitationDTO invitationDTO, Authentication authentication) throws NotAuthorizedException
    {
        AppUser inviting = userRepository.findByEmail(authentication.getPrincipal().toString());
        AppUser invited = userRepository.findByEmail(invitationDTO.getInvitedEmail());
        AppGroup group = groupRepository.findByGuid(invitationDTO.getGuid());
        UserGroup invitingGroup = userGroupRepository.findByUserAndGroup(inviting, group);
        UserGroup isAlreadyInGroup = userGroupRepository.findByUserAndGroup(invited, group);

        if (inviting == null || invited == null || group == null || invitingGroup == null || isAlreadyInGroup == null) {
            return null;
        }

        if(invitingGroup.getRole() == Role.PENDING || invitingGroup.getRole() == Role.USER) {
            throw new NotAuthorizedException();
        }

        Invitation invitation = new Invitation();
        invitation.setGroup(group);
        invitation.setInvited(invited);
        invitation.setInviting(inviting);

        return invitationRepository.save(invitation);
    }
}
