/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomRepositoryTests {
    @Autowired RoomRepository roomRepository;
    @Autowired ArtworkRepository artworkRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
        artworkRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadExhibitRoom() {

        // Create object
        ExhibitRoom exhibitRoom = new ExhibitRoom();

        // Create Artwork object

        List<Artwork> artworks = new ArrayList<Artwork>();

        Artwork artwork1 = new Artwork();
        Artwork artwork2 = new Artwork();

        // Create attributes
        String name = "Test Name";
        int capacity = 100;

        // Create artwork1 attributes
        String artwork1Title = "Test Artwork Title1";
        String artwork1Author = "Test Artwork Author1";
        Date creationDate1 = Date.valueOf("2020-01-01");
        boolean isAvailable1 = true;

        // Create artwork2 attributes
        String artwork2Title = "Test Artwork Title2";
        String artwork2Author = "Test Artwork Author2";
        Date creationDate2 = Date.valueOf("2020-01-02");
        boolean isAvailable2 = false;

        // Set attributes for artwork1

        artwork1.setTitle(artwork1Title);
        artwork1.setAuthor(artwork1Author);
        artwork1.setCreationDate(creationDate1);
        artwork1.setAvailable(isAvailable1);

        // Set attributes for artwork2
        artwork2.setTitle(artwork2Title);
        artwork2.setAuthor(artwork2Author);
        artwork2.setCreationDate(creationDate2);
        artwork2.setAvailable(isAvailable2);

        // Add artwork1 and artwork2 to artworks
        artworks.add(artwork1);
        artworks.add(artwork2);

        // Set attributes for exhibitroom
        exhibitRoom.setName(name);
        exhibitRoom.setCapacity(capacity);

        // Save object;
        artworks = artworkRepository.saveAll(artworks);
        exhibitRoom = roomRepository.save(exhibitRoom);

        Long roomId = exhibitRoom.getId();

        // Read object from database
        exhibitRoom = (ExhibitRoom) roomRepository.findById(roomId).orElse(null);
        artworks = artworkRepository.findAll();

        // Assert that object has correct attributes
        assertNotNull(exhibitRoom);
        assertEquals(name, exhibitRoom.getName());
        assertEquals(capacity, exhibitRoom.getCapacity());

        assertEquals(2, artworks.size());
        assertEquals(artwork1Title, artworks.get(0).getTitle());
        assertEquals(artwork1Author, artworks.get(0).getAuthor());
        assertEquals(creationDate1, artworks.get(0).getCreationDate());
        assertEquals(isAvailable1, artworks.get(0).isAvailable());

        assertEquals(artwork2Title, artworks.get(1).getTitle());
        assertEquals(artwork2Author, artworks.get(1).getAuthor());
        assertEquals(creationDate2, artworks.get(1).getCreationDate());
        assertEquals(isAvailable2, artworks.get(1).isAvailable());
    }

    @Test
    public void testPersistAndLoadStorageRoom() {

        // Create object
        StorageRoom storageRoom = new StorageRoom();

        // Create attributes
        String name = "Storage";

        List<Artwork> artworks = new ArrayList<Artwork>();

        Artwork artwork1 = new Artwork();
        Artwork artwork2 = new Artwork();

        String artwork1Title = "Test Artwork Title1";
        String artwork1Author = "Test Artwork Author1";
        Date creationDate1 = Date.valueOf("2020-01-01");
        boolean isAvailable1 = true;

        // Create artwork2 attributes
        String artwork2Title = "Test Artwork Title2";
        String artwork2Author = "Test Artwork Author2";
        Date creationDate2 = Date.valueOf("2020-01-02");
        boolean isAvailable2 = false;

        // Set attributes for artwork1

        artwork1.setTitle(artwork1Title);
        artwork1.setAuthor(artwork1Author);
        artwork1.setCreationDate(creationDate1);
        artwork1.setAvailable(isAvailable1);

        // Set attributes for artwork2
        artwork2.setTitle(artwork2Title);
        artwork2.setAuthor(artwork2Author);
        artwork2.setCreationDate(creationDate2);
        artwork2.setAvailable(isAvailable2);

        // Add artwork1 and artwork2 to artworks
        artworks.add(artwork1);
        artworks.add(artwork2);

        // Set attributes for storage room
        storageRoom.setName(name);

        // Save object;
        artworks = artworkRepository.saveAll(artworks);
        storageRoom = roomRepository.save(storageRoom);
        Long id = storageRoom.getId();

        // Read object from database
        storageRoom = (StorageRoom) roomRepository.findById(id).orElse(null);
        artworks = artworkRepository.findAll();

        // Assert that object has correct attributes
        assertNotNull(storageRoom);
        assertEquals(name, storageRoom.getName());

        assertEquals(2, artworks.size());
        assertEquals(artwork1Title, artworks.get(0).getTitle());
        assertEquals(artwork1Author, artworks.get(0).getAuthor());
        assertEquals(creationDate1, artworks.get(0).getCreationDate());
        assertEquals(isAvailable1, artworks.get(0).isAvailable());

        assertEquals(artwork2Title, artworks.get(1).getTitle());
        assertEquals(artwork2Author, artworks.get(1).getAuthor());
        assertEquals(creationDate2, artworks.get(1).getCreationDate());
        assertEquals(isAvailable2, artworks.get(1).isAvailable());
    }
}
