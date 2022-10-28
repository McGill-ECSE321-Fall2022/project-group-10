package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Calendar;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.Visitor;

@SpringBootTest
public class ScheduleBlockRepositoryTests {
    @Autowired ScheduleBlockRepository scheduleBlockRepository;
    @Autowired CalendarRepository calendarRepository;
    @Autowired VisitorRepository visitorRepository;
    @Autowired AdministratorRepository administratorRepository;

    @AfterEach
    public void clearDatabase() {
        scheduleBlockRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistAndLoadScheduleBlock() {

        // Create all objects
        ScheduleBlock scheduleBlock = new ScheduleBlock();
        Calendar calendar = new Calendar();
        Administrator admin = new Employee();
        Visitor visitor = new Visitor();
        
        // Set visitor attribute
        String email = "harry.leib@gmail.com";
        visitor.setEmail(email);
        Boolean isActive = true;
        visitor.setActive(isActive);

        // Set calendar attribute
        Boolean isMuseumOpen = true;
        calendar.setMuseumOpen(isMuseumOpen);

        // Set schedule block attributes
        Date startDate = Date.valueOf("2020-01-01");
        scheduleBlock.setStartDate(startDate);
        Date endDate = Date.valueOf("2020-01-02");
        scheduleBlock.setEndDate(endDate);
        Float visitFees = 10.0f;
        scheduleBlock.setVisitFees(visitFees);
        Integer visitCapacity = 100;
        scheduleBlock.setVisitCapacity(visitCapacity);
        ScheduleBlock.ScheduleEvent event = ScheduleBlock.ScheduleEvent.MUSEUM_OPEN;
        scheduleBlock.setEvent(event);
        scheduleBlock.setCalendar(calendar);
        List<Visitor> visitors = Arrays.asList(visitor);
        scheduleBlock.setVisitors(visitors);
        List<Administrator> admins = Arrays.asList(admin);
        scheduleBlock.setAdmins(admins);

        // Save schedule block to database
        scheduleBlockRepository.save(scheduleBlock);
        calendarRepository.save(calendar);
        visitorRepository.save(visitor);
        administratorRepository.save(admin);

        Long scheduleId = scheduleBlock.getId();
        Long calendarId = calendar.getId();
        Long visitorId = visitor.getId();
        Long adminId = admin.getId();

        // Load schedule block from database
        scheduleBlock = scheduleBlockRepository.findById(scheduleId).orElse(null);

        // Check that schedule block was loaded correctly
        // Test attributes of schedule block
        assertNotNull(scheduleBlock);
        assertEquals(scheduleId, scheduleBlock.getId());
        assertEquals(event, scheduleBlock.getEvent());
        assertEquals(startDate, scheduleBlock.getStartDate());
        assertEquals(endDate, scheduleBlock.getEndDate());
        assertEquals(visitFees, scheduleBlock.getVisitFees());
        assertEquals(visitCapacity, scheduleBlock.getVisitCapacity());
        // Test calendar association
        assertNotNull(scheduleBlock.getCalendar());
        assertEquals(calendarId, scheduleBlock.getCalendar().getId());
        assertEquals(isMuseumOpen, scheduleBlock.getCalendar().isMuseumOpen());
        // Test visitor association
        assertNotNull(scheduleBlock.getVisitors());
        assertEquals(1, scheduleBlock.getVisitors().size());
        assertEquals(visitorId, scheduleBlock.getVisitors().get(0).getId());
        assertEquals(email, scheduleBlock.getVisitors().get(0).getEmail());
        assertEquals(isActive, scheduleBlock.getVisitors().get(0).isActive());
        // Test admin association
        assertNotNull(scheduleBlock.getAdmins());
        assertEquals(1, scheduleBlock.getAdmins().size());
        assertEquals(adminId, scheduleBlock.getAdmins().get(0).getId());
    }
    
}
