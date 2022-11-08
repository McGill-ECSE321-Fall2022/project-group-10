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
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;

@SpringBootTest
public class CalendarRepositoryTests {
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    ScheduleBlockRepository scheduleBlockRepository;

    @AfterEach
    public void clearDatabase() {
        // Clear database rows after each test
        calendarRepository.deleteAll();
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
        ScheduleBlock scheduleBlock = new ScheduleBlock();

        // Set schedule block attribute
        ScheduleEvent event = ScheduleEvent.MUSEUM_OPEN;
        scheduleBlock.setEvent(event);

        // Set all calendar attributes
        boolean isMuseumOpen = true;
        calendar.setMuseumOpen(true);
        List<ScheduleBlock> scheduleBlocks = List.of(scheduleBlock);
        calendar.setScheduleBlocks(scheduleBlocks);

        // Save all objects
        scheduleBlockRepository.save(scheduleBlock);
        calendarRepository.save(calendar);

        Long calendarId = calendar.getId();
        Long scheduleBlockId = scheduleBlock.getId();

        // Read calendar from database
        calendar = calendarRepository.findById(calendarId).orElse(null);

        // Assert that calendar has correct attributes
        assertNotNull(calendar);
        assertEquals(calendarId, calendar.getId());
        assertEquals(isMuseumOpen, calendar.isMuseumOpen());
        // Test schedule block association
        assertNotNull(calendar.getScheduleBlocks());
        assertEquals(1, calendar.getScheduleBlocks().size());
        assertEquals(scheduleBlockId, calendar.getScheduleBlocks().get(0).getId());
        assertEquals(event, calendar.getScheduleBlocks().get(0).getEvent());
    }
}
