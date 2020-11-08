package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.Group_;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group_, Long>
{
    Optional<Group_> findById(Long id);
    Group_ findByGuid(String guid);
}
