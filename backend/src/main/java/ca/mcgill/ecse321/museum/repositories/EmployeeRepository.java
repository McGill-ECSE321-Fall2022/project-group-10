package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
