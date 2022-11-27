/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.ScheduleBlockRepository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.swing.text.html.Option;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ScheduleBlockServiceTests {

    @Mock private ScheduleBlockRepository scheduleBlockRepository;

    @InjectMocks private ScheduleBlockService scheduleBlockService;

    private static final Long SCHEDULEBLOCK_KEY = Long.valueOf(1);
    private static final Long ADMIN_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {

        // ScheduleBlock 1 exists
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock = new ScheduleBlock();

                            scheduleBlock.setId(SCHEDULEBLOCK_KEY);
                            scheduleBlock.setStartDate(Date.valueOf("2020-01-01"));
                            scheduleBlock.setEndDate(Date.valueOf("2020-01-02"));
                            scheduleBlock.setVisitFees(10);
                            scheduleBlock.setVisitCapacity(100);
                            scheduleBlock.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 2 exists
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY + 1))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock = new ScheduleBlock();

                            scheduleBlock.setId(SCHEDULEBLOCK_KEY);
                            scheduleBlock.setStartDate(Date.valueOf("2020-02-01"));
                            scheduleBlock.setEndDate(Date.valueOf("2020-02-02"));
                            scheduleBlock.setVisitFees(8);
                            scheduleBlock.setVisitCapacity(120);
                            scheduleBlock.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 3 exists
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY + 2))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock = new ScheduleBlock();

                            scheduleBlock.setId(SCHEDULEBLOCK_KEY);
                            scheduleBlock.setStartDate(Date.valueOf("2020-02-05"));
                            scheduleBlock.setEndDate(Date.valueOf("2020-02-08"));
                            scheduleBlock.setVisitFees(0);
                            scheduleBlock.setVisitCapacity(0);
                            scheduleBlock.setEvent(ScheduleEvent.MUSEUM_RESTORATION);

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 4 has visitors
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY + 3))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock = new ScheduleBlock();

                            scheduleBlock.setId(SCHEDULEBLOCK_KEY);
                            scheduleBlock.setStartDate(Date.valueOf("2020-01-01"));
                            scheduleBlock.setEndDate(Date.valueOf("2020-01-02"));
                            scheduleBlock.setVisitFees(10);
                            scheduleBlock.setVisitCapacity(100);
                            scheduleBlock.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            var visitor = new Visitor();
                            visitor.setId(1);
                            visitor.setFirstName("Vizi");
                            visitor.setLastName("Thor");

                            scheduleBlock.setVisitors(List.of(visitor));

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 5 does not exist
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY + 4))
                .thenReturn(Optional.empty());

        // List of all schedule blocks is a list containing ScheduleBlock 1
        lenient()
                .when(scheduleBlockRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock1 = new ScheduleBlock();
                            scheduleBlock1.setId(SCHEDULEBLOCK_KEY);

                            scheduleBlock1.setStartDate(Date.valueOf("2020-01-01"));
                            scheduleBlock1.setEndDate(Date.valueOf("2020-01-02"));
                            scheduleBlock1.setVisitFees(10);
                            scheduleBlock1.setVisitCapacity(100);
                            scheduleBlock1.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            var scheduleBlock2 = new ScheduleBlock();
                            scheduleBlock2.setId(SCHEDULEBLOCK_KEY + 1);

                            scheduleBlock2.setStartDate(Date.valueOf("2020-02-01"));
                            scheduleBlock2.setEndDate(Date.valueOf("2020-02-02"));
                            scheduleBlock2.setVisitFees(8);
                            scheduleBlock2.setVisitCapacity(120);
                            scheduleBlock2.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            var scheduleBlock3 = new ScheduleBlock();
                            scheduleBlock3.setId(SCHEDULEBLOCK_KEY + 2);
                            scheduleBlock3.setStartDate(Date.valueOf("2020-02-05"));
                            scheduleBlock3.setEndDate(Date.valueOf("2020-02-08"));
                            scheduleBlock3.setVisitFees(0);
                            scheduleBlock3.setVisitCapacity(0);
                            scheduleBlock3.setEvent(ScheduleEvent.MUSEUM_RESTORATION);

                            return List.of(scheduleBlock1, scheduleBlock2, scheduleBlock3);
                        });

        // List of all schedule blocks is a list between two dates
        lenient()
                .when(
                        scheduleBlockRepository
                                .findScheduleBlockByStartDateGreaterThanEqualAndEndDateLessThanEqual(
                                        Date.valueOf("2020-01-01"), Date.valueOf("2020-02-02")))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock1 = new ScheduleBlock();
                            scheduleBlock1.setId(SCHEDULEBLOCK_KEY);

                            scheduleBlock1.setStartDate(Date.valueOf("2020-01-01"));
                            scheduleBlock1.setEndDate(Date.valueOf("2020-01-02"));
                            scheduleBlock1.setVisitFees(10);
                            scheduleBlock1.setVisitCapacity(100);
                            scheduleBlock1.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            var scheduleBlock2 = new ScheduleBlock();
                            scheduleBlock2.setId(SCHEDULEBLOCK_KEY + 1);

                            scheduleBlock2.setStartDate(Date.valueOf("2020-02-01"));
                            scheduleBlock2.setEndDate(Date.valueOf("2020-02-02"));
                            scheduleBlock2.setVisitFees(8);
                            scheduleBlock2.setVisitCapacity(120);
                            scheduleBlock2.setEvent(ScheduleEvent.MUSEUM_OPEN);

                            return List.of(scheduleBlock1, scheduleBlock2);
                        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        // Saving an ScheduleBlock just returns it
        lenient()
                .when(scheduleBlockRepository.save(any(ScheduleBlock.class)))
                .thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateScheduleBlock() {
        Date startDate = Date.valueOf("2020-01-01");
        Date endDate = Date.valueOf("2020-01-02");
        ScheduleEvent event = ScheduleEvent.MUSEUM_OPEN;
        float visitFees = 10.0f;
        int visitCapacity = 100;

        var scheduleBlock =
                scheduleBlockService.createScheduleBlock(
                        startDate, endDate, event, visitFees, visitCapacity);

        assertNotNull(scheduleBlock);
        assertEquals(startDate, scheduleBlock.getStartDate());
        assertEquals(endDate, scheduleBlock.getEndDate());
        assertEquals(event, scheduleBlock.getEvent());
        assertEquals(visitFees, scheduleBlock.getVisitFees());
        assertEquals(visitCapacity, scheduleBlock.getVisitCapacity());
        assertEquals(null, scheduleBlock.getVisitors()); // Empty list of visitors
        assertEquals(null, scheduleBlock.getAdmins()); // Empty list of administrators
    }

    @Test
    public void testGetScheduleBlock() {
        var scheduleBlock = scheduleBlockService.getScheduleBlock(SCHEDULEBLOCK_KEY);

        assertNotNull(scheduleBlock);
        assertEquals(SCHEDULEBLOCK_KEY, scheduleBlock.getId());
        assertEquals(Date.valueOf("2020-01-01"), scheduleBlock.getStartDate());
        assertEquals(Date.valueOf("2020-01-02"), scheduleBlock.getEndDate());
        assertEquals(ScheduleEvent.MUSEUM_OPEN, scheduleBlock.getEvent());
        assertEquals(10, scheduleBlock.getVisitFees());
        assertEquals(100, scheduleBlock.getVisitCapacity());
        assertEquals(null, scheduleBlock.getVisitors()); // Empty list of visitors
        assertEquals(null, scheduleBlock.getAdmins()); // Empty list of administrators
    }

    @Test
    public void testGetScheduleBlockDoesNotExist() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.getScheduleBlock(SCHEDULEBLOCK_KEY + 3));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testUpdateScheduleBlock() {
        Date startDate = Date.valueOf("2020-01-05");
        Date endDate = Date.valueOf("2020-01-06");
        ScheduleEvent event = ScheduleEvent.MUSEUM_MEETING;
        float visitFees = 8.0f;
        int visitCapacity = 120;

        var scheduleBlock =
                scheduleBlockService.updateScheduleBlock(
                        SCHEDULEBLOCK_KEY, startDate, endDate, event, visitFees, visitCapacity);

        assertNotNull(scheduleBlock);
        assertEquals(SCHEDULEBLOCK_KEY, scheduleBlock.getId());
        assertEquals(startDate, scheduleBlock.getStartDate());
        assertEquals(endDate, scheduleBlock.getEndDate());
        assertEquals(event, scheduleBlock.getEvent());
        assertEquals(visitFees, scheduleBlock.getVisitFees());
        assertEquals(visitCapacity, scheduleBlock.getVisitCapacity());
    }

    @Test
    public void testUpdateScheduleBlockDoesNotExist() {

        Date startDate = Date.valueOf("2020-01-05");
        Date endDate = Date.valueOf("2020-01-06");
        ScheduleEvent event = ScheduleEvent.MUSEUM_MEETING;
        float visitFees = 8.0f;
        int visitCapacity = 120;

        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () ->
                                scheduleBlockService.updateScheduleBlock(
                                        SCHEDULEBLOCK_KEY + 3,
                                        startDate,
                                        endDate,
                                        event,
                                        visitFees,
                                        visitCapacity));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testDeleteScheduleBlock() {
        scheduleBlockService.deleteScheduleBlock(SCHEDULEBLOCK_KEY);
        verify(scheduleBlockRepository, times(1)).delete(any(ScheduleBlock.class));
    }

    @Test
    public void testDeleteScheduleBlockDoesNotExist() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.deleteScheduleBlock(SCHEDULEBLOCK_KEY + 3));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testGetAllScheduleBlocks() {
        var scheduleBlocks = scheduleBlockService.getAllScheduleBlocks();

        assertNotNull(scheduleBlocks);
        int count = 0;
        for (ScheduleBlock scheduleBlock : scheduleBlocks) count++;
        assertEquals(3, count);
    }

    @Test
    public void testGetScheduleBetweenDates() {
        Date startDate = Date.valueOf("2020-01-01");
        Date endDate = Date.valueOf("2020-02-02");

        var scheduleBlocks = scheduleBlockService.getScheduleBlocksBetweenDates(startDate, endDate);

        assertNotNull(scheduleBlocks);
        int count = 0;
        for (ScheduleBlock scheduleBlock : scheduleBlocks) count++;
        assertEquals(2, count);
    }

    @Test
    public void testGetVisitorsOnScheduleBlock() {
        var visitor = ((List<Visitor>) scheduleBlockService.getVisitorsOnScheduleBlock(4)).get(0);
        assertEquals(1,visitor.getId());
        assertEquals("Vizi",visitor.getFirstName());
        assertEquals("Thor",visitor.getLastName());
    }

    @Test
    public void testGetVisitorsOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class, () -> scheduleBlockService.getVisitorsOnScheduleBlock(5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }
}
