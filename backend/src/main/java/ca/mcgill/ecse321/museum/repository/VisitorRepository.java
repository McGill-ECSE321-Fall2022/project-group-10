package ca.mcgill.ecse321.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    public List<Visitor> findByEmail(String email);
}
