package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 57 "../../../../..//MuseumSystem.ump"
public abstract class Administrator extends Person {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @Id
    @GeneratedValue
    private Long id;

    //Administrator Associations
    @OneToMany
    private List<Donation> assignedDonations;
    @OneToMany
    private List<Loan> assignedLoans;
    @ManyToMany
    private List<ScheduleBlock> shifts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Donation> getAssignedDonations() {
        return assignedDonations;
    }

    public void setAssignedDonations(List<Donation> assignedDonations) {
        this.assignedDonations = assignedDonations;
    }

    public List<Loan> getAssignedLoans() {
        return assignedLoans;
    }

    public void setAssignedLoans(List<Loan> assignedLoans) {
        this.assignedLoans = assignedLoans;
    }

    public List<ScheduleBlock> getShifts() {
        return shifts;
    }

    public void setShifts(List<ScheduleBlock> shifts) {
        this.shifts = shifts;
    }

    
}