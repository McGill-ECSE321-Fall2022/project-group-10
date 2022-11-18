package ca.mcgill.ecse321.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    public List<Administrator> findByEmail(String email);
}
