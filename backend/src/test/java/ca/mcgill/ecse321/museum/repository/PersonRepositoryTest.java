/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.model.Visitor;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PersonRepositoryTest {
    @Autowired PersonRepository personRepository;
    @Autowired LoanRepository loanRepository;

    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
        loanRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistAndLoadVisitor() {

        // Create objects
        Visitor visitor = new Visitor();

        // Create attribute
        boolean isActive = true;

        // Create reference (one-to-many)
        List<Loan> loans = new ArrayList<Loan>();
        Loan myLoan = new Loan();
        loanRepository.save(myLoan);

        loans.add(myLoan);

        // Set attribute and reference
        visitor.setActive(isActive);

        // Save object
        visitor = personRepository.save(visitor);
        long id = visitor.getId();

        // Read object from database
        visitor = (Visitor) personRepository.findById(id).orElse(null);
        // Assert that object has correct attributes
        assertNotNull(visitor);
        assertEquals(isActive, visitor.isActive());
    }

    @Test
    @Transactional
    public void testPersistAndLoadEmployee() {

        // Create objects
        Employee employee = new Employee();

        // Create attribute
        float salary = 100;

        // Create reference (one-to-many)
        List<Loan> loans = new ArrayList<Loan>();
        Loan myLoan = new Loan();
        loanRepository.save(myLoan);

        loans.add(myLoan);

        // Set attribute and reference
        employee.setSalary(salary);

        // Save object
        employee = personRepository.save(employee);
        long id = employee.getId();

        // Read object from database
        employee = (Employee) personRepository.findById(id).orElse(null);
        // Assert that object has correct attributes
        assertNotNull(employee);
        assertEquals(salary, employee.getSalary());
    }

    @Test
    @Transactional
    public void testPersistAndLoadOwner() {

        // Create objects
        Owner owner = new Owner();

        // Create attribute
        String email = "bigboss@museum.com";

        // Create reference (one-to-many)
        List<Loan> loans = new ArrayList<Loan>();
        Loan myLoan = new Loan();
        loanRepository.save(myLoan);

        loans.add(myLoan);

        // Set attribute and reference
        owner.setEmail(email);

        // Save object
        owner = personRepository.save(owner);
        long id = owner.getId();

        // Read object from database
        owner = (Owner) personRepository.findById(id).orElse(null);
        // Assert that object has correct attributes
        assertNotNull(owner);
        assertEquals(email, owner.getEmail());
    }
}
