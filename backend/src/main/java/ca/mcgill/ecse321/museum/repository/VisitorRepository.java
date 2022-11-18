/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.Visitor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    public List<Visitor> findByEmail(String email);
}
