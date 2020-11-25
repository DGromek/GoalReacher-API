package pl.politechnika.goalreacher.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.repository.GroupRepository;
import pl.politechnika.goalreacher.repository.UserGroupRepository;
import pl.politechnika.goalreacher.repository.UserRepository;

import java.security.SecureRandom;

@Service
public class GroupService
{
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    private static final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();
    private static final int guidLength = 6;

    public GroupService(GroupRepository groupRepository, UserGroupRepository userGroupRepository, UserRepository userRepository)
    {
        this.groupRepository = groupRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    private String generateGuid()
    {
        StringBuilder ret;
        do
        {
            ret = new StringBuilder();
            for (int i = 0; i < guidLength; i++)
                ret.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        } while (groupRepository.findByGuid(ret.toString()) != null);
        return ret.toString();
    }

    public AppGroup findByGuid(String guid)
    {
        return groupRepository.findByGuid(guid);
    }

    public Iterable<AppGroup> findAllGroups()
    {
        return groupRepository.findAll();
    }

    public AppGroup saveGroup(AppGroup newGroup, Authentication authentication)
    {
        newGroup.setGuid(generateGuid());

        AppUser user = userRepository.findByEmail(authentication.getPrincipal().toString());
        AppGroup group = groupRepository.save(newGroup);

        UserGroup newUserGroup = new UserGroup();
        newUserGroup.setUser(user);
        newUserGroup.setGroup(group);
        newUserGroup.setGoogleCalendar(false);
        newUserGroup.setRole(Role.CREATOR);
        userGroupRepository.save(newUserGroup);

        AppGroup retGroup = groupRepository.findByGuid(group.getGuid());

        return retGroup;
    }
}
