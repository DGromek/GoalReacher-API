package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.Group;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long>
{
    Optional<Group> findById(Long id);
    Group findByGuid(String guid);
}
