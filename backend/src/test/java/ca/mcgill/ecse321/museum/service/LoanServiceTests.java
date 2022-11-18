package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.StorageRoomRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;

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

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private ArtworkRepository artworkRepository;
    @Mock
    private LoanRepository loanRepository;    

    @InjectMocks
    private LoanService loanService;

    private static final Long LOAN_KEY = Long.valueOf(0);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(loanRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(LOAN_KEY)) {
                Loan loan = new Loan();
                loan.setId(LOAN_KEY);
                return Optional.of(loan);
            } else {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
            }
        });

        lenient().when(loanRepository.findAll()).thenAnswer ( (InvocationOnMock invocation) -> {
            Loan loan = new Loan();
            loan.setId(LOAN_KEY);
            return List.of(loan);
        });
        
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(loanRepository.save(any(Loan.class))).thenAnswer(returnParameterAsAnswer);
        
    }

    @Test
    public void testCreateLoanComplete() {

        // Mock Artwork
        lenient().when(artworkRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) ->{
            Artwork artwork = new Artwork();
            artwork.setId(Long.valueOf(1));
            return Optional.of(artwork);
        });

        // Mock Visitor
        lenient().when(personRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) ->{
            ca.mcgill.ecse321.museum.model.Visitor visitor = new ca.mcgill.ecse321.museum.model.Visitor();
            visitor.setId(Long.valueOf(1));
            return Optional.of(visitor);
        });

        float price = 100f;
        Date startDate = new Date(0);
        Date endDate = new Date(1000);
        LoanStatus status = LoanStatus.INCART;
        Long artworkId = Long.valueOf(1);
        Long visitorId = Long.valueOf(1);
        Long administratorId = Long.valueOf(1);

        Loan loan = loanService.createLoan(price, startDate, endDate, artworkId, administratorId);

        assertNotNull(loan);
        assertEquals(price, loan.getPrice());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(endDate, loan.getEndDate());
        assertEquals(status, loan.getStatus());
        assertEquals(artworkId, loan.getArtwork().getId());
        assertEquals(visitorId, loan.getCustomer().getId());
    }

    @Test public void testGetLoan() {
        Loan loan = loanService.getLoan(LOAN_KEY);
        assertNotNull(loan);
        assertEquals(LOAN_KEY, loan.getId());
    }

    @Test public void testGetArtworkFail() {
        try {
            loanService.getLoan(2L);
            fail();
        }
        catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test public void testGetAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        assertNotNull(loans);
        assertNotNull(loans.get(0));
        assertEquals(LOAN_KEY, loans.get(0).getId());
        assertEquals(1, loans.size());
    }

    @Test public void requestLoan() {
        // Mock Artwork
        lenient().when(artworkRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) ->{
            Artwork artwork = new Artwork();
            artwork.setId(Long.valueOf(1));
            return Optional.of(artwork);
        });

        // Mock Visitor
        lenient().when(personRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) ->{
            ca.mcgill.ecse321.museum.model.Visitor visitor = new ca.mcgill.ecse321.museum.model.Visitor();
            visitor.setId(Long.valueOf(1));
            return Optional.of(visitor);
        });

        float price = 100f;
        Date startDate = new Date(0);
        Date endDate = new Date(1000);
        Long artworkId = Long.valueOf(1);
        Long visitorId = Long.valueOf(1);

        Loan loan = loanService.createLoan(price, startDate, endDate, artworkId, visitorId);
        assertEquals(LOAN_KEY, loan.getId());
        loan = loanService.requestLoan(LOAN_KEY);

        assertNotNull(loan);
        assertEquals(LoanStatus.PENDING, loan.getStatus());
    }

    @Test public void testDeleteLoan() {
        loanService.deleteLoan(LOAN_KEY);
        verify(loanRepository, times(1)).deleteById(LOAN_KEY);
    }
}
