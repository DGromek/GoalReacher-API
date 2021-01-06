package pl.politechnika.goalreacher.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.Exceptions.NotAuthorizedException;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.repository.UserGroupRepository;
import pl.politechnika.goalreacher.repository.UserRepository;

@Service
public class UserService
{

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, UserGroupRepository userGroupRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser saveUser(AppUser newUser)
    {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public AppUser updateUser(AppUser updatedUser, Authentication authentication)
    {
        AppUser authenticatedUser = userRepository.findByEmail(authentication.getPrincipal().toString());
        if (authenticatedUser == null)
        {
            return null;
        }

        if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().equals(""))
            authenticatedUser.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null && !updatedUser.getLastName().equals(""))
            authenticatedUser.setLastName(updatedUser.getLastName());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().equals(""))
            authenticatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(""))
        {
            AppUser emailTestUser = userRepository.findByEmail(updatedUser.getEmail());
            if (emailTestUser == null || emailTestUser.equals(authenticatedUser))
            {
                authenticatedUser.setEmail(updatedUser.getEmail());
            } else
            {
                return null;
            }
        }

        userRepository.save(authenticatedUser);
        return userRepository.save(authenticatedUser);
    }

    public Iterable<AppUser> findAllUsers()
    {
        return userRepository.findAll();
    }

    public AppUser findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public AppUser updatePlayerId(String playerId, AppUser user)
    {
        user.setOneSignalPlayerId(playerId);

        return userRepository.save(user);
    }

    public void deleteUser(Authentication authentication) throws Exception
    {
        AppUser toDel = userRepository.findByEmail(authentication.getPrincipal().toString());
        if (toDel == null) throw new NotAuthorizedException();

        for (UserGroup group : toDel.getGroups())
        {
            userGroupRepository.delete(group);
        }

        userRepository.delete(toDel);
    }
}
