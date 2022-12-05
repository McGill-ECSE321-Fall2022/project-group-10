/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.ArtworkRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.ArtworkResponseDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import io.swagger.annotations.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Artwork")
public class ArtworkRestController {

    @Autowired private ArtworkService artworkService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create artwork")
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "Artwork successfully created"),
                @ApiResponse(code = 404, message = "No such artwork")
            })
    @PostMapping(value = {"/artworks"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ArtworkResponseDto> createArtwork(@RequestBody ArtworkRequestDto body) {
        Artwork artwork =
                artworkService.createArtwork(
                        body.getTitle(),
                        body.getAuthor(),
                        body.getCreationDate(),
                        body.getDescription(),
                        body.getImageLink(),
                        body.getPrice(),
                        body.isAvailable());
        return new ResponseEntity<ArtworkResponseDto>(
                ArtworkResponseDto.createDto(artwork), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get artwork")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Artwork returned"),
                @ApiResponse(code = 404, message = "No such artwork")
            })
    @GetMapping(value = {"/artworks/{artworkId}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ArtworkResponseDto> getArtwork(@PathVariable long artworkId)
            throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(artworkId);
        return new ResponseEntity<>(ArtworkResponseDto.createDto(artwork), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all artworks")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Artworks returned"),
                @ApiResponse(code = 404, message = "No such artwork")
            })
    @GetMapping(value = {"/artworks"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ArtworkResponseDto>> getAllArtworks()
            throws IllegalArgumentException {
        var artworks = artworkService.getAllArtworks();
        var ArtworkResponseDtos =
                artworks.stream().map(artwork -> ArtworkResponseDto.createDto(artwork));
        return new ResponseEntity<List<ArtworkResponseDto>>(
                ArtworkResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete artwork")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Artwork deleted"),
                @ApiResponse(code = 404, message = "No such artwork")
            })
    @DeleteMapping(value = {"/artworks/{artworkId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteArtwork(@PathVariable long artworkId)
            throws IllegalArgumentException {
        artworkService.deleteArtwork(artworkId);
        return new ResponseEntity<String>("Artwork successfully deleted", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Move artwork to room")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Artwork moved"),
                @ApiResponse(code = 404, message = "No such artwork/room"),
                @ApiResponse(code = 403, message = "The room is full")
            })
    @PutMapping(value = {"/artworks/move/{artworkId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ArtworkResponseDto> moveArtworkToRoom(
            @PathVariable long artworkId, @RequestParam long roomId)
            throws IllegalArgumentException {
        var artwork = artworkService.moveArtworkToRoom(artworkId, roomId);
        return new ResponseEntity<ArtworkResponseDto>(
                ArtworkResponseDto.createDto(artwork), HttpStatus.OK);
    }
}
