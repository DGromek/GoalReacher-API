package pl.politechnika.goalreacher.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User_
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

    @OneToMany(mappedBy = "user")
    List<UserGroup> groups = new ArrayList<>();

    @OneToMany(mappedBy = "invited")
    List<Invitation> invitesGot = new ArrayList<>();

    @OneToMany(mappedBy = "inviting")
    List<Invitation> invitesSent = new ArrayList<>();
}
