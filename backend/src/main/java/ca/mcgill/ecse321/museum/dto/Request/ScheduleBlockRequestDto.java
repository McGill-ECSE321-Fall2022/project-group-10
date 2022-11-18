package ca.mcgill.ecse321.museum.dto.Request;

import java.sql.Date;

import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;

public class ScheduleBlockRequestDto {
    private Date startDate;
    private Date endDate;
    private float visitFees;
    private int visitCapacity;
    private ScheduleEvent event;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getVisitFees() {
        return visitFees;
    }

    public int getVisitCapacity() {
        return visitCapacity;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setVisitFees(float visitFees) {
        this.visitFees = visitFees;
    }

    public void setVisitCapacity(int visitCapacity) {
        this.visitCapacity = visitCapacity;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

}
