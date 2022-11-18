package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import ca.mcgill.ecse321.museum.repository.StorageRoomRepository;
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

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {

    @Mock private ArtworkRepository artworkRepository;
    @Mock private StorageRoomRepository storageRoomRepository;
    @Mock private RoomRepository roomRepository;

    @InjectMocks
    private ArtworkService artworkService;

    private static final Long ARTWORK_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(artworkRepository.findById(ARTWORK_KEY)).thenAnswer( (InvocationOnMock invocation) -> {
                var artwork = new Artwork();
                artwork.setId(ARTWORK_KEY);
                return Optional.of(artwork);
        });

        lenient().when(artworkRepository.findById(2L)).thenReturn(Optional.empty());

        lenient().when(artworkRepository.findAll()).thenAnswer ( (InvocationOnMock invocation) -> {
            var artwork = new Artwork();
            artwork.setId(ARTWORK_KEY);
            return List.of(artwork);
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * Test CreateArtwork with complete information and with existing storage
     */
    @Test
    public void testCreateArtworkComplete() {
        // Mock a storage room
        lenient().when(storageRoomRepository.findAll()).thenAnswer ( (InvocationOnMock invocation) -> {
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
        var artwork = artworkService.createArtwork(title, author, date, description, imageLink, price, isAvailable);
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

    /**
     * Test CreateArtwork with complete information and with no existing storage
     */
    @Test public void testCreateArtworkNoStorage() {
        var title = "Mona Lisa";
        var author = "Leonardo da Vinci";
        var date = new Date(13371337);
        var description = "This is a nice painting";
        var imageLink = "https://yeet.com/images/124";
        float price = 100;
        boolean isAvailable = false;
        var artwork = artworkService.createArtwork(title, author, date, description, imageLink, price, isAvailable);
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

    /**
     * Test GetArtwork and artwork exists
     */
    @Test public void testGetArtwork() {
        Artwork artwork = artworkService.getArtwork(1);
        assertNotNull(artwork);
        assertEquals(ARTWORK_KEY,artwork.getId());
    }

    /**
     * Test GetArtwork and artwork does not exist
     */
    @Test public void testGetArtworkFail() {
        ServiceLayerException exception = assertThrows(ServiceLayerException.class, () -> artworkService.getArtwork(2L));
        assertEquals(HttpStatus.NOT_FOUND,exception.getStatus());
        assertEquals("No such artwork", exception.getMessage());
    }

    /**
     * Test GetAllArtworks
     */
    @Test public void testGetAllArtworks() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        assertNotNull(artworks);
        assertNotNull(artworks.get(0));
        assertEquals(ARTWORK_KEY, artworks.get(0).getId());
    }

    /**
     * Test Move Artwork to Room Successful
     */
    @Test public void testMoveArtworkToRoom() {
        var exhibit = new ExhibitRoom();
        exhibit.setId(2);
        exhibit.setCapacity(200);
        exhibit.setName("Exhibit");

        lenient().when(roomRepository.findById(2L)).thenReturn(Optional.of(exhibit));

        var artwork = new Artwork();
        artwork = artworkService.moveArtworkToRoom(1, 2);
        assertNotNull(artwork);
        assertEquals(exhibit, artwork.getStorage());

    }

    @Test public void testMoveArtworkToFullRoom() {
        var exhibit = new ExhibitRoom();
        exhibit.setId(2);
        exhibit.setCapacity(200);
        exhibit.setName("Exhibit");

        lenient().when(roomRepository.findById(2L)).thenReturn(Optional.of(exhibit));
        lenient().when(artworkRepository.countArtworksByStorage(exhibit)).thenReturn(200L);

        ServiceLayerException exception = assertThrows(ServiceLayerException.class, () -> artworkService.moveArtworkToRoom(1, 2));
        assertEquals(HttpStatus.FORBIDDEN,exception.getStatus());
        assertEquals("The room is full",exception.getMessage());
    }
    @Test public void testMoveArtworkToStorage() {}
    @Test public void testMoveArtworkButArtworkDoesNotExist() {
        var exhibit = new ExhibitRoom();
        exhibit.setId(2);
        exhibit.setCapacity(200);
        exhibit.setName("Exhibit");

        lenient().when(roomRepository.findById(2L)).thenReturn(Optional.of(exhibit));
        lenient().when(artworkRepository.countArtworksByStorage(exhibit)).thenReturn(200L);

        ServiceLayerException exception = assertThrows(ServiceLayerException.class, () -> artworkService.moveArtworkToRoom(1, 2));
        assertEquals(HttpStatus.FORBIDDEN,exception.getStatus());
        assertEquals("The room is full",exception.getMessage());
    }
    @Test public void testMoveArtworkButRoomDoesNotExist() {}

    @Test public void testDeleteArtwork() {
        var artwork = new Artwork();
        artworkService.deleteArtwork(1);
        verify(artworkRepository,times(1)).delete(artwork);
    }

}
