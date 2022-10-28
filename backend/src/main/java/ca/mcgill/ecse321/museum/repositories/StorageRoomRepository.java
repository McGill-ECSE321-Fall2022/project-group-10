package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.StorageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRoomRepository extends JpaRepository<StorageRoom, Long> {
}
