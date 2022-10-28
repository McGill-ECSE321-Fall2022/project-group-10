package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitRoomRepository extends JpaRepository<ExhibitRoom, Long> {
}
