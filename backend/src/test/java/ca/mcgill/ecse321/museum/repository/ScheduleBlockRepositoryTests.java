/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.Visitor;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ScheduleBlockRepositoryTests {
    @Autowired ScheduleBlockRepository scheduleBlockRepository;
    @Autowired VisitorRepository visitorRepository;
    @Autowired AdministratorRepository administratorRepository;

    @AfterEach
    public void clearDatabase() {
        scheduleBlockRepository.deleteAll();
        visitorRepository.deleteAll();
        administratorRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistAndLoadScheduleBlock() {

        // Create all objects
        ScheduleBlock scheduleBlock = new ScheduleBlock();
        Administrator admin = new Employee();
        Visitor visitor = new Visitor();

        // Set visitor attribute
        String email = "harry.leib@gmail.com";
        visitor.setEmail(email);
        Boolean isActive = true;
        visitor.setActive(isActive);

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
        List<Administrator> admins = Arrays.asList(admin);
        scheduleBlock.setAdmins(admins);

        // Save schedule block to database
        scheduleBlockRepository.save(scheduleBlock);
        visitorRepository.save(visitor);
        administratorRepository.save(admin);

        Long scheduleId = scheduleBlock.getId();
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

        // Test admin association
        assertNotNull(scheduleBlock.getAdmins());
        assertEquals(1, scheduleBlock.getAdmins().size());
        assertEquals(adminId, scheduleBlock.getAdmins().get(0).getId());
    }
}
