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
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
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
public class AdministratorServiceTests {
    @Mock private AdministratorRepository administratorRepository;

    @InjectMocks private AdministratorService administratorService;

    private static final Long EMPLOYEE_KEY = Long.valueOf(0);
    private static final Long OWNER_KEY = Long.valueOf(1);
    private static final Long NEW_KEY = Long.valueOf(2);

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(administratorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(EMPLOYEE_KEY)) {
                                Employee employee = new Employee();
                                employee.setId(EMPLOYEE_KEY);
                                employee.setEmail("first@mail.museum.com");
                                employee.setActive(true);
                                return Optional.of(employee);
                            } else if (invocation.getArgument(0).equals(OWNER_KEY)) {
                                Owner owner = new Owner();
                                owner.setId(OWNER_KEY);
                                owner.setEmail("second@mail.museum.com");
                                return Optional.of(owner);
                            } else {
                                return Optional.empty();
                            }
                        });

        lenient()
                .when(administratorRepository.findByEmail(anyString()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            List<Administrator> administrators = new ArrayList<Administrator>();
                            if (invocation.getArgument(0).equals("first@mail.museum.com")) {
                                Employee employee = new Employee();
                                employee.setId(EMPLOYEE_KEY);
                                employee.setEmail("first@mail.museum.com");
                                administrators.add(employee);
                            } else if (invocation.getArgument(0).equals("second@mail.museum.com")) {
                                Owner owner = new Owner();
                                owner.setId(OWNER_KEY);
                                owner.setEmail("second@mail.museum.com");
                                administrators.add(owner);
                            }
                            return administrators;
                        });

        lenient()
                .when(administratorRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Employee employee = new Employee();
                            employee.setId(EMPLOYEE_KEY);
                            Owner owner = new Owner();
                            owner.setId(OWNER_KEY);
                            return List.of(employee, owner);
                        });

        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        lenient()
                .when(administratorRepository.save(any(Administrator.class)))
                .thenAnswer(returnParameterAsAnswer);
    }

    /** Test CreateEmployee with complete information */
    @Test
    public void testCreateEmployeeComplete() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "tester1@mail.museum.com";
        String password = "password123";
        float salary = 99999;
        boolean isActive = true;
        Employee employee =
                administratorService.createEmployee(firstName, lastName, email, password, salary);
        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(email, employee.getEmail());

        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, employee.getPassword()));

        assertEquals(salary, employee.getSalary());
        assertEquals(isActive, employee.isActive());
    }

    /** Test CreateEmployee with duplicate email information */
    @Test
    public void testCreateEmployeeDupEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@mail.museum.com"; // Email of owner (OWNER_KEY)
            String password = "password123";
            float salary = 99999;
            administratorService.createEmployee(firstName, lastName, email, password, salary);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test CreateEmployee with invalid email */
    @Test
    public void testCreateEmployeeInvalidEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@email.com"; // Invalid email
            String password = "password123";
            float salary = 99999;
            administratorService.createEmployee(firstName, lastName, email, password, salary);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditEmployee with complete info */
    @Test
    public void testEditEmployeeComplete() {
        Long id = EMPLOYEE_KEY;
        String firstName = "Johnathan";
        String lastName = "Doe";
        String email = "first@mail.museum.com";
        String password = "password123";
        float salary = 99999;
        boolean isActive = true;
        Employee employee = administratorService.editEmployee(id, firstName, lastName, email, password, salary);
        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(email, employee.getEmail());

        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, employee.getPassword()));

        assertEquals(salary, employee.getSalary());
        assertEquals(isActive, employee.isActive());
    }

    /** Test EditEmployee with email change */
    @Test
    public void testEditEmployeeEmailChange() {
        Long id = EMPLOYEE_KEY;
        String firstName = "Johnathan";
        String lastName = "Doe";
        String email = "newemail@mail.museum.com";
        String password = "password123";
        float salary = 99999;
        boolean isActive = true;
        Employee employee = administratorService.editEmployee(id, firstName, lastName, email, password, salary);
        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(email, employee.getEmail());

        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, employee.getPassword()));

        assertEquals(salary, employee.getSalary());
        assertEquals(isActive, employee.isActive());
    }

    /** Test EditEmployee with the email of another administrator */
    @Test
    public void testEditEmployeeFailDupEmail() {
        try {
            Long id = EMPLOYEE_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@mail.museum.com"; // Email of owner (OWNER_KEY)
            String password = "password123";
            float salary = 99999;
            administratorService.editEmployee(id, firstName, lastName, email, password, salary);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditEmployee with the invalid email */
    @Test
    public void testEditEmployeeInvalidEmail() {
        try {
            Long id = EMPLOYEE_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "second@email.com"; // Invalid email
            String password = "password123";
            float salary = 99999;
            administratorService.editEmployee(id, firstName, lastName, email, password, salary);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditEmployee with non-existant id */
    @Test
    public void testEditEmployeeFailNonExistant() {
        try {
            Long id = NEW_KEY; // New key (does not exist)
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@mail.museum.com";
            String password = "password123";
            float salary = 99999;
            administratorService.editEmployee(id, firstName, lastName, email, password, salary);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetEmployee with valid id */
    @Test
    public void testGetEmployeeValid() {
        Employee employee = administratorService.getEmployee(EMPLOYEE_KEY);
        assertNotNull(employee);
        assertEquals(EMPLOYEE_KEY, employee.getId());
    }

    /** Test GetEmployee with invalid id */
    @Test
    public void testGetEmployeeInvalid() {
        try {
            administratorService.getEmployee(NEW_KEY);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetAllEmployees */
    @Test
    public void testGetAllEmployeesComplete() {
        List<Employee> employees = administratorService.getAllEmployees();
        assertNotNull(employees);
        assertNotNull(employees.get(0));
        assertEquals(EMPLOYEE_KEY, employees.get(0).getId());
        assertEquals(1, employees.size());
    }

    /** Test Deactivate Employee with valid id */
    @Test
    public void testDeactivateEmployeeValid() {
        administratorService.deactivateEmployee(EMPLOYEE_KEY);
        verify(administratorRepository, times(1)).save(any(Employee.class));
    }

    /** Test Deactivate Employee with invalid id */
    @Test
    public void testDeactivateEmployeeInvalid() {
        try {
            administratorService.deactivateEmployee(NEW_KEY);
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test Deactivate Employee with valid id */
    @Test
    public void testReactivateEmployeeValid() {
        administratorService.reactivateEmployee(EMPLOYEE_KEY);
        verify(administratorRepository, times(1)).save(any(Employee.class));
    }

    /** Test Deactivate Employee with invalid id */
    @Test
    public void testActivateEmployeeInvalid() {
        try {
            administratorService.reactivateEmployee(NEW_KEY);
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Start of the owner tests */

    /** Test CreateOwner with complete information */
    @Test
    public void testCreateOwnerComplete() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "tester2@mail.museum.com";
        String password = "password123";
        Owner owner = administratorService.createOwner(firstName, lastName, email, password);
        assertNotNull(owner);
        assertEquals(firstName, owner.getFirstName());
        assertEquals(lastName, owner.getLastName());
        assertEquals(email, owner.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, owner.getPassword()));
    }

    /** Test CreateOwner with the email of another administrator */
    @Test
    public void testCreateOwnerFailDupEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@mail.museum.com"; // Email of owner (EMPLOYEE_KEY)
            String password = "password123";
            administratorService.createOwner(firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test CreateOwner with invalid email */
    @Test
    public void testCreateOwnerInvalidEmail() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@email.com"; // Invalid email
            String password = "password123";
            administratorService.createOwner(firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditOwner with complete info */
    @Test
    public void testEditOwnerComplete() {
        Long id = OWNER_KEY;
        String firstName = "Johnathan";
        String lastName = "Doe";
        String email = "second@mail.museum.com";
        String password = "password123";
        Owner owner = administratorService.editOwner(id, firstName, lastName, email, password);
        assertNotNull(owner);
        assertEquals(firstName, owner.getFirstName());
        assertEquals(lastName, owner.getLastName());
        assertEquals(email, owner.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, owner.getPassword()));
    }

    /** Test EditOwner with email change info */
    @Test
    public void testEditOwnerEmailChange() {
        Long id = OWNER_KEY;
        String firstName = "Johnathan";
        String lastName = "Doe";
        String email = "newemail@mail.museum.com";
        String password = "password123";
        Owner owner = administratorService.editOwner(id, firstName, lastName, email, password);
        assertNotNull(owner);
        assertEquals(firstName, owner.getFirstName());
        assertEquals(lastName, owner.getLastName());
        assertEquals(email, owner.getEmail());
        CredentialsEncoder credentialsEncoder = new CredentialsEncoder();
        assertTrue(credentialsEncoder.matches(password, owner.getPassword()));
    }

    /** Test EditOwner with the email of another administrator */
    @Test
    public void testEditOwnerFailDupEmail() {
        try {
            Long id = OWNER_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@mail.museum.com"; // Email of employee (EMPLOYEE_KEY)
            String password = "password123";
            administratorService.editOwner(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditOwner with invalid email */
    @Test
    public void testEditOwnerInvalidEmail() {
        try {
            Long id = OWNER_KEY;
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@email.com"; // Invalid email
            String password = "password123";
            administratorService.editOwner(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    /** Test EditOwnerwith non-existant id */
    @Test
    public void testEditOwnerFailNonExistant() {
        try {
            Long id = NEW_KEY; // New key (does not exist)
            String firstName = "John";
            String lastName = "Doe";
            String email = "first@mail.museum.com";
            String password = "password123";
            administratorService.editOwner(id, firstName, lastName, email, password);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetOwner with valid id */
    @Test
    public void testGetOwnerValid() {
        Owner owner = administratorService.getOwner(OWNER_KEY);
        assertNotNull(owner);
        assertEquals(OWNER_KEY, owner.getId());
    }

    /** Test GetOwner with invalid id */
    @Test
    public void testGetOwnerInvalid() {
        try {
            administratorService.getOwner(NEW_KEY);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    /** Test GetAllOwners */
    @Test
    public void testGetAllOwnersComplete() {
        List<Owner> owners = administratorService.getAllOwners();
        assertNotNull(owners);
        assertNotNull(owners.get(0));
        assertEquals(OWNER_KEY, owners.get(0).getId());
        assertEquals(1, owners.size());
    }
}
