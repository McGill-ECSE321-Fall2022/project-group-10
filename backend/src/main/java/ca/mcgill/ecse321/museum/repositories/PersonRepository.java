package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
