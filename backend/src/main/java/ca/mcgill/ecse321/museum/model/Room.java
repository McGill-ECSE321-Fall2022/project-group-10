/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "RoomType")
@SequenceGenerator(name = "seq", initialValue = 12)
public abstract class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    private long id;

    @NonNull private String name = "DefaultRoomName";

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) this.name = "DefaultRoomName";
        else this.name = name;
    }
}
