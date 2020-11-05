package pl.politechnika.goalreacher.entity;

import pl.politechnika.goalreacher.embeddable.UserGroupKey;

import javax.persistence.*;

@Entity
public class UserGroup
{
    private enum Status
    {
        CREATOR,
        ADMIN,
        USER,
        PENDING
    }

    @EmbeddedId
    UserGroupKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    Group group;

    Status status;

    private boolean googleCallendar;
}
