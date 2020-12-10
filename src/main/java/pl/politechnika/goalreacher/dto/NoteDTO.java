package pl.politechnika.goalreacher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteDTO {

    private String title;
    private String content;
    private String guid;
}
