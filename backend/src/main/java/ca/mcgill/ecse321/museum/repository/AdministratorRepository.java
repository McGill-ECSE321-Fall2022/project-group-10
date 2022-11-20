/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.Administrator;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    public List<Administrator> findByEmail(String email);
}
