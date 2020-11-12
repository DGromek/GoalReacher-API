package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>
{

    AppUser findByEmail(String email);
}
