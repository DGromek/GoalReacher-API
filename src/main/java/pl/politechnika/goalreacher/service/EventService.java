package pl.politechnika.goalreacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.goalreacher.dto.EventDTO;
import pl.politechnika.goalreacher.entity.AppGroup;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.entity.Event;
import pl.politechnika.goalreacher.entity.UserGroup;
import pl.politechnika.goalreacher.model.Role;
import pl.politechnika.goalreacher.repository.EventRepository;
import pl.politechnika.goalreacher.repository.GroupRepository;
import pl.politechnika.goalreacher.repository.UserGroupRepository;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService
{

    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final SimpleDateFormat formatter;

    @Autowired
    public EventService(EventRepository eventRepository, GroupRepository groupRepository, UserGroupRepository userGroupRepository)
    {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
        this.userGroupRepository = userGroupRepository;
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    public List<Event> getAllByGroupId(Long groupId)
    {
        return eventRepository.getAllByGroupId(groupId);
    }

    public List<Event> getAllByGroupFromToDate(Long groupId, Date from, Date to)
    {
        List<Event> matching = eventRepository.getAllByDatetimeBetweenAndGroupId(from, to, groupId);

        Collections.sort(matching);
        return matching;
    }

    public Event updateEvent(EventDTO newEventDTO)
    {
        Optional<Event> toChange = eventRepository.findById(newEventDTO.getId());
        if (!toChange.isPresent())
            return null;

        if (!newEventDTO.getDescription().isEmpty() && !newEventDTO.getDescription().equals(""))
            toChange.get().setDescription(newEventDTO.getDescription());
        if (!newEventDTO.getName().isEmpty() && !newEventDTO.getName().equals(""))
            toChange.get().setName(newEventDTO.getName());

        try
        {
            toChange.get().setDatetime(formatter.parse(newEventDTO.getDatetime()));
        } catch (Exception e)
        {
            return null;
        }
        return eventRepository.save(toChange.get());
    }

    public boolean deleteEvent(Long eventId, AppUser user)
    {
        Optional<Event> event = eventRepository.findById(eventId);
        if (!event.isPresent())
        {
            return false;
        }
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, event.get().getGroup());
        if (userGroup == null || userGroup.getRole() == Role.PENDING || userGroup.getRole() == Role.USER)
            return false;
        eventRepository.delete(event.get());
        return true;
    }

    public Event createEvent(EventDTO newEventDTO)
    {
        AppGroup group = groupRepository.findByGuid(newEventDTO.getGuid());
        if (group == null)
            return null;

        Event newEvent = new Event();

        newEvent.setGroup(group);
        newEvent.setDescription(newEventDTO.getDescription());
        newEvent.setName(newEventDTO.getName());

        try
        {
            newEvent.setDatetime(formatter.parse(newEventDTO.getDatetime()));
        } catch (Exception e)
        {
            return null;
        }


//        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//        cal.setTime(newEvent.getDatetime());

//        String cron = String.format("* %d %d %d %d *", cal.get(Calendar.MINUTE), cal.get(Calendar.HOUR)-1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1);

//        CronTrigger cronTrigger = new CronTrigger(cron);
//
//        taskScheduler.schedule(new SendNotificationsTask(newEvent), cronTrigger);

        return eventRepository.save(newEvent);
    }
}
