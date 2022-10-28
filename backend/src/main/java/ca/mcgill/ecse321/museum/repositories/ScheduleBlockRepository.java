package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock, Long> {
}
