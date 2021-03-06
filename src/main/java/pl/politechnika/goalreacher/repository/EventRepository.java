package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getAllByGroupId(Long groupId);
    List<Event> getAllByDatetimeBetweenAndGroupId(Date datetime, Date datetime2, Long group);
}
