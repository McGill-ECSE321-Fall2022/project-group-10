package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

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

    public List<ScheduleBlock> getScheduleBlocks() {
        return scheduleBlocks;
    }

    public void setScheduleBlocks(List<ScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
    }
}