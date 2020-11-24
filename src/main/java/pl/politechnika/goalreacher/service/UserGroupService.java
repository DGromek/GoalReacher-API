package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.Exceptions.GroupNotExistingException;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.Exceptions.UserNotExistingException;
import pl.politechnika.goalreacher.Exceptions.UserNotInGroupException;
import pl.politechnika.goalreacher.dto.ChangeStatusDTO;
import pl.politechnika.goalreacher.dto.JoinGroupDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.repository.GroupRepository;
import pl.politechnika.goalreacher.repository.UserGroupRepository;
import pl.politechnika.goalreacher.repository.UserRepository;

import java.util.Optional;

@Service
public class UserGroupService
{
    UserGroupRepository userGroupRepository;
    UserRepository userRepository;
    GroupRepository groupRepository;

    @Autowired
    public UserGroupService(UserGroupRepository userGroupRepository, UserRepository userRepository, GroupRepository groupRepository)
    {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public UserGroup joinGroup(JoinGroupDTO joinGroupDTO)
    {
        AppGroup joiningGroup = groupRepository.findByGuid(joinGroupDTO.getTargetGroupGuid());
        if (joiningGroup == null)
            return null;

        Optional<AppUser> joiningUser = userRepository.findById(joinGroupDTO.getUserId());
        if (!joiningUser.isPresent())
            return null;

        for (UserGroup userGroup : joiningGroup.getUsers())
        {
            if (userGroup.getUser().equals(joiningUser.get()))
                return null;
        }
        UserGroup newUserGroup = new UserGroup();
        newUserGroup.setUser(joiningUser.get());
        newUserGroup.setGroup(joiningGroup);
        newUserGroup.setGoogleCalendar(false);
        newUserGroup.setRole(Role.PENDING);

        return userGroupRepository.save(newUserGroup);
    }

    private UserGroup getUserGroup(String email, String guid) throws Exception
    {
        AppGroup appGroup = groupRepository.findByGuid(guid);
        AppUser appUser = userRepository.findByEmail(email);
        if (appUser == null)
            throw new UserNotExistingException();
        if (appGroup == null)
            throw new GroupNotExistingException();
        return userGroupRepository.findByUserAndGroup(appUser, appGroup);
    }

    private boolean canOverrideStatus(UserGroup changee, UserGroup changer, Role role2B)
    {

        if(role2B != null && role2B.ordinal() == 0)
            return false;
        if(changee.getGroup().getId() == changer.getGroup().getId())
        {
            if(changer.getRole().ordinal() == 0 && role2B.ordinal() > 0)
                return true;

            return changer.getRole().ordinal() == 1 && changer.getRole().ordinal() < changee.getRole().ordinal();
        }
        return false;
    }

    public UserGroup changeStatus(ChangeStatusDTO changeStatusDTO, Authentication authentication) throws Exception
    {
        UserGroup toChange = getUserGroup(changeStatusDTO.getTargetUserEmail(), changeStatusDTO.getTargetGroupGuid());
        UserGroup changer = getUserGroup(authentication.getPrincipal().toString(), changeStatusDTO.getTargetGroupGuid());
        if(toChange == null || changer == null)
            throw new UserNotInGroupException();

        if(canOverrideStatus(toChange, changer, changeStatusDTO.getNewRole()))
            toChange.setRole(changeStatusDTO.getNewRole());
        else
            throw new NotAuthorizedException();

        return userGroupRepository.save(toChange);
    }

    public void leaveGroup(ChangeStatusDTO changeStatusDTO, Authentication authentication) throws Exception
    {
        UserGroup toChange = getUserGroup(changeStatusDTO.getTargetUserEmail(), changeStatusDTO.getTargetGroupGuid());
        UserGroup changer = getUserGroup(authentication.getPrincipal().toString(), changeStatusDTO.getTargetGroupGuid());
        if(toChange == null || changer == null)
            throw new UserNotInGroupException();

        if(changeStatusDTO.getTargetUserEmail().equals(authentication.getPrincipal().toString()))
        {
            userGroupRepository.delete(toChange);
            return;
        }
        else
        {
            if(changer.getRole() == Role.CREATOR || (changer.getRole() == Role.ADMIN && toChange.getRole().ordinal() > 1))
            {
                userGroupRepository.delete(toChange);
                return;
            }
        }
        throw new NotAuthorizedException();
    }
}
