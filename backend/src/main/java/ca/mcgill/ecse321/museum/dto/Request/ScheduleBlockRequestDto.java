/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Request;

import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import java.sql.Date;

public class ScheduleBlockRequestDto {
    Date startDate;
    Date endDate;
    float visitFees;
    int visitCapacity;
    ScheduleEvent event;

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
