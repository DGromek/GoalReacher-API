package pl.politechnika.goalreacher.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.politechnika.goalreacher.Serializers.CustomEventGroupSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event implements Comparable<Event>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonSerialize(using = CustomEventGroupSerializer.class)
    private AppGroup group;

    @NotEmpty
    private String name;

    private String description;

    private Date datetime;

    @Override
    public int compareTo(Event o)
    {
        return this.getDatetime().compareTo(o.getDatetime());
    }
}
