package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import ca.mcgill.ecse321.museum.repository.StorageRoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {

    @Mock
    private ArtworkRepository artworkRepository;
    @Mock
    private StorageRoomRepository storageRoomRepository;

    @InjectMocks
    private ArtworkService artworkService;

    private static final Long ARTWORK_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(artworkRepository.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ARTWORK_KEY)) {
                var artwork = new Artwork();
                artwork.setId(ARTWORK_KEY);
                return Optional.of(artwork);
            } else {
                return null;
            }
        });

        lenient().when(artworkRepository.findAll()).thenAnswer ( (InvocationOnMock invocation) -> {
            var artwork = new Artwork();
            artwork.setId(ARTWORK_KEY);
            return List.of(artwork);
        });

        lenient().when(storageRoomRepository.findAll()).thenAnswer ( (InvocationOnMock invocation) -> {
            var storage = new StorageRoom();
            return List.of(storage);
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
    }
    @Test
    public void testCreateArtworkComplete() {
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

    @Test public void testGetArtwork() {
        Artwork artwork = artworkService.getArtwork(1);
        assertNotNull(artwork);
        assertEquals(ARTWORK_KEY,artwork.getId());
    }

    @Test public void testGetAllArtworks() {
        List<Artwork> artworks = artworkService.getAllArtworks();
        assertNotNull(artworks);
        assertNotNull(artworks.get(0));
        assertEquals(ARTWORK_KEY, artworks.get(0).getId());
    }

    @Test public void testMoveArtworkToRoom() {
        var room = new ExhibitRoom();
        var artwork = new Artwork();
        artwork = artworkService.moveArtworkToRoom(artwork, room);
        assertNotNull(artwork);
        assertEquals(room, artwork.getStorage());
    }
    @Test public void testDeleteArtwork() {
        var artwork = new Artwork();
        artworkService.deleteArtwork(artwork);
        verify(artworkRepository,times(1)).delete(artwork);
    }

}
