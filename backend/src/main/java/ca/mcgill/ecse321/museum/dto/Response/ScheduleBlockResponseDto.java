/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ScheduleBlockResponseDto {

    // =========================
    // Attributes
    // =========================

    private long id;
    private Date startDate;
    private Date endDate;
    private float visitFees;
    private int visitCapacity;
    private ScheduleEvent event;
    private List<Long> adminIds; // List of admin IDs that are associated with this schedule block
    private List<Long> visitorIds; // List of visitor IDs that are associated with this schedule block
    private int visitSize; // Number of visitors that are associated with this schedule block

    // =========================
    // Getters and Setters
    // =========================

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

    public List<Long> getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(List<Long> adminIds) {
        this.adminIds = adminIds;
    }

    public List<Long> getVisitorIds() {
        return visitorIds;
    }

    public void setVisitorIds(List<Long> visitorIds) {
        this.visitorIds = visitorIds;
    }

    public int getVisitSize() {
        return visitSize;
    }

    public void setVisitSize(int visitSize) {
        this.visitSize = visitSize;
    }

    // =========================
    // Handle methods
    // =========================

    public static ScheduleBlockResponseDto createDto(ScheduleBlock scheduleBlock) {
        if (scheduleBlock == null) {
            return null;
        }

        ScheduleBlockResponseDto scheduleBlockDto = new ScheduleBlockResponseDto();
        scheduleBlockDto.setId(scheduleBlock.getId());
        scheduleBlockDto.setStartDate(scheduleBlock.getStartDate());
        scheduleBlockDto.setEndDate(scheduleBlock.getEndDate());
        scheduleBlockDto.setVisitFees(scheduleBlock.getVisitFees());
        scheduleBlockDto.setVisitCapacity(scheduleBlock.getVisitCapacity());
        scheduleBlockDto.setEvent(scheduleBlock.getEvent());

        // Get admin IDs associated with this schedule block
        List<Long> adminsIds = new ArrayList<Long>();
        if (scheduleBlock.getAdmins() != null) {
            for (Administrator admin : scheduleBlock.getAdmins()) {
                adminsIds.add(admin.getId());
            }
        }
        scheduleBlockDto.setAdminIds(adminsIds);

        // Get visitor IDs associated with this schedule block
        List<Long> visitorIds = new ArrayList<Long>();
        if (scheduleBlock.getVisitors() != null) {
            for (ca.mcgill.ecse321.museum.model.Visitor visitor : scheduleBlock.getVisitors()) {
                visitorIds.add(visitor.getId());
            }
        }
        scheduleBlockDto.setVisitorIds(visitorIds);

        // Get number of visitors associated with this schedule block
        if (scheduleBlock.getVisitors() != null) {
            scheduleBlockDto.setVisitSize(scheduleBlock.getVisitors().size());
        } else {
            scheduleBlockDto.setVisitSize(0);
        }

        return scheduleBlockDto;
    }
}
