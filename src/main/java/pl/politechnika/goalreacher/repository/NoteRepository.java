package pl.politechnika.goalreacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.Note;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> getAllByGroup(AppGroup appGroup);
}
