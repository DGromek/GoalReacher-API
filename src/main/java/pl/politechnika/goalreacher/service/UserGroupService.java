package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.dto.JoinGroupDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.model.Status;
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
        newUserGroup.setStatus(Status.PENDING);

        return userGroupRepository.save(newUserGroup);
    }
}
