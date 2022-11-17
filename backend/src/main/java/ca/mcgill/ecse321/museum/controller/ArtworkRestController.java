/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.controller.body.CreateArtworkRequestBody;
import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import ca.mcgill.ecse321.museum.service.RoomService;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<ArtworkDto> createArtwork(@RequestBody CreateArtworkRequestBody body) {
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
    }

    @GetMapping(value = {"/artworks/{id}"})
    public ResponseEntity<ArtworkDto> getArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        return new ResponseEntity<>(new ArtworkDto(artwork), HttpStatus.OK);
    }

    @GetMapping(value = {"/artworks"})
    public ResponseEntity<List<ArtworkDto>> getAllArtworks() throws IllegalArgumentException {
        var artworks = artworkService.getAllArtworks();
        var artworkDtos = artworks.stream().map(artwork -> new ArtworkDto(artwork));
        return new ResponseEntity<List<ArtworkDto>>(artworkDtos.toList(), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/artworks/{id}"})
    public ResponseEntity deleteArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        artworkService.deleteArtwork(artwork);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping(value = {"/artworks/move/{id}"})
    public ResponseEntity<ArtworkDto> moveArtworkToRoom(@PathVariable long id, @RequestParam long roomId) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        var room = roomService.getRoom(roomId);

        if (artwork == null || room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (room instanceof ExhibitRoom exhibitRoom) {
            if (roomService.countArtworksInExhibitRoom(exhibitRoom) >= (exhibitRoom).getCapacity()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        artwork = artworkService.moveArtworkToRoom(artwork, room);
        return new ResponseEntity<ArtworkDto>(new ArtworkDto(artwork), HttpStatus.OK);

    }
}
