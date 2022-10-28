/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

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