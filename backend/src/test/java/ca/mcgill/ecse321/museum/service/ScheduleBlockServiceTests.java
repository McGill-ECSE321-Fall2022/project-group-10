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
import ca.mcgill.ecse321.museum.model.*;
import ca.mcgill.ecse321.museum.model.ScheduleBlock.ScheduleEvent;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ScheduleBlockRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import java.sql.Date;
import java.util.ArrayList;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ScheduleBlockServiceTests {

    @Mock private ScheduleBlockRepository scheduleBlockRepository;
    @Mock private VisitorRepository visitorRepository;
    @Mock private AdministratorRepository administratorRepository;

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
                            scheduleBlock.setVisitors(new ArrayList<Visitor>());
                            scheduleBlock.setAdmins(new ArrayList<Administrator>());

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

        // ScheduleBlock 4 has visitor and admin
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

                            var visitors = new ArrayList<Visitor>();
                            visitors.add(visitor);
                            scheduleBlock.setVisitors(visitors);

                            var admin = new Employee();
                            admin.setId(1);
                            admin.setFirstName("Emplo");
                            admin.setLastName("Yee");

                            var admins = new ArrayList<Administrator>();
                            admins.add(admin);
                            scheduleBlock.setAdmins(admins);

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 5 does not exist
        lenient()
                .when(scheduleBlockRepository.findById(SCHEDULEBLOCK_KEY + 4))
                .thenReturn(Optional.empty());

        // ScheduleBlock 6 has array lists
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
                            scheduleBlock.setVisitors(new ArrayList<Visitor>());
                            scheduleBlock.setAdmins(new ArrayList<Administrator>());

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 7 has no capacity
        lenient()
                .when(scheduleBlockRepository.findById(7L))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var scheduleBlock = new ScheduleBlock();

                            scheduleBlock.setId(SCHEDULEBLOCK_KEY);
                            scheduleBlock.setStartDate(Date.valueOf("2020-01-01"));
                            scheduleBlock.setEndDate(Date.valueOf("2020-01-02"));
                            scheduleBlock.setVisitFees(10);
                            scheduleBlock.setVisitCapacity(0);
                            scheduleBlock.setEvent(ScheduleEvent.MUSEUM_OPEN);
                            scheduleBlock.setVisitors(new ArrayList<Visitor>());
                            scheduleBlock.setAdmins(new ArrayList<Administrator>());

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 8 has a visitor and employee not used elsewhere
        lenient()
                .when(scheduleBlockRepository.findById(8L))
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
                            visitor.setId(9999);
                            visitor.setFirstName("DO NOT USE ME");
                            visitor.setLastName("DO NOT USE ME");

                            var visitors = new ArrayList<Visitor>();
                            visitors.add(visitor);
                            scheduleBlock.setVisitors(visitors);

                            var admin = new Employee();
                            admin.setId(9999);
                            admin.setFirstName("DO NOT USE ME");
                            admin.setLastName("DO NOT USE ME");

                            var admins = new ArrayList<Administrator>();
                            admins.add(admin);
                            scheduleBlock.setAdmins(admins);

                            return Optional.of(scheduleBlock);
                        });

        // ScheduleBlock 9 has 2 visitors, 2 employees
        lenient()
                .when(scheduleBlockRepository.findById(9L))
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
                            visitor.setId(9999);
                            visitor.setFirstName("DO NOT USE ME");
                            visitor.setLastName("DO NOT USE ME");

                            var visitor2 = new Visitor();
                            visitor2.setId(1);
                            visitor2.setFirstName("Vizi");
                            visitor2.setLastName("Thor");

                            var visitors = new ArrayList<Visitor>();
                            visitors.add(visitor);
                            visitors.add(visitor2);
                            scheduleBlock.setVisitors(visitors);

                            var admin = new Employee();
                            admin.setId(9999);
                            admin.setFirstName("DO NOT USE ME");
                            admin.setLastName("DO NOT USE ME");

                            var admin2 = new Employee();
                            admin2.setId(1);
                            admin2.setFirstName("Emplo");
                            admin2.setLastName("Yee");

                            var admins = new ArrayList<Administrator>();
                            admins.add(admin);
                            admins.add(admin2);
                            scheduleBlock.setAdmins(admins);

                            return Optional.of(scheduleBlock);
                        });

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

        // Visitor 1 exists
        lenient()
                .when(visitorRepository.findById(1L))
                .thenAnswer(
                        invocation -> {
                            var person = new Visitor();
                            person.setId(1);
                            person.setFirstName("Vizi");
                            person.setLastName("Thor");
                            return Optional.of(person);
                        });

        // Visitor 2 does not exist
        lenient().when(visitorRepository.findById(2L)).thenReturn(Optional.empty());

        // Admin 1 is an employee
        lenient()
                .when(administratorRepository.findById(1L))
                .thenAnswer(
                        invocation -> {
                            var person = new Employee();
                            person.setId(1);
                            person.setFirstName("Emplo");
                            person.setLastName("Yee");
                            return Optional.of(person);
                        });

        // Admin 2 does not exist
        lenient().when(administratorRepository.findById(2L)).thenReturn(Optional.empty());

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
        assertEquals(
                new ArrayList<Visitor>(), scheduleBlock.getVisitors()); // Empty list of visitors
        assertEquals(
                new ArrayList<Administrator>(),
                scheduleBlock.getAdmins()); // Empty list of administrators
    }

    @Test
    public void testGetScheduleBlockDoesNotExist() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.getScheduleBlock(5));

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
                                        5, startDate, endDate, event, visitFees, visitCapacity));
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
                        () -> scheduleBlockService.deleteScheduleBlock(5));
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
        assertEquals(1, visitor.getId());
        assertEquals("Vizi", visitor.getFirstName());
        assertEquals("Thor", visitor.getLastName());
    }

    @Test
    public void testGetVisitorsOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.getVisitorsOnScheduleBlock(5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlock() {
        var scheduleBlock = scheduleBlockService.registerVisitorOnScheduleBlock(1, 1);
        var registeredVisitor = scheduleBlock.getVisitors().get(0);
        assertNotNull(registeredVisitor);
        assertEquals(1, registeredVisitor.getId());
        assertEquals("Vizi", registeredVisitor.getFirstName());
        assertEquals("Thor", registeredVisitor.getLastName());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlockWithOtherVisitors() {
        var scheduleBlock = scheduleBlockService.registerVisitorOnScheduleBlock(8, 1);
        var registeredVisitor = scheduleBlock.getVisitors().get(1);
        assertNotNull(registeredVisitor);
        assertEquals(1, registeredVisitor.getId());
        assertEquals("Vizi", registeredVisitor.getFirstName());
        assertEquals("Thor", registeredVisitor.getLastName());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerVisitorOnScheduleBlock(5, 1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlockButVisitorIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerVisitorOnScheduleBlock(1, 2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such visitor", exception.getMessage());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlockButVisitorIsAlreadyRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerVisitorOnScheduleBlock(4, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Visitor already registered to schedule block", exception.getMessage());
    }

    @Test
    public void testRegisterVisitorsOnScheduleBlockButFull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerVisitorOnScheduleBlock(7, 1));
        assertEquals("Schedule block is full", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testRegisterStaffOnScheduleBlock() {
        var scheduleBlock = scheduleBlockService.registerStaffOnScheduleBlock(1, 1);
        var registeredStaff = scheduleBlock.getAdmins().get(0);
        assertNotNull(registeredStaff);
        assertEquals(1, registeredStaff.getId());
        assertEquals("Emplo", registeredStaff.getFirstName());
        assertEquals("Yee", registeredStaff.getLastName());
    }

    @Test
    public void testRegisterStaffOnScheduleBlockWithOtherStaff() {
        var scheduleBlock = scheduleBlockService.registerStaffOnScheduleBlock(8, 1);
        var registeredStaff = scheduleBlock.getAdmins().get(1);
        assertNotNull(registeredStaff);
        assertEquals(1, registeredStaff.getId());
        assertEquals("Emplo", registeredStaff.getFirstName());
        assertEquals("Yee", registeredStaff.getLastName());
    }

    @Test
    public void testRegisterStaffOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerStaffOnScheduleBlock(5, 1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    public void testRegisterStaffOnScheduleBlockButStaffIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerStaffOnScheduleBlock(1, 2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such staff", exception.getMessage());
    }

    @Test
    public void testRegisterStaffOnScheduleBlockButStaffIsAlreadyRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.registerStaffOnScheduleBlock(4, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Staff already registered to schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlock() {
        var scheduleBlock = scheduleBlockService.unregisterVisitorOnScheduleBlock(4, 1);
        assertEquals(new ArrayList<Visitor>(), scheduleBlock.getVisitors());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterVisitorOnScheduleBlock(5, 1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlockButVisitorIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterVisitorOnScheduleBlock(4, 2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such visitor", exception.getMessage());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlockButNoVisitorsAreRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterVisitorOnScheduleBlock(1, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Visitor not registered to schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlockButVisitorIsNotRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterVisitorOnScheduleBlock(8, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Visitor not registered to schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterVisitorOnScheduleBlockContainsTwoVisitors() {
        var scheduleBlock = scheduleBlockService.unregisterVisitorOnScheduleBlock(9, 1);
        var visitor = scheduleBlock.getVisitors().get(0);
        assertEquals(9999, visitor.getId());
        assertEquals("DO NOT USE ME", visitor.getFirstName());
        assertEquals("DO NOT USE ME", visitor.getLastName());
    }

    @Test
    void testUnregisterStaffOnScheduleBlock() {
        var scheduleBlock = scheduleBlockService.unregisterStaffOnScheduleBlock(4, 1);
        assertEquals(new ArrayList<Administrator>(), scheduleBlock.getAdmins());
    }

    @Test
    void testUnregisterStaffOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterStaffOnScheduleBlock(5, 1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterStaffOnScheduleBlockButStaffIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterStaffOnScheduleBlock(4, 2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such staff", exception.getMessage());
    }

    @Test
    void testUnregisterStaffOnScheduleBlockButNoStaffIsRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterStaffOnScheduleBlock(1, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Staff not registered to schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterStaffOnScheduleBlockButStaffIsNotRegistered() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.unregisterStaffOnScheduleBlock(8, 1));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Staff not registered to schedule block", exception.getMessage());
    }

    @Test
    void testUnregisterStaffOnScheduleBlockContainsTwoStaff() {
        var scheduleBlock = scheduleBlockService.unregisterStaffOnScheduleBlock(9, 1);
        var staff = scheduleBlock.getAdmins().get(0);
        assertEquals(9999, staff.getId());
        assertEquals("DO NOT USE ME", staff.getFirstName());
        assertEquals("DO NOT USE ME", staff.getLastName());
    }

    @Test
    void testGetStaffOnScheduleBlock() {
        var staff = scheduleBlockService.getStaffOnScheduleBlock(4);
        var admin = ((List<Administrator>) staff).get(0);
        assertEquals(1, admin.getId());
        assertEquals("Emplo", admin.getFirstName());
        assertEquals("Yee", admin.getLastName());
    }

    @Test
    void testGetStaffOnScheduleBlockButScheduleBlockIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> scheduleBlockService.getStaffOnScheduleBlock(5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such schedule block", exception.getMessage());
    }
}
