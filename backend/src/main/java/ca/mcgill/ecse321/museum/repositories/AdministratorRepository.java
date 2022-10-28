package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
