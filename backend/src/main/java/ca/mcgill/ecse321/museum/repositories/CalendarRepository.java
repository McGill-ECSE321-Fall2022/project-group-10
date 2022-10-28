package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
