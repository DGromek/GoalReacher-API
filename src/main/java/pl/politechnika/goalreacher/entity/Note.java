package pl.politechnika.goalreacher.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "group_id")
    private AppGroup group;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;
}
