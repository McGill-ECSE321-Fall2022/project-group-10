package ca.mcgill.ecse321.museum.integration;

import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtworkStepDefinitions {
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired TestRestTemplate client;
    @Autowired ModelMapper modelMapper;

    List<ArtworkDto> artworks = List.of();
    ArtworkDto artwork = null;
    HttpStatus httpStatus = null;

    @Before
    public void resetState() {
        artworks = List.of();
        artwork = null;
        httpStatus = null;
    }

    @Given("the following artworks exist:")
    public void theFollowingArtworksExist(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        rows.forEach((Map<String,String> item) -> {
            var artwork = modelMapper.map(item,Artwork.class);
            artworkRepository.save(artwork);
        });
    }

    @When("a GET request to artworks is made")
    public void aGETRequestToArtworksIsMade() {
        HttpEntity requestEntity = new HttpEntity<>(null, new HttpHeaders());
        var response = client.exchange("/artworks", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ArtworkDto>>() {
        });
        artworks = response.getBody();
        httpStatus = response.getStatusCode();
    }

    @Then("the following artworks are returned:")
    public void theFollowingArtworksAreReturned(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        List<ArtworkDto> returnedArtworks = new ArrayList<>();
        rows.forEach((Map<String,String> item) -> {
            var artwork = modelMapper.map(item,ArtworkDto.class);
            returnedArtworks.add(artwork);
        });
        assertEquals(artworks.size(),returnedArtworks.size());
        returnedArtworks.forEach(returnedArtwork -> {
            var artwork = artworks.stream().filter(art -> Objects.equals(art.getId(), returnedArtwork.getId())).findFirst().orElse(null);
            assertNotNull(artwork);
            assertEquals(artwork.getTitle(),returnedArtwork.getTitle());
            assertEquals(artwork.getAuthor(),returnedArtwork.getAuthor());
            assertEquals(artwork.getPrice(),returnedArtwork.getPrice());
            assertEquals(artwork.getCreationDate(),returnedArtwork.getCreationDate());
            assertEquals(artwork.getDescription(), returnedArtwork.getDescription());
            assertEquals(artwork.isAvailable(),returnedArtwork.isAvailable());
            assertEquals(artwork.getStorage(),returnedArtwork.getStorage());
        } );
    }

    @Then("an HTTP Status of {string} is returned")
    public void anHTTPStatusOfIsReturned(String httpStatusString) {
        var expectedHttpStatus = HttpStatus.valueOf(parseInt(httpStatusString));
        assertEquals(expectedHttpStatus, httpStatus);
    }

    @When("a DELETE request to artworks.{string} is made")
    public void aDELETERequestToArtworksIsMade(String arg0) {
        HttpEntity requestEntity = new HttpEntity<>(null, new HttpHeaders());
        var response = client.exchange("/artworks/"+arg0, HttpMethod.DELETE, requestEntity, new ParameterizedTypeReference<>() {
        });
        httpStatus = response.getStatusCode();
    }

    @When("a GET request to artworks.{string} is made")
    public void aGETRequestToArtworksIsMade(String arg0) {
        HttpEntity requestEntity = new HttpEntity<>(null, new HttpHeaders());
        var response = client.exchange("/artworks/"+arg0, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<ArtworkDto>() {
        });
        artwork = response.getBody();
        httpStatus = response.getStatusCode();
    }

    @Then("the following artwork is returned:")
    public void theFollowingArtworkIsReturned(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        ArtworkDto returnedArtwork = modelMapper.map(row, ArtworkDto.class);
        assertNotNull(artwork);
        assertEquals(artwork.getTitle(),returnedArtwork.getTitle());
        assertEquals(artwork.getAuthor(),returnedArtwork.getAuthor());
        assertEquals(artwork.getPrice(),returnedArtwork.getPrice());
        assertEquals(artwork.getCreationDate(),returnedArtwork.getCreationDate());
        assertEquals(artwork.getDescription(), returnedArtwork.getDescription());
        assertEquals(artwork.isAvailable(),returnedArtwork.isAvailable());
        assertEquals(artwork.getStorage(),returnedArtwork.getStorage());
    }

    @Then("the artwork with id {string} shall not exist in the system")
    public void theArtworkWithIdShallNotExistInTheSystem(String arg0) {
        assertNull(artworkRepository.findById(Long.parseLong(arg0)).orElse(null));
    }
}
