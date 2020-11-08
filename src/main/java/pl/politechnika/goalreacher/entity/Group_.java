package pl.politechnika.goalreacher.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Group_
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String guid;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "group")
    @JsonBackReference
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference(value = "events")
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference(value = "notes")
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference(value = "invitations")
    private List<Note> invitations = new ArrayList<>();
}
