package pl.politechnika.goalreacher.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.politechnika.goalreacher.Serializers.CustomInvitationsUserSerializer;
import pl.politechnika.goalreacher.Serializers.CustomNoteGroupSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Note
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private Date lastEdited;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonSerialize(using = CustomNoteGroupSerializer.class)
    private AppGroup group;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonSerialize(using = CustomInvitationsUserSerializer.class)
    private AppUser author;
}
