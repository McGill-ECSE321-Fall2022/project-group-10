package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
