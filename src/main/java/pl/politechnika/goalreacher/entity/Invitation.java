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
    private AppGroup group;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private AppUser invited;

    @ManyToOne
    @JoinColumn(name = "inviting_id")
    private AppUser inviting;
}
