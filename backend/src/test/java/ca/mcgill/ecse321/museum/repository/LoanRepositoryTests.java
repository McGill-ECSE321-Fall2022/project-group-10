package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.museum.model.Loan;

@SpringBootTest
public class LoanRepositoryTests {
    @Autowired LoanRepository loanRepository;

    @AfterEach
    public void clearDatabase() {
        loanRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadLoan() {

        // Create object
        Loan loan = new Loan();

        // Create attributes
        Date startDate = Date.valueOf("2020-01-01");
        Date endDate = Date.valueOf("2020-01-10");
        float price = 100.0f;
        boolean isValidated = true;

        // Set attributes
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setPrice(price);
        loan.setValidated(isValidated);

        // Save object
        loan = loanRepository.save(loan);
        long id = loan.getId();

        // Read object from database
        loan = loanRepository.findById(id).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(loan);
        assertEquals(startDate, loan.getStartDate());
        assertEquals(endDate, loan.getEndDate());
        assertEquals(price, loan.getPrice());
        assertEquals(isValidated, loan.isValidated());
    }

}
