package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.museum.model.Artwork;

@SpringBootTest
public class ArtworkRepositoryTests {
    @Autowired ArtworkRepository artworkRepository;

    @AfterEach
    public void clearDatabase() {
        artworkRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadArtwork() {

        // Create object
        Artwork artwork = new Artwork();

        // Create attributes
        String title = "Test Title";
        String author = "Test Author";
        String description = "Test Description";
        String imageLink = "Test Image Link";
        Date creationDate = Date.valueOf("2020-01-01");
        float price = 100.0f;
        boolean isAvailable = true;

        // Set attributes
        artwork.setTitle(title);
        artwork.setAuthor(author);
        artwork.setDescription(description);
        artwork.setImageLink(imageLink);
        artwork.setCreationDate(creationDate);
        artwork.setPrice(price);
        artwork.setAvailable(isAvailable);

        // Save object
        artwork = artworkRepository.save(artwork);
        long id = artwork.getId();

        // Read object from database
        artwork = artworkRepository.findById(id).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(artwork);
        assertEquals(title, artwork.getTitle());
        assertEquals(author, artwork.getAuthor());
        assertEquals(description, artwork.getDescription());
        assertEquals(imageLink, artwork.getImageLink());
        assertEquals(creationDate, artwork.getCreationDate());
        assertEquals(price, artwork.getPrice());
        assertEquals(isAvailable, artwork.isAvailable());
    }

}
