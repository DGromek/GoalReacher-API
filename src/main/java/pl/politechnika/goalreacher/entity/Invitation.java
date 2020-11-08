package pl.politechnika.goalreacher.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invitation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group_ group;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private User invited;

    @ManyToOne
    @JoinColumn(name = "inviting_id")
    private User inviting;
}
