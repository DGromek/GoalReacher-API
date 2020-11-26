package pl.politechnika.goalreacher.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.politechnika.goalreacher.Serializers.CustomInvitationsGroupSerializer;
import pl.politechnika.goalreacher.Serializers.CustomInvitationsUserSerializer;
import pl.politechnika.goalreacher.Serializers.CustomUserSerializer;

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
    @JsonSerialize(using = CustomInvitationsGroupSerializer.class)
    private AppGroup group;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    @JsonSerialize(using = CustomInvitationsUserSerializer.class)
    private AppUser invited;

    @ManyToOne
    @JoinColumn(name = "inviting_id")
    @JsonSerialize(using = CustomInvitationsUserSerializer.class)
    private AppUser inviting;
}
