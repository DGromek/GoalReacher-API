package pl.politechnika.goalreacher.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.politechnika.goalreacher.Serializers.CustomGroupSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppGroup
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String guid;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private boolean isLocked;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonSerialize(using = CustomGroupSerializer.class)
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonBackReference(value = "invitations")
    private List<Invitation> invitations = new ArrayList<>();
}
