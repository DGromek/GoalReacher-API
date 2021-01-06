package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.dto.NoteDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Note;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.repository.NoteRepository;
import pl.politechnika.goalreacher.repository.UserGroupRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService
{

    private final NoteRepository noteRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserGroupRepository userGroupRepository)
    {
        this.noteRepository = noteRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public List<Note> getAllByGroup(AppGroup appGroup)
    {
        return noteRepository.getAllByGroup(appGroup);
    }

    public Note createNote(NoteDTO noteDTO, AppUser user, AppGroup group)
    {
        Note note = new Note();
        note.setGroup(group);
        note.setAuthor(user);
        note.setContent(noteDTO.getContent());
        note.setTitle(noteDTO.getTitle());
        note.setLastEdited(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        return noteRepository.save(note);
    }

    public boolean deleteNote(long id, AppUser user)
    {
        Optional<Note> note = noteRepository.findById(id);

        if (!note.isPresent())
        {
            return false;
        }

        AppGroup group = note.get().getGroup();
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, group);
        if (userGroup == null || (userGroup.getRole() != Role.ADMIN || userGroup.getRole() != Role.CREATOR) && userGroup.getUser() != user)
        {
            return false;
        }
        noteRepository.delete(note.get());
        return true;
    }

    public Note updateNote(NoteDTO noteDTO, AppUser user, long id)
    {
        Optional<Note> noteO = noteRepository.findById(id);

        if (!noteO.isPresent())
        {
            return null;
        }

        AppGroup group = noteO.get().getGroup();
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, group);
        if (userGroup == null || (userGroup.getRole() != Role.ADMIN || userGroup.getRole() != Role.CREATOR) && userGroup.getUser() != user)
        {
            return null;
        }
        Note note = noteO.get();

        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setLastEdited(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        return noteRepository.save(note);
    }
}
