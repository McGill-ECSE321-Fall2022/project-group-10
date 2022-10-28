/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 166 "../../../../..//MuseumSystem.ump"
public class StorageRoom extends Room {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}