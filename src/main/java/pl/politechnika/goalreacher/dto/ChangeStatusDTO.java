package pl.politechnika.goalreacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.politechnika.goalreacher.model.Status;

@AllArgsConstructor
@Getter
@Setter
public class ChangeStatusDTO
{
    private String targetGroupGuid;
    private long userId;
    private Status newStatus;
}
