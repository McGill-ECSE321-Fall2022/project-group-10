/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse321.museum.model.*;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import java.sql.Date;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoanRepositoryTests {
    @Autowired LoanRepository loanRepository;
    @Autowired AdministratorRepository administratorRepository;
    @Autowired VisitorRepository visitorRepository;
    @Autowired ArtworkRepository artworkRepository;

    @AfterEach
    public void clearDatabase() {
        loanRepository.deleteAll();
        administratorRepository.deleteAll();
        visitorRepository.deleteAll();
        artworkRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistAndLoadLoan() {

        // Create object
        Loan loan = new Loan();

        // Create attributes
        Date startDate = Date.valueOf("2020-01-01");
        Date endDate = Date.valueOf("2020-01-10");
        float price = 100.0f;
        LoanStatus status = LoanStatus.VALIDATED;

        // Set attributes
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setPrice(price);
        loan.setStatus(status);

        // Set associations
        // Administrator
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Lennon");
        owner.setEmail("john_lennon@thebeatles.com");
        owner.setPassword("lovelovemedo");
        owner = administratorRepository.save(owner);

        loan.setValidator(owner);

        // Artwork
        Artwork artwork = new Artwork();
        artwork.setTitle("Girl with a Pearl Earring");
        artwork.setAuthor("Johannes Vermeer");
        artwork.setDescription("Meisje met de parel in original Dutch");
        artwork.setImageLink("https://test123.com");
        artwork.setPrice(10000);
        artwork.setAvailable(false);
        artwork = artworkRepository.save(artwork);

        loan.setArtwork(artwork);

        // Visitor
        Visitor visitor = new Visitor();
        visitor.setFirstName("Rowan");
        visitor.setLastName("Atkinson");
        visitor.setEmail("mr_bean@ifunny.com");
        visitor.setPassword("plainstringpassword");
        visitor = visitorRepository.save(visitor);

        loan.setCustomer(visitor);

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
        assertEquals(status, loan.getStatus());
        assertEquals(artwork, loan.getArtwork());
        assertEquals(visitor, loan.getCustomer());
        assertEquals(owner, loan.getValidator());
    }
}
