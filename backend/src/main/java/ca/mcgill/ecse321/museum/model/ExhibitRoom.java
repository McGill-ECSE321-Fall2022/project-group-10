package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

@Entity
// line 159 "../../../../..//MuseumSystem.ump"
public class ExhibitRoom extends Room {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //ExhibitRoom Attributes
    private int capacity;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}