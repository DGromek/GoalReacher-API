package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.repository.GroupRepository;

import java.security.SecureRandom;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    private static final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();
    private static final int guidLength = 6;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    private String generateGuid(int length) {
        StringBuilder ret;
        do {
            ret = new StringBuilder();
            for (int i = 0; i < length; i++)
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

    public AppGroup saveGroup(AppGroup newGroup)
    {
        newGroup.setGuid(generateGuid(guidLength));
        return groupRepository.save(newGroup);
    }
}
