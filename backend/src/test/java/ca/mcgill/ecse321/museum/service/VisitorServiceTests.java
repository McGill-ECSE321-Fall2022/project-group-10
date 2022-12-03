/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import ca.mcgill.ecse321.museum.security.CredentialsEncoder;
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
public class VisitorServiceTests {
    @Mock private VisitorRepository visitorRepository;

    @InjectMocks private VisitorService visitorService;

    private static final Long VISITOR_KEY = Long.valueOf(0);
    private static final Long VISITOR2_KEY = Long.valueOf(1);
    private static final Long NEW_KEY = Long.valueOf(2);

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(visitorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(VISITOR_KEY)) {
                                Visitor visitor = new Visitor();
                                visitor.setId(VISITOR_KEY);
                                visitor.setEmail("first@email.com");
                                return Optional.of(visitor);
                            } else if (invocation.getArgument(0).equals(VISITOR2_KEY)) {
                                Visitor visitor = new Visitor();
                                visitor.setId(VISITOR2_KEY);
                                visitor.setEmail("second@email.com");
                                return Optional.of(visitor);
                            } else {
                                return Optional.empty();
                            }
                        });

        lenient()
                .when(visitorRepository.findByEmail(anyString()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            List<Visitor> visitors = new ArrayList<Visitor>();
                            if (invocation.getArgument(0).equals("first@email.com")) {
                                Visitor visitor = new Visitor();
                                visitor.setId(VISITOR_KEY);
                                visitor.setEmail("first@email.com");
                                visitors.add(visitor);
                            } else if (invocation.getArgument(0).equals("second@email.com")) {
                                Visitor visitor = new Visitor();
                                visitor.setId(VISITOR2_KEY);
                                visitor.setEmail("second@email.com");
                                visitors.add(visitor);
                            }
                            return visitors;
                        });

        lenient()
                .when(visitorRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Visitor visitor = new Visitor();
                            visitor.setId(VISITOR_KEY);
                            return List.of(visitor);
                        });

        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        lenient()
                .when(visitorRepository.save(any(Visitor.class)))
                .thenAnswer(returnParameterAsAnswer);
    }

    /** Test CreateVisitor with complete information */
    @Test
    public void testCreateVisitorComplete() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "tester1@email.com";
        String password = "password123";
        boolean isActive = true;
        Visitor visitor = visitorService.createVisitor(firstName, lastName, email, password);
        assertNotNull(visitor);
        assertEquals(firstName, visitor.getFirstName());
        assertEquals(lastName, visitor.getLastName());
        assertEquals(email, visitor.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, visitor.getPassword()));
        assertEquals(isActive, visitor.isActive());
    }

    /** Test CreateVisitor with the email of another visitor */
    @Test
    public void testCreateVisitorFailDupEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@email.com"; // Email of imaginary already existing visitor
            String password = "password123";
            visitorService.createVisitor(firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test CreateVisitor with invalid email */
    @Test
    public void testCreateVisitorInvalidEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@mail.museum.com"; // Invalid email
            String password = "password123";
            visitorService.createVisitor(firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditVisitor with complete information */
    @Test
    public void testEditVisitorComplete() {
        Long id = VISITOR_KEY;
        String firstName = "John";
        String lastName = "Doe";
        String email = "tester1@email.com";
        String password = "password123";
        Visitor visitor = visitorService.editVisitor(id, firstName, lastName, email, password);
        assertNotNull(visitor);
        assertEquals(firstName, visitor.getFirstName());
        assertEquals(lastName, visitor.getLastName());
        assertEquals(email, visitor.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, visitor.getPassword()));
    }

    /** Test EditVisitor with different email */
    @Test
    public void testEditVisitorEmailChange() {
        Long id = VISITOR_KEY;
        String firstName = "John";
        String lastName = "Doe";
        String email = "newemail@email.com";
        String password = "password123";
        Visitor visitor = visitorService.editVisitor(id, firstName, lastName, email, password);
        assertNotNull(visitor);
        assertEquals(firstName, visitor.getFirstName());
        assertEquals(lastName, visitor.getLastName());
        assertEquals(email, visitor.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, visitor.getPassword()));
    }

    /** Test EditVisitor with the email of another visitor */
    @Test
    public void testEditVisitorFailDupEmail() {
        try {
            Long id = VISITOR_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@email.com"; // Email of imaginary already existing visitor
            String password = "password123";
            visitorService.editVisitor(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditVisitor with invalid email */
    @Test
    public void testEditVisitorInvalidEmail() {
        try {
            Long id = VISITOR_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@mail.museum.com"; // Invalid email
            String password = "password123";
            visitorService.editVisitor(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditVisitor with non-existant id */
    @Test
    public void testEditVisitorFailNonExistant() {
        try {
            Long id = NEW_KEY; // New key (does not exist)
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@email.com";
            String password = "password123";
            visitorService.editVisitor(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetVisitor with valid id */
    @Test
    public void testGetVisitorValid() {
        Visitor visitor = visitorService.getVisitor(VISITOR_KEY);
        assertNotNull(visitor);
        assertEquals(VISITOR_KEY, visitor.getId());
    }

    /** Test GetVisitor with invalid id */
    @Test
    public void testGetVisitorInvalid() {
        try {
            visitorService.getVisitor(NEW_KEY);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetAllVisitors */
    @Test
    public void testGetAllVisitorsComplete() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        assertNotNull(visitors);
        assertNotNull(visitors.get(0));
        assertEquals(VISITOR_KEY, visitors.get(0).getId());
        assertEquals(1, visitors.size());
    }

    /** Test Deactivate Visitor with valid id */
    @Test
    public void testDeactivateVisitorValid() {
        visitorService.deactivateVisitor(VISITOR_KEY);
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    /** Test Deactivate Visitor with invalid id */
    @Test
    public void testDeactivateVisitorInvalid() {
        try {
            visitorService.deactivateVisitor(NEW_KEY);
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test Deactivate Visitor with valid id */
    @Test
    public void testReactivateVisitorValid() {
        visitorService.reactivateVisitor(VISITOR_KEY);
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    /** Test Deactivate Visitor with invalid id */
    @Test
    public void testActivateVisitorInvalid() {
        try {
            visitorService.reactivateVisitor(NEW_KEY);
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}
