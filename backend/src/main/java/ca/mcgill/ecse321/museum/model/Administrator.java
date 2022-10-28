/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 57 "../../../../..//MuseumSystem.ump"
public abstract class Administrator extends Person {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Administrator Associations
    @OneToMany
    private List<Donation> assignedDonations;
    @OneToMany
    private List<Loan> assignedLoans;
    @ManyToMany
    private List<ScheduleBlock> shifts;
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