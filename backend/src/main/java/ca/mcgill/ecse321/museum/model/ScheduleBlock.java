package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 28 "../../../../..//MuseumSystem.ump"
public class ScheduleBlock {

    //------------------------
    // ENUMERATIONS
    //------------------------

    //ScheduleBlock Attributes
    @Id
    @GeneratedValue
    private long id;

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    private Date startDate;
    private Date endDate;
    private float visitFees;
    private int visitCapacity;
    private ScheduleEvent event;
    //ScheduleBlock Associations
    @ManyToMany
    private List<Administrator> admins;
    @ManyToMany
    private List<Visitor> visitors;
    @ManyToOne
    private Calendar calendar;

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
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public enum ScheduleEvent {MUSEUM_OPEN, MUSEUM_RESTORATION, MUSEUM_MEETING}
}