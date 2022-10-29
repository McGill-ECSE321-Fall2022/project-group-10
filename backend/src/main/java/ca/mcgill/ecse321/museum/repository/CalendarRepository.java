package ca.mcgill.ecse321.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    
}
