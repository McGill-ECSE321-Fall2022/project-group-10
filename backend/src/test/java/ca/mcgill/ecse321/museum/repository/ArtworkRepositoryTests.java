package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;

@SpringBootTest
public class ArtworkRepositoryTests {

    @Autowired ArtworkRepository artworkRepository;
    @Autowired RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        artworkRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadArtwork() {

        // Create object
        Artwork artwork = new Artwork();
        Room room = new ExhibitRoom();

        // Create attributes
        String title = "Test Title";
        String author = "Test Author";
        Date creationDate = Date.valueOf("2020-01-01");
        boolean isAvailable = true;

        String roomName = "Test Room Name";

        // Set attributes
        artwork.setTitle(title);
        artwork.setAuthor(author);
        artwork.setCreationDate(creationDate);
        artwork.setAvailable(isAvailable);

        room.setName(roomName);
        artwork.setStorage(room);

        // Save object
        room = roomRepository.save(room);
        artwork = artworkRepository.save(artwork);
        long artworkId = artwork.getId();
        long roomId = room.getId();

        // Read object from database
        artwork = artworkRepository.findById(artworkId).orElse(null);
        room = roomRepository.findById(roomId).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(artwork);
        assertNotNull(room);

        assertEquals(title, artwork.getTitle());
        assertEquals(author, artwork.getAuthor());
        assertEquals(creationDate, artwork.getCreationDate());
        assertEquals(isAvailable, artwork.isAvailable());

        assertEquals(roomName, room.getName());
        assertEquals(roomName, artwork.getStorage().getName());
    }

}
