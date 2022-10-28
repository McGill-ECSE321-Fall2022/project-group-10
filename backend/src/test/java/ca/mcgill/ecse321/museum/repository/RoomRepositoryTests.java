package ca.mcgill.ecse321.museum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;


@SpringBootTest
public class RoomRepositoryTests {
    @Autowired RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadExhibitRoom() {

        // Create object
        ExhibitRoom exhibitRoom = new ExhibitRoom();

        // Create attributes
        String name = "Test Name";
        int capacity = 100;

        // Set attributes
        exhibitRoom.setName(name);
        exhibitRoom.setCapacity(capacity);

        // Save object;
        exhibitRoom = roomRepository.save(exhibitRoom);
        Long id = exhibitRoom.getId();

        // Read object from database
        exhibitRoom = (ExhibitRoom) roomRepository.findById(id).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(exhibitRoom);
        assertEquals(name, exhibitRoom.getName());
        assertEquals(capacity, exhibitRoom.getCapacity());
    }

    @Test
    public void testPersistAndLoadStorageRoom() {

        // Create object
        StorageRoom storageRoom = new StorageRoom();

        // Create attributes
        String name = "Storage";

        // Set attributes
        storageRoom.setName(name);

        // Save object;
        storageRoom = roomRepository.save(storageRoom);
        Long id = storageRoom.getId();

        // Read object from database
        storageRoom = (StorageRoom) roomRepository.findById(id).orElse(null);

        // Assert that object has correct attributes
        assertNotNull(storageRoom);
        assertEquals(name, storageRoom.getName());
    }
}
