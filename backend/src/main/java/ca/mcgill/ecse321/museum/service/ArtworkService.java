/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {
    @Autowired
    ArtworkRepository artworkRepository;

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
        return artworkRepository.save(artwork);
    }

    @Transactional public Artwork getArtwork(long id) { return artworkRepository.findById(id).orElse(null); }

    @Transactional public List<Artwork> getArtworks() { return artworkRepository.findAll();}
}
