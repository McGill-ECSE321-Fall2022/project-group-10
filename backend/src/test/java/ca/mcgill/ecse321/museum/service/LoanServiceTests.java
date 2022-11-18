/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {

    @Mock private PersonRepository personRepository;
    @Mock private AdministratorRepository administratorRepository;
    @Mock private ArtworkRepository artworkRepository;
    @Mock private LoanRepository loanRepository;

    @InjectMocks private LoanService loanService;

    private static final Long LOAN_KEY_EMPTY = Long.valueOf(0);
    private static final Long LOAN_KEY_COMPLETE = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(loanRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(LOAN_KEY_EMPTY)) {
                                Loan loan = new Loan();
                                loan.setId(LOAN_KEY_EMPTY);
                                return Optional.of(loan);
                            } else if (invocation.getArgument(0).equals(LOAN_KEY_COMPLETE)) {
                                Loan loan = new Loan();
                                loan.setId(LOAN_KEY_COMPLETE);
                                loan.setStatus(LoanStatus.INCART);
                                Artwork artwork = new Artwork();
                                artwork.setId(Long.valueOf(1));
                                artwork.setAvailable(true);
                                loan.setArtwork(artwork);
                                return Optional.of(loan);
                            } else {
                                return Optional.empty();
                            }
                        });

        lenient()
                .when(loanRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Loan loan = new Loan();
                            loan.setId(LOAN_KEY_EMPTY);
                            return List.of(loan);
                        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        Answer<?> returnNull =
                (InvocationOnMock invocation) -> {
                    return null;
                };

        lenient().when(loanRepository.save(any(Loan.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateLoanComplete() {

        // Mock Artwork
        lenient()
                .when(artworkRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(Long.valueOf(1));
                            return Optional.of(artwork);
                        });

        // Mock Visitor
        lenient()
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Visitor visitor =
                                    new ca.mcgill.ecse321.museum.model.Visitor();
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

    @Test
    public void testGetLoan() {
        Loan loan = loanService.getLoan(LOAN_KEY_EMPTY);
        assertNotNull(loan);
        assertEquals(LOAN_KEY_EMPTY, loan.getId());
    }

    @Test
    public void testGetArtworkFail() {
        try {
            loanService.getLoan(2L);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    public void testGetAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        assertNotNull(loans);
        assertNotNull(loans.get(0));
        assertEquals(LOAN_KEY_EMPTY, loans.get(0).getId());
        assertEquals(1, loans.size());
    }

    // request loan success
    @Test
    public void requestLoan() {
        Loan loan = loanService.requestLoan(LOAN_KEY_COMPLETE);

        assertNotNull(loan);
        assertEquals(LoanStatus.PENDING, loan.getStatus());
    }

    // request loan fail
    @Test
    public void requestLoanFail() {
        try {
            loanService.requestLoan(LOAN_KEY_EMPTY);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    // validate loan success
    @Test
    public void validateLoan() {
        // Mock Artwork
        lenient()
                .when(artworkRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(Long.valueOf(1));
                            return Optional.of(artwork);
                        });

        // Mock Visitor
        lenient()
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Visitor visitor =
                                    new ca.mcgill.ecse321.museum.model.Visitor();
                            visitor.setId(Long.valueOf(1));
                            return Optional.of(visitor);
                        });

        // Mock Employee
        lenient()
                .when(administratorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Employee employee = new Employee();
                            employee.setId(Long.valueOf(1));
                            return Optional.of(employee);
                        });

        Loan loan = loanService.validateLoan(LOAN_KEY_COMPLETE, 1L);

        assertNotNull(loan);
        assertEquals(LoanStatus.VALIDATED, loan.getStatus());
    }

    // validate loan fail
    @Test
    public void validateLoanFail() {
        try {
            loanService.validateLoan(LOAN_KEY_EMPTY, 1L);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // reject loan success
    @Test
    public void denyLoan() {
        // Mock Artwork
        lenient()
                .when(artworkRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(Long.valueOf(1));
                            return Optional.of(artwork);
                        });

        // Mock Visitor
        lenient()
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Visitor visitor =
                                    new ca.mcgill.ecse321.museum.model.Visitor();
                            visitor.setId(Long.valueOf(1));
                            return Optional.of(visitor);
                        });

        // Mock Employee
        lenient()
                .when(administratorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Employee employee = new Employee();
                            employee.setId(Long.valueOf(1));
                            return Optional.of(employee);
                        });

        Loan loan = loanService.rejectLoan(LOAN_KEY_COMPLETE, 1L);

        assertNotNull(loan);
        assertEquals(LoanStatus.DENIED, loan.getStatus());
    }

    // reject loan fail
    @Test
    public void denyLoanFail() {
        try {
            loanService.rejectLoan(LOAN_KEY_EMPTY, 1L);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // success editLoan

    @Test
    public void editLoan() {
        // Mock Artwork
        lenient()
                .when(artworkRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(Long.valueOf(1));
                            return Optional.of(artwork);
                        });

        // Mock Visitor
        lenient()
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Visitor visitor =
                                    new ca.mcgill.ecse321.museum.model.Visitor();
                            visitor.setId(Long.valueOf(1));
                            return Optional.of(visitor);
                        });

        // Mock Employee
        lenient()
                .when(administratorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Employee employee = new Employee();
                            employee.setId(Long.valueOf(1));
                            return Optional.of(employee);
                        });

        Date newStartDate = new Date(55);
        Date newEndDate = new Date(65);
        Loan loan = loanService.editLoan(LOAN_KEY_EMPTY, newStartDate, newEndDate);

        assertNotNull(loan);
        assertEquals(newStartDate, loan.getStartDate());
        assertEquals(newEndDate, loan.getEndDate());
    }

    // fail getValidatedLoansForArtwork
    @Test
    public void getValidatedLoansForArtworkFail() {
        try {
            loanService.getValidatedLoansForArtwork(1L);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    public void testDeleteLoan() {
        loanService.deleteLoan(LOAN_KEY_EMPTY);
        verify(loanRepository, times(1)).delete(any(Loan.class));
    }
}
