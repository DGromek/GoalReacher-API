package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
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
import pl.politechnika.goalreacher.utils.NotificationSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvitationService
{
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository, UserRepository userRepository, GroupRepository groupRepository, UserGroupRepository userGroupRepository)
    {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public Invitation getFromId(long id)
    {
        Optional<Invitation> invitation = invitationRepository.findById(id);
        return invitation.orElse(null);
    }

    public Invitation createInvitation(InvitationDTO invitationDTO, Authentication authentication) throws NotAuthorizedException
    {
        AppUser inviting = userRepository.findByEmail(authentication.getPrincipal().toString());
        AppUser invited = userRepository.findByEmail(invitationDTO.getInvitedEmail());
        AppGroup group = groupRepository.findByGuid(invitationDTO.getGuid());
        UserGroup invitingGroup = userGroupRepository.findByUserAndGroup(inviting, group);
        UserGroup isAlreadyInGroup = userGroupRepository.findByUserAndGroup(invited, group);

        if (inviting == null || invited == null || group == null || invitingGroup == null || isAlreadyInGroup != null)
        {
            return null;
        }

        if (invitingGroup.getRole() == Role.PENDING || invitingGroup.getRole() == Role.USER)
        {
            throw new NotAuthorizedException();
        }

        Invitation invitation = new Invitation();
        invitation.setGroup(group);
        invitation.setInvited(invited);
        invitation.setInviting(inviting);

        List<String> recipient = new ArrayList<>();
        recipient.add(invited.getOneSignalPlayerId());
        String message = String.format("%s %s invited you to group %s", inviting.getFirstName(), inviting.getLastName(), group.getName());
        String data = "{\"goto\": \"invitations\"}";

        NotificationSender.sendMessageToUsers(message, recipient, data);

        return invitationRepository.save(invitation);
    }

    public boolean deleteInvitation(AppUser user, long invitationId)
    {
        Optional<Invitation> invitation = invitationRepository.findById(invitationId);
        if (!invitation.isPresent())
        {
            return false;
        }

        if (invitation.get().getInvited() == user)
        {
            invitationRepository.delete(invitation.get());
            return true;
        }

        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, invitation.get().getGroup());
        if (userGroup == null || (userGroup.getRole() != Role.ADMIN && userGroup.getRole() != Role.CREATOR))
        {
            return false;
        }

        invitationRepository.delete(invitation.get());
        return true;
    }
}
