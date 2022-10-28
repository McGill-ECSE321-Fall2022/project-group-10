package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.museum.model.Calendar;
import ca.mcgill.ecse321.museum.model.MuseumSystem;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;

@SpringBootTest
public class CalendarRepositoryTests {
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    MuseumSystemRepository museumSystemRepository;

    @Autowired
    ScheduleBlockRepository scheduleBlockRepository;

    @AfterEach
    public void clearDatabase() {
        // Clear database rows after each test
        calendarRepository.deleteAll();
        museumSystemRepository.deleteAll();
        scheduleBlockRepository.deleteAll();
    }

    /*
     * Test that a calendar can be created and saved to the database and that it can be retrieved from the database
     */
    @Test
    @Transactional
    public void testPersistAndLoadCalendar() {

        // Create all objects
        Calendar calendar = new Calendar();
        MuseumSystem museum = new MuseumSystem();
        ScheduleBlock scheduleBlock = new ScheduleBlock();

        // Set museum attribute
        String museumName = "Museum";
        museum.setName(museumName);

        // Set schedule block attribute
        ScheduleEvent event = ScheduleEvent.MUSEUM_OPEN;
        scheduleBlock.setEvent(event);

        // Set all calendar attributes
        boolean isMuseumOpen = true;
        calendar.setMuseumOpen(true);
        calendar.setMuseum(museum);
        List<ScheduleBlock> scheduleBlocks = Arrays.asList(scheduleBlock);
        calendar.setScheduleBlocks(scheduleBlocks);
        
        // Save all objects
        museumSystemRepository.save(museum);
        scheduleBlockRepository.save(scheduleBlock);
        calendarRepository.save(calendar);

        Long calendarId = calendar.getId();

        // Read calendar from database
        museum = null;
        scheduleBlock = null;
        calendar = calendarRepository.findById(calendarId).orElse(null);

        // Assert that calendar has correct attributes
        assertNotNull(calendar);
        assertEquals(calendarId, calendar.getId());
        assertEquals(isMuseumOpen, calendar.isMuseumOpen());
        assertNotNull(calendar.getMuseum());
        assertEquals(museumName, calendar.getMuseum().getName());
        assertNotNull(calendar.getScheduleBlocks());
        assertEquals(1, calendar.getScheduleBlocks().size());
        for (ScheduleBlock sb : calendar.getScheduleBlocks()) assertEquals(event, sb.getEvent());
    }
}
