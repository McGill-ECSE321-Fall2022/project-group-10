package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.controller.body.CreateArtworkRequestBody;
import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArtworkRestControllerTests {

    @Mock
    private ArtworkService artworkService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ArtworkRestController artworkRestController;

    private static final Long ARTWORK_KEY = Long.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(artworkService.createArtwork(
                anyString(),
                anyString(),
                any(Date.class),
                anyString(),
                anyString(),
                anyFloat(),
                anyBoolean())).thenReturn(mock(Artwork.class));

        lenient().when(artworkService.getAllArtworks()).thenReturn(List.of(mock(Artwork.class)));

        lenient().when(artworkService.getArtwork(anyLong())).thenReturn(mock(Artwork.class));

        lenient().when(artworkService.moveArtworkToRoom(any(Artwork.class),any(Room.class))).thenReturn(mock(Artwork.class));

        lenient().doNothing().when(artworkService).deleteArtwork(any(Artwork.class));

        lenient().when(modelMapper.map(any(Artwork.class),any(Class.class))).thenReturn(mock(ArtworkDto.class));
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
        var body = new CreateArtworkRequestBody();
        body.setTitle(title);
        body.setAuthor(author);
        body.setCreationDate(date);
        body.setDescription(description);
        body.setImageLink(imageLink);
        body.setPrice(price);
        body.setAvailable(isAvailable);
        var artwork = artworkRestController.createArtwork(body);
        assertNotNull(artwork);
    }

    @Test public void testGetArtwork() {
        ArtworkDto artwork = artworkRestController.getArtwork(1);
        assertNotNull(artwork);
    }

    @Test public void testGetAllArtworks() {
        List<ArtworkDto> artworks = artworkRestController.getAllArtworks();
        assertNotNull(artworks);
        assertNotNull(artworks.get(0));
    }

    @Test public void testMoveArtworkToRoom() {
        var room = new ExhibitRoom();
        var artwork = artworkRestController.moveArtworkToRoom(1, room);
        assertNotNull(artwork);
    }

    @Test public void testDeleteArtwork() {
        var artwork = new Artwork();
        artworkRestController.deleteArtwork(1);
        verify(artworkService,times(1)).deleteArtwork(any(Artwork.class));
    }

}
