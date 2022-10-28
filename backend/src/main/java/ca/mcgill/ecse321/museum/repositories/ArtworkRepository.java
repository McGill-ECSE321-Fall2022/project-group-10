package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
}
