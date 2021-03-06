package pl.politechnika.goalreacher.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.politechnika.goalreacher.Serializers.CustomUserSerializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private String oneSignalPlayerId;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonSerialize(using = CustomUserSerializer.class)
    List<UserGroup> groups = new ArrayList<>();

    @OneToMany(mappedBy = "invited")
    List<Invitation> invitesGot = new ArrayList<>();

    @OneToMany(mappedBy = "inviting")
    List<Invitation> invitesSent = new ArrayList<>();
}
