package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.UserGroup;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>
{
    UserGroup findByUser(AppUser user);

    Iterable<UserGroup> findByGroup(AppGroup group);

    UserGroup findByUserAndGroup(AppUser appUser, AppGroup appGroup);
}
