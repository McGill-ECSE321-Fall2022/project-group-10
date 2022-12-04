/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.DonationRepository;
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
public class DonationServiceTests {

    @Mock private PersonRepository personRepository;
    @Mock private AdministratorRepository administratorRepository;
    @Mock private ArtworkRepository artworkRepository;
    @Mock private DonationRepository donationRepository;
    @Mock private ArtworkService artworkService;

    @InjectMocks private DonationService donationService;

    private static final Long DONATION_KEY_INVALID = Long.valueOf(1);
    private static final Long DONATION_KEY_VALID = Long.valueOf(2);
    private static final Long DONOR_KEY = Long.valueOf(3);
    private static final Long VALIDATOR_KEY = Long.valueOf(4);
    private static final Long ARTWORK_KEY = Long.valueOf(5);
    // code that is executed before every test
    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(donationRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Donation donation = new Donation();
                            donation.setId(DONATION_KEY_INVALID);
                            return List.of(donation);
                        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        lenient()
                .when(donationRepository.save(any(Donation.class)))
                .thenAnswer(returnParameterAsAnswer);
        // Donation that has not be validated yet
        lenient()
                .when(donationRepository.findById(DONATION_KEY_INVALID))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            String description = "This artwork is of an apple";
                            Donation donation = new Donation();
                            donation.setDescription(description);
                            Visitor donor = new Visitor();
                            donor.setId(DONOR_KEY);
                            donation.setId(DONATION_KEY_INVALID);
                            donation.setDonor(donor);
                            return Optional.of(donation);
                        });

        // Donation that has beeen validated
        lenient()
                .when(donationRepository.findById(DONATION_KEY_VALID))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            String description = "This artwork is of Joey";
                            Donation donation = new Donation();
                            donation.setDescription(description);
                            donation.setValidated(true);
                            Visitor donor = new Visitor();
                            donor.setId(DONOR_KEY);
                            donation.setId(DONATION_KEY_VALID);
                            donation.setDonor(donor);
                            Employee validator = new Employee();
                            validator.setId(VALIDATOR_KEY);
                            donation.setValidator(validator);
                            return Optional.of(donation);
                        });
        // Mock Artwork
        lenient()
                .when(artworkRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(ARTWORK_KEY);
                            return Optional.of(artwork);
                        });
        // donation that has been validated

        // Mock Visitor
        lenient()
                .when(personRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Visitor visitor =
                                    new ca.mcgill.ecse321.museum.model.Visitor();
                            visitor.setId(Long.valueOf(DONOR_KEY));
                            return Optional.of(visitor);
                        });

        lenient()
                .when(administratorRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Administrator administrator =
                                    new ca.mcgill.ecse321.museum.model.Employee();
                            administrator.setId(Long.valueOf(DONOR_KEY));
                            return Optional.of(administrator);
                        });

        lenient()
                .when(
                        artworkService.createArtwork(
                                anyString(),
                                anyString(),
                                any(Date.class),
                                anyString(),
                                anyString(),
                                anyFloat(),
                                anyBoolean()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ca.mcgill.ecse321.museum.model.Artwork artwork =
                                    new ca.mcgill.ecse321.museum.model.Artwork();
                            return artwork;
                        });
    }
    // Test creating a Donation
    @Test
    public void testCreatDonationComplete() {

        String description = "This artwork is of an apple";
        Donation donation = donationService.createDonation(description, DONOR_KEY);

        assertNotNull(donation);
        assertEquals(description, donation.getDescription());
        assertEquals(false, donation.isValidated());
        assertEquals(DONOR_KEY, donation.getDonor().getId());
    }

    // Test creating a donation fail
    @Test
    public void testCreatDonationFail() {

        String description = "This artwork is of Joey";
        try {
            donationService.createDonation(description, Long.valueOf(400));

        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // Test validating a donation
    @Test
    public void testValidateDonation() {
        String author = "Keith Crochetiere";
        String title = "Banana";
        float price = 50;
        String imagelink = "WWW.321isbestclass.com";
        Date creationDate = new Date(1337137);
        Boolean isAvailable = true;

        Donation donation =
                donationService.validateDonation(
                        DONATION_KEY_INVALID,
                        VALIDATOR_KEY,
                        price,
                        title,
                        author,
                        imagelink,
                        creationDate,
                        isAvailable);

        assertNotNull(donation);
        assertNotNull(donation.getArtworks());
        assertEquals(true, donation.isValidated());
    }

    // Test validating a donation with wrong donation ID
    public void testValidateDonationfail() {
        String author = "Keith Crochetiere";
        String title = "Banana";
        float price = 50;
        String imagelink = "WWW.321isbestclass.com";
        Date creationDate = new Date(1337137);
        Boolean isAvailable = true;

        try {
            donationService.validateDonation(
                    800, VALIDATOR_KEY, price, title, author, imagelink, creationDate, isAvailable);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // Test validating a donation with wrong Validator ID
    public void testValidateDonationfail2() {
        String author = "Keith Crochetiere";
        String title = "Banana";
        float price = 50;
        String imagelink = "WWW.321isbestclass.com";
        Date creationDate = new Date(1337137);
        Boolean isAvailable = true;

        try {
            donationService.validateDonation(
                    DONATION_KEY_INVALID,
                    300,
                    price,
                    title,
                    author,
                    imagelink,
                    creationDate,
                    isAvailable);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // Test deleting a donation
    @Test
    public void testDeleteDonation() {
        donationService.deleteDonation(DONATION_KEY_INVALID);
        verify(donationRepository, times(1)).delete(any(Donation.class));
    }

    // Delete donation fail
    @Test
    public void testDeleteDonationfail() {
        try {
            donationService.deleteDonation(Long.valueOf(3000));
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    // Test getting all donations
    @Test
    public void testGetAllDonations() {
        List<Donation> donations = donationService.getAllDonations();
        assertNotNull(donations);
        assertNotNull(donations.get(0));
        assertEquals(DONATION_KEY_INVALID, donations.get(0).getId());
        assertEquals(1, donations.size());
    }

    // Test get donation
    @Test
    public void testGetDonationSuccess() {
        Donation donation = donationService.getDonation(DONATION_KEY_VALID);
        assertNotNull(donation);
        assertEquals(DONATION_KEY_VALID, donation.getId());
    }

    // Test get donation fail
    @Test
    public void testGetDonationFail() {
        try {
            donationService.getDonation(222);
            fail();
        } catch (ServiceLayerException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}
