package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;
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
    private VisitorRepository visitorRepository;
    @Mock
    private AdministratorRepository administratorRepository;
    @Mock
    private ArtworkRepository artworkRepository;
    @Mock
    private LoanRepository loanRepository;    

    @InjectMocks
    private LoanService loanService;

    private static final Long LOAN_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(loanRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(LOAN_KEY)) {
                Loan loan = new Loan();
                loan.setId(LOAN_KEY);
                return Optional.of(loan);
            } else {
                return null;
            }
        });
    }

    @Test
    public void testCreateLoanComplete() {
    }

    @Test public void testCreateArtworkNoStorage() {
    }

    @Test public void testGetArtwork() {
    }

    @Test public void testGetAllArtworks() {
    }

    @Test public void testMoveArtworkToRoom() {
    }

    @Test public void testDeleteArtwork() {
    }
}
