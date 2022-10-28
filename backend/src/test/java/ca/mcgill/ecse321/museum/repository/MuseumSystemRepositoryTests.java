package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MuseumSystemRepositoryTests {
    @Autowired
    MuseumSystemRepository museumSystemRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired
    RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        museumSystemRepository.deleteAll();
        calendarRepository.deleteAll();
        personRepository.deleteAll();
        donationRepository.deleteAll();
        loanRepository.deleteAll();
        artworkRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistAndLoadMuseumRepository() {
        // Create object
        MuseumSystem museumSystem = new MuseumSystem();

        // Create attributes
        String name = "Museum Test Name";

        // Set attributes
        museumSystem.setName(name);

        // Set associations
        Calendar calendar = new Calendar();
        calendar.setMuseumOpen(true);
        calendar = calendarRepository.save(calendar);

        museumSystem.setCalendar(calendar);

        // For Persons
        Visitor visitor = new Visitor();
        visitor.setFirstName("Rowan");
        visitor.setLastName("Atkinson");
        visitor.setEmail("mr_bean@ifunny.com");
        visitor.setPassword("plainstringpassword");
        visitor = personRepository.save(visitor);

        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Lennon");
        owner.setEmail("john_lennon@thebeatles.com");
        owner.setPassword("lovelovemedo");
        owner = personRepository.save(owner);

        List users = List.of(visitor, owner);
        museumSystem.setUsers(users);

        // For artworks
        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Girl with a Pearl Earring");
        artwork1.setAuthor("Johannes Vermeer");
        artwork1.setDescription("Meisje met de parel in original Dutch");
        artwork1.setImageLink("https://test123.com");
        artwork1.setPrice(10000);
        artwork1.setAvailable(false);
        artwork1 = artworkRepository.save(artwork1);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Wanderer above the Sea of Fog");
        artwork2.setAuthor("Caspar David Friedrich");
        artwork2.setDescription("Der Wanderer über dem Nebelmeer");
        artwork2.setImageLink("https://test12345.com");
        artwork2.setPrice(999999);
        artwork2.setAvailable(true);
        artwork2 = artworkRepository.save(artwork2);

        List artworks = List.of(artwork1, artwork2);
        museumSystem.setArtworks(artworks);

        // For Donations
        Donation donation1 = new Donation();
        donation1.setArtworks(new ArrayList<Artwork>(List.of(artwork1)));
        donation1.setDescription("This is a very nice painting I would like to donate");
        donation1.setDonor(visitor);
        donation1.setValidated(false);
        donation1 = donationRepository.save(donation1);

        Donation donation2 = new Donation();
        donation2.setArtworks(List.of(artwork2));
        donation2.setDescription("This is a cool painting");
        donation2.setDonor(visitor);
        donation2.setValidated(true);
        donation2.setValidator(owner);
        donation2 = donationRepository.save(donation2);

        List donations = List.of(donation1, donation2);
        museumSystem.setDonations(donations);

        // For Loans
        Loan loan1 = new Loan();
        loan1.setPrice(100);
        loan1.setArtworks(List.of(artwork1));
        loan1.setValidated(false);
        loan1.setCustomer(visitor);
        loan1 = loanRepository.save(loan1);

        Loan loan2 = new Loan();
        loan2.setPrice(100000);
        loan2.setArtworks(List.of(artwork2));
        loan2.setValidated(false);
        loan2.setCustomer(visitor);
        loan2 = loanRepository.save(loan2);

        List loans = List.of(loan1, loan2);
        museumSystem.setLoans(loans);

        // For Rooms
        ExhibitRoom room = new ExhibitRoom();
        room.setCapacity(500);
        room.setArtworks(List.of(artwork1));
        room.setName("room");
        room = roomRepository.save(room);

        StorageRoom storageRoom = new StorageRoom();
        storageRoom.setName("storage");
        storageRoom.setArtworks(List.of(artwork2));
        storageRoom = roomRepository.save(storageRoom);

        List rooms = List.of(room, storageRoom);
        museumSystem.setRooms(rooms);

        // Save object
        museumSystem = museumSystemRepository.save(museumSystem);
        long id = museumSystem.getId();

        // Read object from database
        museumSystem = museumSystemRepository.findById(id).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(museumSystem);
        assertEquals(name, museumSystem.getName());
        assertEquals(loans, museumSystem.getLoans());
        assertEquals(calendar, museumSystem.getCalendar());
        assertEquals(donations, museumSystem.getDonations());
        assertEquals(artworks, museumSystem.getArtworks());
        assertEquals(rooms, museumSystem.getRooms());
    }

}
