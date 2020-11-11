package pl.politechnika.goalreacher.entity;

import pl.politechnika.goalreacher.model.Status;

import javax.persistence.*;

@Entity
public class UserGroup
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    Group_ group;

    Status status;

    private boolean googleCalendar;

}
