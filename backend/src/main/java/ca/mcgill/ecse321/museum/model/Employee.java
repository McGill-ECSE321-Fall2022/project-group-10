package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

// line 136 "../../../../..//MuseumSystem.ump"
@Entity
@DiscriminatorValue("Employee")
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