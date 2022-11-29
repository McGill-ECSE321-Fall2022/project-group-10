package ca.mcgill.ecse321.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    public Person findByEmail(String email);
    
}
