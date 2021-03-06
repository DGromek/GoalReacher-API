package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.Exceptions.*;
import pl.politechnika.goalreacher.dto.ChangeStatusDTO;
import pl.politechnika.goalreacher.dto.JoinGroupDTO;
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
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final InvitationRepository invitationRepository;

    @Autowired
    public UserGroupService(UserGroupRepository userGroupRepository, UserRepository userRepository, GroupRepository groupRepository, InvitationRepository invitationRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.invitationRepository = invitationRepository;
    }

    public UserGroup joinGroup(JoinGroupDTO joinGroupDTO, AppUser joiningUser) {
        AppGroup joiningGroup = groupRepository.findByGuid(joinGroupDTO.getTargetGroupGuid());
        if (joiningGroup == null) {
            return null;
        }

        if (joiningUser == null)
            return null;

        for (UserGroup userGroup : joiningGroup.getUsers()) {
            if (userGroup.getUser().equals(joiningUser))
                return null;
        }
        UserGroup newUserGroup = new UserGroup();
        newUserGroup.setUser(joiningUser);
        newUserGroup.setGroup(joiningGroup);
        newUserGroup.setGoogleCalendar(false);
        newUserGroup.setRole(Role.PENDING);

        List<String> recepients = new ArrayList<>();

        for(UserGroup userGroup : userGroupRepository.findByGroup(joiningGroup))
        {
            if(userGroup.getRole() == Role.CREATOR || userGroup.getRole() == Role.ADMIN)
            {
                if(userGroup.getUser().getOneSignalPlayerId() != null)
                    recepients.add(userGroup.getUser().getOneSignalPlayerId());
            }
        }

        String message = joiningUser.getFirstName() + " " + joiningUser.getLastName()
                + " is trying to join your " + joiningGroup.getName() + " group!";

        String data = "{\"goto\": \"users\", \"group\": \"" + joiningGroup.getGuid() + "\"}";

        if(!recepients.isEmpty())
            NotificationSender.sendMessageToUsers(message, recepients, data);

        return userGroupRepository.save(newUserGroup);
    }

    private UserGroup getUserGroup(String email, String guid) {
        AppGroup appGroup = groupRepository.findByGuid(guid);
        AppUser appUser = userRepository.findByEmail(email);

        return userGroupRepository.findByUserAndGroup(appUser, appGroup);
    }

    private boolean canOverrideStatus(UserGroup changee, UserGroup changer, Role role2B) {
        if (role2B != null && role2B.ordinal() == 0) {
            return false;
        }
        if (changee.getGroup().getId() == changer.getGroup().getId()) {
            if (changer.getRole().ordinal() == 0 && role2B.ordinal() > 0) {
                return true;
            }

            return changer.getRole().ordinal() == 1 && changer.getRole().ordinal() < changee.getRole().ordinal();
        }
        return false;
    }

    public UserGroup changeStatus(ChangeStatusDTO changeStatusDTO, String email) {
        UserGroup toChange = getUserGroup(changeStatusDTO.getTargetUserEmail(), changeStatusDTO.getTargetGroupGuid());
        UserGroup changer = getUserGroup(email, changeStatusDTO.getTargetGroupGuid());
        if (toChange == null || changer == null)
            return null;

        if (canOverrideStatus(toChange, changer, changeStatusDTO.getNewRole())) {
            toChange.setRole(changeStatusDTO.getNewRole());
            return userGroupRepository.save(toChange);
        }
        return null;
    }

    public void leaveGroup(ChangeStatusDTO changeStatusDTO, Authentication authentication) throws Exception {
        UserGroup toChange = getUserGroup(changeStatusDTO.getTargetUserEmail(), changeStatusDTO.getTargetGroupGuid());
        UserGroup changer = getUserGroup(authentication.getPrincipal().toString(), changeStatusDTO.getTargetGroupGuid());
        if (toChange == null || changer == null) {
            throw new UserNotInGroupException();
        }

        if (changeStatusDTO.getTargetUserEmail().equals(authentication.getPrincipal().toString())) {
            userGroupRepository.delete(toChange);
            return;
        } else {
            if (changer.getRole() == Role.CREATOR || (changer.getRole() == Role.ADMIN && toChange.getRole().ordinal() > 1)) {
                userGroupRepository.delete(toChange);
                return;
            }
        }
        throw new NotAuthorizedException();
    }

    public UserGroup joinFromInvitation(long invitationId, Authentication authentication) throws NotAuthorizedException {
        Optional<Invitation> invitation = invitationRepository.findById(invitationId);

        if (!invitation.isPresent()) {
            return null;
        }

        Optional<AppGroup> group = groupRepository.findById(invitation.get().getGroup().getId());
        Optional<AppUser> invited = userRepository.findById(invitation.get().getInvited().getId());

        if (!group.isPresent() || !invited.isPresent()) {
            return null;
        }

        AppUser authenticated = userRepository.findByEmail(authentication.getPrincipal().toString());
        if (authenticated != invited.get()) {
            throw new NotAuthorizedException();
        }

        UserGroup userGroup = userGroupRepository.findByUserAndGroup(invited.get(), group.get());
        if (userGroup != null) {
            if (userGroup.getRole() != Role.PENDING) {
                return null;
            } else {
                userGroup.setRole(Role.USER);
                return userGroupRepository.save(userGroup);
            }
        }

        UserGroup newUserGroup = new UserGroup();
        newUserGroup.setUser(invited.get());
        newUserGroup.setGroup(group.get());
        newUserGroup.setGoogleCalendar(false);
        newUserGroup.setRole(Role.USER);

        invitationRepository.delete(invitation.get());
        return userGroupRepository.save(newUserGroup);
    }

    public UserGroup findByUserAndGroup(AppUser appUser, AppGroup appGroup) {
        return userGroupRepository.findByUserAndGroup(appUser, appGroup);
    }
}
