/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.controller.body.CreateArtworkBody;
import ca.mcgill.ecse321.museum.dto.ArtworkDto;
import ca.mcgill.ecse321.museum.dto.VisitorDto;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.service.ArtworkService;
import ca.mcgill.ecse321.museum.service.VisitorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class ArtworkRestController {
    @Autowired private ArtworkService artworkService;
    @Autowired private ModelMapper modelMapper;

    private ArtworkDto convertToDto(Artwork artwork) {
        return modelMapper.map(artwork, ArtworkDto.class);
    }

    private Artwork convertToEntity(ArtworkDto artworkDto) {
        return artworkService.getArtwork(artworkDto.getId());
    }

    @PostMapping(value = {"/artworks"})
    public ArtworkDto createArtwork(@RequestBody CreateArtworkBody body) {
        var artwork = artworkService.createArtwork(
                body.getTitle(),
                body.getAuthor(),
                body.getCreationDate(),
                body.getDescription(),
                body.getImageLink(),
                body.getPrice(),
                body.isAvailable()
        );
        return convertToDto(artwork);
    }

    @GetMapping(value = {"/artworks/{id}"})
    public ArtworkDto getArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        return convertToDto(artwork);
    }

    @GetMapping(value = {"/artworks"})
    public List<ArtworkDto> getAllArtworks() throws IllegalArgumentException {
        var artworks = artworkService.getAllArtworks();
        var artworkDtos = artworks.stream().map(artwork -> convertToDto(artwork));
        return artworkDtos.toList();
    }

    @DeleteMapping(value = {"/artworks/{id}"})
    public void deleteArtwork(@PathVariable long id) throws IllegalArgumentException {
        var artwork = artworkService.getArtwork(id);
        artworkService.deleteArtwork(artwork);
    }

    @PutMapping(value = {"/artworks/{id}/storage"})
    public ArtworkDto moveArtworkToRoom(@PathVariable long id, @RequestBody Room newRoom) {
        //
        var artwork = artworkService.getArtwork(id);
        artwork = artworkService.moveArtworkToRoom(artwork, newRoom);
        return convertToDto(artwork);
    }
}
