/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    @Mock private RoomRepository roomRepository;

    @Mock private ArtworkRepository artworkRepository;

    @InjectMocks private RoomService roomService;

    private static final Long EXHIBIT_LONG = 1L;
    private static final Long STORAGE_LONG = 32L;

    @BeforeEach
    public void setMockOutput() {
        lenient()
                .when(roomRepository.findById(anyLong()))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            if (invocation.getArgument(0).equals(EXHIBIT_LONG)) {
                                ExhibitRoom room = new ExhibitRoom();
                                room.setId(EXHIBIT_LONG);
                                return Optional.of(room);
                            } else if (invocation.getArgument(0).equals(STORAGE_LONG)) {
                                StorageRoom room = new StorageRoom();
                                room.setId(STORAGE_LONG);
                                return Optional.of(room);
                            } else {
                                return Optional.empty();
                            }
                        });

        lenient()
                .when(roomRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ExhibitRoom exhibit = new ExhibitRoom();
                            exhibit.setId(EXHIBIT_LONG);
                            StorageRoom storage = new StorageRoom();
                            storage.setId(STORAGE_LONG);
                            return List.of(exhibit, storage);
                        });

        // when save, return instance
        lenient()
                .when(roomRepository.save(any(ExhibitRoom.class)))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            return invocation.getArgument(0);
                        });
        lenient()
                .when(roomRepository.save(any(StorageRoom.class)))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            return invocation.getArgument(0);
                        });
    }

    // test create exhibit room
    @Test
    public void testCreateExhibitRoomComplete() {
        String roomName = "Exhibit Room 1";
        int capacity = 10;
        ExhibitRoom exhibitRoom = roomService.createExhibitRoom(roomName, capacity);

        assertNotNull(exhibitRoom);
        assertEquals(roomName, exhibitRoom.getName());
        assertEquals(capacity, exhibitRoom.getCapacity());
    }

    // test create storage room
    @Test
    public void testCreateStorageRoomComplete() {
        String roomName = "Storage Room 1";
        StorageRoom storageRoom = roomService.createStorageRoom(roomName);

        assertNotNull(storageRoom);
        assertEquals(roomName, storageRoom.getName());
    }

    // test get exhibit room
    @Test
    public void testGetExhibitRoomComplete() {
        ExhibitRoom exhibitRoom = roomService.getExhibitRoom(EXHIBIT_LONG);

        assertNotNull(exhibitRoom);
        assertEquals(EXHIBIT_LONG, exhibitRoom.getId());
    }

    // test get storage room
    @Test
    public void testGetStorageRoomComplete() {
        StorageRoom storageRoom = roomService.getStorageRoom(STORAGE_LONG);

        assertNotNull(storageRoom);
        assertEquals(STORAGE_LONG, storageRoom.getId());
    }

    // test fail get exhibit room
    @Test
    public void testFailGetExhibitRoom() {
        Long id = 3L;
        assertThrows(
                ServiceLayerException.class,
                () -> {
                    roomService.getExhibitRoom(id);
                });
    }

    // test fail get storage room
    @Test
    public void testFailGetStorageRoom() {
        Long id = 3L;
        assertThrows(
                ServiceLayerException.class,
                () -> {
                    roomService.getStorageRoom(id);
                });
    }

    // Test update exhibit room
    @Test
    public void testUpdateExhibitRoomComplete() {

        // Mock existing exhibit room
        lenient()
                .when(roomRepository.findById(3L))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            ExhibitRoom room = new ExhibitRoom();
                            room.setId(3L);
                            room.setName("Exhibit Room 1");
                            room.setCapacity(10);
                            return Optional.of(room);
                        });

        String newRoomName = "Exhibit Room 2";
        int newCapacity = 20;
        ExhibitRoom updatedExhibitRoom =
                roomService.updateExhibitRoom(3L, newRoomName, newCapacity);

        assertNotNull(updatedExhibitRoom);
        assertEquals(newRoomName, updatedExhibitRoom.getName());
        assertEquals(newCapacity, updatedExhibitRoom.getCapacity());
    }

    // Test update storage room
    @Test
    public void testUpdateStorageRoomComplete() {
        // Mock existing storage room
        lenient()
                .when(roomRepository.findById(4L))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            StorageRoom room = new StorageRoom();
                            room.setId(4L);
                            room.setName("Storage Room 1");
                            return Optional.of(room);
                        });

        String newRoomName = "Storage Room 2";
        StorageRoom updatedStorageRoom = roomService.updateStorageRoom(4L, newRoomName);

        assertNotNull(updatedStorageRoom);
        assertEquals(newRoomName, updatedStorageRoom.getName());
    }

    // Test get all exhibit rooms
    @Test
    public void testGetAllExhibitRooms() {
        List<ExhibitRoom> rooms = roomService.getAllExhibitRooms();
        assertNotNull(rooms);
        assertNotNull(rooms.get(0));
        assertEquals(EXHIBIT_LONG, rooms.get(0).getId());
        assertEquals(1, rooms.size());
    }

    // Test get all storage rooms
    @Test
    public void testGetAllStorageRooms() {
        List<StorageRoom> rooms = roomService.getAllStorageRooms();
        assertNotNull(rooms);
        assertNotNull(rooms.get(0));
        assertEquals(STORAGE_LONG, rooms.get(0).getId());
        assertEquals(1, rooms.size());
    }

    // Test delete exhibit room
    @Test
    public void testDeleteRoom() {
        roomService.deleteRoom(EXHIBIT_LONG);
        verify(roomRepository, times(1)).delete(any(Room.class));
    }

    // Test countArtworksInExhibitRoom
    @Test
    public void testCountArtworksInExhibitRoom() {

        // Mock artwork
        lenient()
                .when(artworkRepository.findById(1L))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            Artwork artwork = new Artwork();
                            artwork.setId(1L);
                            artwork.setTitle("Artwork 1");
                            return Optional.of(artwork);
                        });

        long count = roomService.countArtworksInExhibitRoom(1L);
        assertEquals(0L, count);
    }

    @Test
    public void testUpdateExhibitRoomButExhibitRoomIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> roomService.updateExhibitRoom(999, "yeet", 100));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such exhibit rooms", exception.getMessage());
    }

    @Test
    public void testUpdateStorageRoomButStorageRoomIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> roomService.updateStorageRoom(999, "yeet"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such rooms", exception.getMessage());
    }

    @Test
    public void testDeleteRoomButRoomIsNull() {
        ServiceLayerException exception =
                assertThrows(ServiceLayerException.class, () -> roomService.deleteRoom(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such rooms", exception.getMessage());
    }

    @Test
    public void testcountArtworksInExhibitRoomButExhibitRoomIsNull() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class,
                        () -> roomService.countArtworksInExhibitRoom(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such exhibit rooms", exception.getMessage());
    }
}
