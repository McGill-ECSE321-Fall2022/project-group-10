/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.ArtworkRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.ArtworkResponseDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import ca.mcgill.ecse321.museum.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ArtworkRestController {
    @Autowired private ArtworkService artworkService;
    @Autowired private RoomService roomService;

    @PostMapping(value = {"/artworks"})
    public ResponseEntity<ArtworkResponseDto> createArtwork(@RequestBody ArtworkRequestDto body) {
        var artwork = artworkService.createArtwork(
                body.getTitle(),
                body.getAuthor(),
                body.getCreationDate(),
                body.getDescription(),
                body.getImageLink(),
                body.getPrice(),
                body.isAvailable()
        );
        return new ResponseEntity<ArtworkResponseDto>(ArtworkResponseDto.createDto(artwork), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/artworks/{id}"})
    public ResponseEntity<ArtworkResponseDto> getArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        return new ResponseEntity<>(ArtworkResponseDto.createDto(artwork), HttpStatus.OK);
    }

    @GetMapping(value = {"/artworks"})
    public ResponseEntity<List<ArtworkResponseDto>> getAllArtworks() throws IllegalArgumentException {
        var artworks = artworkService.getAllArtworks();
        var artworkDtos = artworks.stream().map(artwork -> ArtworkResponseDto.createDto(artwork));
        return new ResponseEntity<List<ArtworkResponseDto>>(artworkDtos.toList(), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/artworks/{id}"})
    public ResponseEntity deleteArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        artworkService.deleteArtwork(artwork);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping(value = {"/artworks/move/{id}"})
    public ResponseEntity<ArtworkResponseDto> moveArtworkToRoom(@PathVariable long id, @RequestParam long roomId) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        var room = roomService.getRoom(roomId);

        if (artwork == null || room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (room instanceof ExhibitRoom exhibitRoom) {
            if (roomService.countArtworksInExhibitRoom(exhibitRoom) >= (exhibitRoom).getCapacity()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        artwork = artworkService.moveArtworkToRoom(artwork, room);
        return new ResponseEntity<ArtworkResponseDto>(ArtworkResponseDto.createDto(artwork), HttpStatus.OK);

    }
}
