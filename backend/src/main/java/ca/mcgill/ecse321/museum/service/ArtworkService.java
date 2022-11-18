/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import ca.mcgill.ecse321.museum.repository.StorageRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
public class ArtworkService {
    @Autowired ArtworkRepository artworkRepository;
    @Autowired RoomRepository roomRepository;
    @Autowired StorageRoomRepository storageRoomRepository;

    /**
     * @param title Title of the artwork
     * @param author Author of the artwork
     * @param creationDate Date the artwork was created
     * @param description Description of the artwork
     * @param imageLink Link to an image of the artwork
     * @param price Price to loan an artwork
     * @param isAvailable If the artwork is available to loan
     * @return A new artwork with the given parameters
     */
    @Transactional
    public Artwork createArtwork(String title,
                                 String author,
                                 Date creationDate,
                                 String description,
                                 String imageLink,
                                 float price,
                                 boolean isAvailable) {
        var artwork = new Artwork();
        artwork.setTitle(title);
        artwork.setAuthor(author);
        artwork.setCreationDate(creationDate);
        artwork.setDescription(description);
        artwork.setImageLink(imageLink);
        artwork.setPrice(price);
        artwork.setAvailable(isAvailable);
        // Placed in storage by default if it exists
        List<StorageRoom> storageRooms = storageRoomRepository.findAll();
        if (storageRooms.size() >= 1) {
            artwork.setStorage(storageRooms.get(0));
        }
        return artworkRepository.save(artwork);
    }

    /**
     * @param id Id of the artwork to get
     * @return The artwork if it is found
     */
    @Transactional public Artwork getArtwork(long id) {
        var artwork = artworkRepository.findById(id).orElse(null);
        if (artwork == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        return artwork;
    }

    /**
     * @return List of all artworks in the system
     */
    @Transactional public List<Artwork> getAllArtworks() { return artworkRepository.findAll();}

    @Transactional public void deleteArtwork(long artworkId) {
        var artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        artworkRepository.delete(artwork);
    }

    /**
     * @param artworkId Id of the artwork to be moved
     * @param roomId Id of the room where the artwork is moved
     * @return The artwork in its new room
     */
    @Transactional public Artwork moveArtworkToRoom(long artworkId, long roomId) {
        var artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        var room = roomRepository.findById(roomId).orElse(null);
        if (room == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such room");

        artwork.setStorage(room);
        return artworkRepository.save(artwork);
    }
}
