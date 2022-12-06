/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Person;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ScheduleBlockRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ScheduleBlockService {

    @Autowired ScheduleBlockRepository scheduleBlockRepository;

    @Autowired VisitorRepository visitorRepository;
    @Autowired AdministratorRepository administratorRepository;

    /**
     * Create a new schedule block
     *
     * @param startDate - start date of the schedule block
     * @param endDate - end date of the schedule block
     * @param event - event of the schedule block
     * @param visitFees - visit fees of the schedule block
     * @param visitCapacity - visit capacity of the schedule block
     * @return the created schedule block
     */
    @Transactional
    public ScheduleBlock createScheduleBlock(
            Date startDate, Date endDate, ScheduleEvent event, float visitFees, int visitCapacity) {
        ScheduleBlock scheduleBlock = new ScheduleBlock();

        // Set attributes of the schedule block
        scheduleBlock.setStartDate(startDate); // Must not be null
        scheduleBlock.setEndDate(endDate); // Must not be null
        scheduleBlock.setEvent(event);
        scheduleBlock.setVisitFees(visitFees);
        scheduleBlock.setVisitCapacity(visitCapacity);

        // Save the schedule block to the database
        return scheduleBlockRepository.save(scheduleBlock);
    }

    /**
     * Update a schedule block by its id
     *
     * @param id - id of the schedule block
     * @param startDate - start date of the schedule block
     * @param endDate - end date of the schedule block
     * @param event - event of the schedule block
     * @param visitFees - visit fees of the schedule block
     * @param visitCapacity - visit capacity of the schedule block
     * @return the updated schedule block
     */
    @Transactional
    public ScheduleBlock updateScheduleBlock(
            long id,
            Date startDate,
            Date endDate,
            ScheduleEvent event,
            float visitFees,
            int visitCapacity) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(id).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Set attributes of the schedule block
        scheduleBlock.setStartDate(startDate); // Must not be null
        scheduleBlock.setEndDate(endDate); // Must not be null
        scheduleBlock.setEvent(event);
        scheduleBlock.setVisitFees(visitFees);
        scheduleBlock.setVisitCapacity(visitCapacity);

        // Save the schedule block in the database
        return scheduleBlockRepository.save(scheduleBlock);
    }

    /**
     * Delete a schedule block by its id
     *
     * @param id - id of the schedule block
     */
    @Transactional
    public void deleteScheduleBlock(long id) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(id).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Delete the schedule block from the database
        scheduleBlockRepository.delete(scheduleBlock);
    }

    /**
     * Get a schedule block by its id
     *
     * @param id - id of the schedule block
     * @return - the schedule block
     */
    @Transactional
    public ScheduleBlock getScheduleBlock(long id) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(id).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        return scheduleBlock;
    }

    /**
     * Get all schedule blocks
     *
     * @return - all schedule blocks
     */
    @Transactional
    public List<ScheduleBlock> getAllScheduleBlocks() {
        return scheduleBlockRepository.findAll();
    }

    /**
     * Get all schedule blocks between two dates
     *
     * @param startDate - start date
     * @param endDate - end date
     * @return - all schedule blocks between the two dates
     */
    @Transactional
    public List<ScheduleBlock> getScheduleBlocksBetweenDates(Date startDate, Date endDate) {
        return scheduleBlockRepository
                .findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqual(
                        startDate, endDate);
    }

    // /**
    //  *  Get all schedule blocks of specified type between two dates
    //  * @param startDate - start date
    //  * @param endDate - end date
    //  * @param event - event type
    //  * @return - all schedule blocks of specified type between two dates
    //  */
    // @Transactional
    // public List<ScheduleBlock> getScheduleBlocksBetweenDatesAndEvent(Date startDate, Date
    // endDate, ScheduleEvent event) {
    //     return
    // scheduleBlockRepository.findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqualAndEvent(startDate, endDate, event);
    // }

    /**
     * Get all visitors of a schedule block
     *
     * @param id - id of the schedule block
     * @return - all visitors of the schedule block
     */
    @Transactional
    public List<Visitor> getVisitorsOnScheduleBlock(long id) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(id).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        return scheduleBlock.getVisitors();
    }

    /**
     * Add a visitor to a schedule block
     *
     * @param scheduleId - id of the schedule block
     * @param visitorId - id of the visitor
     */
    @Transactional
    public ScheduleBlock registerVisitorOnScheduleBlock(long scheduleId, long visitorId) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(scheduleId).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Get the visitor from the database using the id
        Visitor person = visitorRepository.findById(visitorId).orElse(null);

        // Check if the person exists and is a visitor
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such visitor");
        }

        // Check if the visitor is already registered to the schedule block
        if (scheduleBlock.getVisitors().stream()
                .anyMatch(visitor -> visitor.getId() == visitorId)) {
            throw new ServiceLayerException(
                    HttpStatus.BAD_REQUEST, "Visitor already registered to schedule block");
        }

        // Check if the schedule block is full
        if (scheduleBlock.getVisitors().size() >= scheduleBlock.getVisitCapacity()) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Schedule block is full");
        }

        // Add the visitor to the schedule block
        scheduleBlock.getVisitors().add(person);

        // Save the schedule block in the database
        return scheduleBlockRepository.save(scheduleBlock);
    }

    /**
     * Remove a visitor from a schedule block
     *
     * @param scheduleId - id of the schedule block
     * @param visitorId - id of the visitor
     */
    @Transactional
    public ScheduleBlock unregisterVisitorOnScheduleBlock(long scheduleId, long visitorId) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(scheduleId).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Get the visitor from the database using the id
        Person person = visitorRepository.findById(visitorId).orElse(null);

        // Check if the person exists and is a visitor
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such visitor");
        }

        // Check if the visitor is not registered to the schedule block
        if (scheduleBlock.getVisitors().stream()
                .noneMatch(visitor -> visitor.getId() == visitorId)) {
            throw new ServiceLayerException(
                    HttpStatus.BAD_REQUEST, "Visitor not registered to schedule block");
        }

        // Remove the visitor from the schedule block
        scheduleBlock.getVisitors().removeIf(visitor -> visitor.getId() == visitorId);

        // Save the schedule block in the database
        return scheduleBlockRepository.save(scheduleBlock);
    }

    /**
     * Get all staff of a schedule block
     *
     * @param id - id of the schedule block
     * @return - all staff of the schedule block
     */
    @Transactional
    public List<Administrator> getStaffOnScheduleBlock(long id) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(id).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        return scheduleBlock.getAdmins();
    }

    /**
     * Add a staff member to a schedule block
     *
     * @param scheduleId - id of the schedule block
     * @param staffId - id of the staff member
     */
    @Transactional
    public ScheduleBlock registerStaffOnScheduleBlock(long scheduleId, long staffId) {

        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(scheduleId).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Get the staff from the database using the id
        Administrator person = administratorRepository.findById(staffId).orElse(null);

        // Check if the person exists and is a staff
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such staff");
        }

        // Check if the staff is already registered to the schedule block
        if (scheduleBlock.getAdmins().stream().anyMatch(admin -> admin.getId() == staffId)) {
            throw new ServiceLayerException(
                    HttpStatus.BAD_REQUEST, "Staff already registered to schedule block");
        }

        // Add the staff to the schedule block
        scheduleBlock.getAdmins().add(person);

        // Save the schedule block in the database
        return scheduleBlockRepository.save(scheduleBlock);
    }

    /**
     * Remove a staff member from a schedule block
     *
     * @param scheduleId - id of the schedule block
     * @param staffId - id of the staff member
     */
    @Transactional
    public ScheduleBlock unregisterStaffOnScheduleBlock(long scheduleId, long staffId) {
        // Get the schedule block from the database using the id
        ScheduleBlock scheduleBlock = scheduleBlockRepository.findById(scheduleId).orElse(null);

        // Check if the schedule block exists
        if (scheduleBlock == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such schedule block");
        }

        // Get the staff from the database using the id
        Person person = administratorRepository.findById(staffId).orElse(null);

        // Check if the person exists and is a staff
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such staff");
        }

        // Check if the staff is not registered to the schedule block
        if (scheduleBlock.getAdmins().stream().noneMatch(admin -> admin.getId() == staffId)) {
            throw new ServiceLayerException(
                    HttpStatus.BAD_REQUEST, "Staff not registered to schedule block");
        }

        // Remove the staff from the schedule block
        scheduleBlock.getAdmins().removeIf(admin -> admin.getId() == staffId);

        // Save the schedule block in the database
        return scheduleBlockRepository.save(scheduleBlock);
    }
}
