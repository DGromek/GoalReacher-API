package pl.politechnika.goalreacher.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String guid;

    @NotBlank
    private String name;

    private String description;

    @OneToMany(mappedBy = "group")
    @JsonBackReference
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @JsonBackReference
    private List<Note> invitations = new ArrayList<>();
}
