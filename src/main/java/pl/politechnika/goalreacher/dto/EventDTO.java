package pl.politechnika.goalreacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO
{
    private long id;
    private String guid;
    private String name;
    private String description;
    private String datetime;
}
