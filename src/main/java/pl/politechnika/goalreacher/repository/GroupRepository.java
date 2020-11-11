package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.Group_;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group_, Long>
{
    Group_ findByGuid(String guid);
}
