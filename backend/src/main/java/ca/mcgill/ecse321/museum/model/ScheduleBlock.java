/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import java.sql.Date;
import java.util.*;
import javax.persistence.*;
import org.springframework.lang.NonNull;

@Entity
// line 28 "../../../../..//MuseumSystem.ump"
public class ScheduleBlock {

    // ------------------------
    // ENUMERATIONS
    // ------------------------

    // ScheduleBlock Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------
    @NonNull private Date startDate = new Date(0);
    @NonNull private Date endDate = new Date(0);
    private float visitFees;
    private int visitCapacity;

    private ScheduleEvent event;

    @ManyToMany private List<Administrator> admins;
    @ManyToMany private List<Visitor> visitors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        } else {
            this.startDate = new Date(0);
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        } else {
            this.endDate = new Date(0);
        }
    }

    public float getVisitFees() {
        return visitFees;
    }

    public void setVisitFees(float visitFees) {
        this.visitFees = visitFees;
    }

    public int getVisitCapacity() {
        return visitCapacity;
    }

    public void setVisitCapacity(int visitCapacity) {
        this.visitCapacity = visitCapacity;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public List<Administrator> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Administrator> admins) {
        this.admins = admins;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    public enum ScheduleEvent {
        MUSEUM_OPEN,
        MUSEUM_RESTORATION,
        MUSEUM_MEETING
    }
}
