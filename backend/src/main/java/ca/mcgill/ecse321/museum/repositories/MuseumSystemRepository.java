package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.MuseumSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumSystemRepository extends JpaRepository<MuseumSystem, Long> {
}
