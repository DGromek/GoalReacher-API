package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.User_;

@Repository
public interface UserRepository extends JpaRepository<User_, Long>
{

    User_ findByEmail(String email);
}
