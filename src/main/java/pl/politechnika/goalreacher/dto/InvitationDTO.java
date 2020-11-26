package pl.politechnika.goalreacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvitationDTO
{
    private String guid;
    private String invitedEmail;
}
