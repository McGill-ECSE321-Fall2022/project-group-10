/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

// line 136 "../../../../..//MuseumSystem.ump"
@Entity
public class Employee extends Administrator {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Employee Attributes
    private boolean isActive;
    private float salary;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}