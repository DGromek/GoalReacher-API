package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.Note;
import pl.politechnika.goalreacher.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllByGroup(AppGroup appGroup) {
        return noteRepository.getAllByGroup(appGroup);
    }
}
