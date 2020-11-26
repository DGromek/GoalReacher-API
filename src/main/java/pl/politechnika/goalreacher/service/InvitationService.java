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

    public Invitation createInvitation(InvitationDTO invitationDTO, Authentication authentication) throws Exception
    {
        AppUser inviting = userRepository.findByEmail(authentication.getPrincipal().toString());
        if(inviting == null) throw new NotAuthorizedException();
        AppUser invited = userRepository.findByEmail(invitationDTO.getInvitedEmail());
        if(invited == null) throw new UserNotExistingException();
        AppGroup group = groupRepository.findByGuid(invitationDTO.getGuid());
        if(group == null) throw new GroupNotExistingException();

        UserGroup invitingGroup = userGroupRepository.findByUserAndGroup(inviting, group);
        if(invitingGroup == null) throw new UserNotInGroupException();
        if(invitingGroup.getRole() == Role.PENDING || invitingGroup.getRole() == Role.USER) throw new NotAuthorizedException();

        UserGroup isAlreadyInGroup = userGroupRepository.findByUserAndGroup(invited, group);
        if(isAlreadyInGroup != null) throw new UserAlreadyInGroupException();

        Invitation invitation = new Invitation();
        invitation.setGroup(group);
        invitation.setInvited(invited);
        invitation.setInviting(inviting);

        return invitationRepository.save(invitation);
    }
}
