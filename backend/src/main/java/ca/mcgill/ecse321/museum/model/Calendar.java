/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
// line 18 "../../../../..//MuseumSystem.ump"
public class Calendar {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Calendar Attributes
    @Id
    @GeneratedValue
    private long id;
    private boolean isMuseumOpen;

    //Calendar Associations
    @OneToOne
    private MuseumSystem museum;
    @OneToMany
    private List<ScheduleBlock> scheduleBlocks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMuseumOpen() {
        return isMuseumOpen;
    }

    public void setMuseumOpen(boolean museumOpen) {
        isMuseumOpen = museumOpen;
    }

    public MuseumSystem getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumSystem museum) {
        this.museum = museum;
    }

    public List<ScheduleBlock> getScheduleBlocks() {
        return scheduleBlocks;
    }

    public void setScheduleBlocks(List<ScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
    }
}