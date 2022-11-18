/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import ca.mcgill.ecse321.museum.repository.StorageRoomRepository;
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
public class ArtworkServiceTests {

    @Mock private ArtworkRepository artworkRepository;
    @Mock private StorageRoomRepository storageRoomRepository;
    @Mock private RoomRepository roomRepository;

    @InjectMocks private ArtworkService artworkService;

    private static final Long ARTWORK_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        // Artwork 1 exists
        lenient()
                .when(artworkRepository.findById(ARTWORK_KEY))
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var artwork = new Artwork();
                            artwork.setId(ARTWORK_KEY);
                            return Optional.of(artwork);
                        });

        // Artwork 2 does not exist
        lenient().when(artworkRepository.findById(2L)).thenReturn(Optional.empty());

        // List of all artworks is a list containing Artwork 1
        lenient()
                .when(artworkRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var artwork = new Artwork();
                            artwork.setId(ARTWORK_KEY);
                            return List.of(artwork);
                        });

        // Room 2 is a non-full room
        lenient()
                .when(roomRepository.findById(2L))
                .thenAnswer(
                        invocation -> {
                            var exhibit = new ExhibitRoom();
                            exhibit.setId(2);
                            exhibit.setCapacity(200);
                            exhibit.setName("Exhibit");
                            return Optional.of(exhibit);
                        });

        // Room 3 is a full room
        var fullExhibit = new ExhibitRoom();
        fullExhibit.setId(3);
        fullExhibit.setCapacity(200);
        fullExhibit.setName("Exhibit");
        lenient().when(roomRepository.findById(3L)).thenReturn(Optional.of(fullExhibit));
        lenient().when(artworkRepository.countArtworksByStorage(fullExhibit)).thenReturn(200L);

        // Room 4 is a storage
        lenient()
                .when(roomRepository.findById(4L))
                .thenAnswer(
                        invocation -> {
                            StorageRoom storage = new StorageRoom();
                            storage.setId(4);
                            storage.setName("Storage");
                            return Optional.of(storage);
                        });

        // Room 5 does not exist
        lenient().when(roomRepository.findById(5L)).thenReturn(Optional.empty());

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer =
                (InvocationOnMock invocation) -> {
                    return invocation.getArgument(0);
                };

        // Saving an artwork just returns it
        lenient()
                .when(artworkRepository.save(any(Artwork.class)))
                .thenAnswer(returnParameterAsAnswer);
    }

    /** Test CreateArtwork with complete information and with existing storage */
    @Test
    public void testCreateArtworkComplete() {
        // Mock a storage room
        lenient()
                .when(storageRoomRepository.findAll())
                .thenAnswer(
                        (InvocationOnMock invocation) -> {
                            var storage = new StorageRoom();
                            return List.of(storage);
                        });

        var title = "Mona Lisa";
        var author = "Leonardo da Vinci";
        var date = new Date(13371337);
        var description = "This is a nice painting";
        var imageLink = "https://yeet.com/images/124";
        float price = 100;
        boolean isAvailable = false;
        var artwork =
                artworkService.createArtwork(
                        title, author, date, description, imageLink, price, isAvailable);
        assertNotNull(artwork);
        assertNotNull(artwork.getStorage());
        assertEquals(title, artwork.getTitle());
        assertEquals(author, artwork.getAuthor());
        assertEquals(date, artwork.getCreationDate());
        assertEquals(description, artwork.getDescription());
        assertEquals(imageLink, artwork.getImageLink());
        assertEquals(price, artwork.getPrice());
        assertEquals(isAvailable, artwork.isAvailable());
    }

    /** Test CreateArtwork with complete information and with no existing storage */
    @Test
    public void testCreateArtworkNoStorage() {
        var title = "Mona Lisa";
        var author = "Leonardo da Vinci";
        var date = new Date(13371337);
        var description = "This is a nice painting";
        var imageLink = "https://yeet.com/images/124";
        float price = 100;
        boolean isAvailable = false;
        var artwork =
                artworkService.createArtwork(
                        title, author, date, description, imageLink, price, isAvailable);
        assertNotNull(artwork);
        assertNull(artwork.getStorage());
        assertEquals(title, artwork.getTitle());
        assertEquals(author, artwork.getAuthor());
        assertEquals(date, artwork.getCreationDate());
        assertEquals(description, artwork.getDescription());
        assertEquals(imageLink, artwork.getImageLink());
        assertEquals(price, artwork.getPrice());
        assertEquals(isAvailable, artwork.isAvailable());
    }

    /** Test GetArtwork and artwork exists */
    @Test
    public void testGetArtwork() {
        Artwork artwork = artworkService.getArtwork(1L);
        assertNotNull(artwork);
        assertEquals(ARTWORK_KEY, artwork.getId());
    }

    /** Test GetArtwork and artwork does not exist */
    @Test
    public void testGetArtworkFail() {
        ServiceLayerException exception =
                assertThrows(ServiceLayerException.class, () -> artworkService.getArtwork(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such artwork", exception.getMessage());
    }

    /** Test GetAllArtworks */
    @Test
    public void testGetAllArtworks() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        assertNotNull(artworks);
        assertNotNull(artworks.get(0));
        assertEquals(ARTWORK_KEY, artworks.get(0).getId());
    }

    /** Test Move Artwork to Room Successful */
    @Test
    public void testMoveArtworkToRoom() {
        var artwork = new Artwork();
        artwork = artworkService.moveArtworkToRoom(1L, 2L);
        assertNotNull(artwork);
        assertEquals(2, artwork.getStorage().getId());
    }

    /** Test Move Artwork to Room Successful */
    @Test
    public void testMoveArtworkToFullRoom() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class, () -> artworkService.moveArtworkToRoom(1, 3));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals("The room is full", exception.getMessage());
    }
    /** Test Move Artwork to a Storage Room */
    @Test
    public void testMoveArtworkToStorage() {
        var artwork = new Artwork();
        artwork = artworkService.moveArtworkToRoom(1L, 4L);
        assertNotNull(artwork);
        assertEquals(4, artwork.getStorage().getId());
    }
    /** Test Move Artwork but Artwork not found */
    @Test
    public void testMoveArtworkButArtworkDoesNotExist() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class, () -> artworkService.moveArtworkToRoom(2, 2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such artwork", exception.getMessage());
    }
    /** Test Move Artwork but Room not found */
    @Test
    public void testMoveArtworkButRoomDoesNotExist() {
        ServiceLayerException exception =
                assertThrows(
                        ServiceLayerException.class, () -> artworkService.moveArtworkToRoom(1, 5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such room", exception.getMessage());
    }

    /** Test Delete Artwork */
    @Test
    public void testDeleteArtwork() {
        artworkService.deleteArtwork(1L);
        verify(artworkRepository, times(1)).delete(any(Artwork.class));
    }
    /** Test Delete Artwork but Artwork not found */
    @Test
    public void testDeleteArtworkDoesNotExist() {
        var artwork = new Artwork();
        ServiceLayerException exception =
                assertThrows(ServiceLayerException.class, () -> artworkService.deleteArtwork(2L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No such artwork", exception.getMessage());
    }
}
