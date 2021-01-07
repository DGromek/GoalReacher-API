package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.AppUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query(value = "SELECT * from app_user " +
            "inner join user_group ug on app_user.id = ug.user_id " +
            "inner join app_group ag on ag.id = ug.group_id " +
            "where ag.guid = ?1", nativeQuery = true)
    List<AppUser> findAllByGroupGUID(String guid);

    AppUser findByEmail(String email);
}
