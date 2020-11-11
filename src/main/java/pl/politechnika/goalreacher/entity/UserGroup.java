package pl.politechnika.goalreacher.entity;

import pl.politechnika.goalreacher.embeddable.UserGroupKey;
import pl.politechnika.goalreacher.model.Status;

import javax.persistence.*;

@Entity
public class UserGroup
{

    @EmbeddedId
    UserGroupKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    Group_ group;

    Status status;

    private boolean googleCallendar;
}
