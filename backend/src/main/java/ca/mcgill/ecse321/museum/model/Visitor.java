/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

@Entity
// line 95 "../../../../..//MuseumSystem.ump"
public class Visitor extends Person {

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------

    // Visitor Attributes
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
