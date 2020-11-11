package pl.politechnika.goalreacher.embeddable;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserGroupKey implements Serializable
{
    @Column(name = "user_id")
    Long userId;

    @Column(name = "group_id")
    Long groupId;
}
