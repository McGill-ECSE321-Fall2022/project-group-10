package ca.mcgill.ecse321.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.MuseumSystem;

public interface MuseumSystemRepository extends JpaRepository<MuseumSystem, Long> {
    List<MuseumSystem> findByName(String name); // Find a museum system by name
}
