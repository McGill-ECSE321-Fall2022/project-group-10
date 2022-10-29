package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.MuseumSystem;
import ca.mcgill.ecse321.museum.model.Visitor;
@Transactional
@SpringBootTest
public class DonationRepositoryTests {
    @Autowired DonationRepository donationRepository;
    @Autowired VisitorRepository visitorRepository;
    @Autowired AdministratorRepository administratorRepository;
    @Autowired ArtworkRepository artworkRepository;
    @Autowired MuseumSystemRepository museumRepository;


    @AfterEach
    public void clearDatabase() {
        donationRepository.deleteAll();
        visitorRepository.deleteAll();
        administratorRepository.deleteAll();
        artworkRepository.deleteAll();
        museumRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadDonation() {

        // Create object
        Donation donation = new Donation();

        Visitor donor = new Visitor();

        Administrator validator = new Employee();

        List<Artwork> artworks = new ArrayList<>();

        MuseumSystem museum = new MuseumSystem();

        

        Artwork art =new Artwork();
        Artwork art2 = new Artwork();
        artworks.add(art);
        artworks.add(art2);

        // Create attributes
        boolean isValidated = true;
        String description="This artwork features joey and a tentacle monster";
        String firstname="Joey";
        String email="donor@gmail.com";
        String name="sussy museum";
        
        

        // Set attributes
        validator.setFirstName(firstname);
        donor.setEmail(email);
        museum.setName(name);

        donation.setValidated(isValidated);
        donation.setDescription(description);
        donation.setDonor(donor);
        donation.setValidator(validator);
        donation.setMuseum(museum);
        donation.setArtworks(artworks);

        // Save objects to repositories 
       
        museum = museumRepository.save(museum);
        art = artworkRepository.save(art);
        art2 = artworkRepository.save(art2);
        donor = visitorRepository.save(donor);
        validator =administratorRepository.save(validator);
        donation = donationRepository.save(donation);
        
        //get donation ID
        long donid = donation.getId();


        // Read object from database
        donation = donationRepository.findById(donid).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(donation);
        assertEquals(description, donation.getDescription());
        assertNotNull(validator);
        assertEquals(validator.getFirstName(), donation.getValidator().getFirstName());
        assertNotNull(donor);
        assertEquals(donor.getEmail(), donation.getDonor().getEmail());
        assertEquals(isValidated, donation.isValidated());
        assertNotNull(museum);
        assertEquals(museum.getName(), donation.getMuseum().getName());
        assertNotNull(artworks);
        assertEquals(artworks.size(), donation.getArtworks().size());
    }

}
