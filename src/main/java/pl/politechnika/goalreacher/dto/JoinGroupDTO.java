package pl.politechnika.goalreacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JoinGroupDTO
{
    private String targetGroupGuid;
    private long userId;
}
