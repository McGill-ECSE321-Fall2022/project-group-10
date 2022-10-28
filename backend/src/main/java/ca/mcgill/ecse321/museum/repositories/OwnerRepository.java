package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
