package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {

    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private ArtworkService artworkService;

    @BeforeEach
    public void setMockOutput() {
        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);
    }

    @AfterEach


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
        assertEquals(title, artwork.getTitle());
        assertEquals(author, artwork.getAuthor());
        assertEquals(date, artwork.getCreationDate());
        assertEquals(description, artwork.getDescription());
        assertEquals(imageLink, artwork.getImageLink());
        assertEquals(price, artwork.getPrice());
        assertEquals(isAvailable, artwork.isAvailable());
    }

    
}
