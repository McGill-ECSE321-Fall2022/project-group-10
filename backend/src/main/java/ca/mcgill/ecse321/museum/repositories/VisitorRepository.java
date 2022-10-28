package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
