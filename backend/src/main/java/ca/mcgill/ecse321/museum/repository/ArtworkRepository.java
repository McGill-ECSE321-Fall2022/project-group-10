package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Artwork;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    public long countArtworksByStorage(Room room);
}
