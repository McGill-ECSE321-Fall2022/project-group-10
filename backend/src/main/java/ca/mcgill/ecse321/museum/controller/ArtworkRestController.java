/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.controller.body.CreateArtworkRequestBody;
import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import ca.mcgill.ecse321.museum.service.RoomService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Artwork")
public class ArtworkRestController {
    @Autowired private ArtworkService artworkService;
    @Autowired private RoomService roomService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create artwork")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Artwork successfully created"),
            @ApiResponse(code = 404, message = "No such artwork")
            }
    )
    @PostMapping(value = {"/artworks"})
    public ResponseEntity<ArtworkDto> createArtwork(@RequestBody CreateArtworkRequestBody body) {
        try {

            var artwork = artworkService.createArtwork(
                    body.getTitle(),
                    body.getAuthor(),
                    body.getCreationDate(),
                    body.getDescription(),
                    body.getImageLink(),
                    body.getPrice(),
                    body.isAvailable()
            );
            return new ResponseEntity<ArtworkDto>(new ArtworkDto(artwork), HttpStatus.CREATED);
        } catch(ServiceLayerException e) {
            return new ResponseEntity(e.getStatus());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get artwork")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Artwork returned"),
            @ApiResponse(code = 404, message = "No such artwork")
    }
    )
    @GetMapping(value = {"/artworks/{id}"})
    public ResponseEntity<ArtworkDto> getArtwork(@PathVariable long id) throws IllegalArgumentException {
        try {
            var artwork = artworkService.getArtwork(id);
            return new ResponseEntity<>(new ArtworkDto(artwork), HttpStatus.OK);
        } catch(ServiceLayerException e) {
            return new ResponseEntity(e.getStatus());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all artworks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Artworks returned"),
            @ApiResponse(code = 404, message = "No such artwork")
    }
    )
    @GetMapping(value = {"/artworks"})
    public ResponseEntity<List<ArtworkDto>> getAllArtworks() throws IllegalArgumentException {
        try {
            var artworks = artworkService.getAllArtworks();
            var artworkDtos = artworks.stream().map(artwork -> new ArtworkDto(artwork));
            return new ResponseEntity<List<ArtworkDto>>(artworkDtos.toList(), HttpStatus.OK);
        } catch(ServiceLayerException e) {
            return new ResponseEntity(e.getStatus());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete artwork")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Artwork deleted"),
            @ApiResponse(code = 404, message = "No such artwork")
    }
    )
    @DeleteMapping(value = {"/artworks/{id}"})
    public ResponseEntity deleteArtwork(@PathVariable long id) throws IllegalArgumentException {
        try {
            artworkService.deleteArtwork(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch(ServiceLayerException e) {
            return new ResponseEntity(e.getStatus());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Move artwork to room")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Artwork moved"),
            @ApiResponse(code = 404, message = "No such artwork/room")
    }
    )
    @PutMapping(value = {"/artworks/move/{id}"})
    public ResponseEntity<ArtworkDto> moveArtworkToRoom(@PathVariable long id, @RequestParam long roomId) throws IllegalArgumentException {
        try {
            var artwork = artworkService.moveArtworkToRoom(id, roomId);
            return new ResponseEntity<ArtworkDto>(new ArtworkDto(artwork), HttpStatus.OK);
        } catch(ServiceLayerException e) {
            return new ResponseEntity(e.getStatus());
        }


    }
}
