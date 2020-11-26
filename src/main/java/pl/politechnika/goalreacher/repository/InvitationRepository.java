package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.Invitation;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvitationRepository extends JpaRepository<Invitation, Long>
{

}
