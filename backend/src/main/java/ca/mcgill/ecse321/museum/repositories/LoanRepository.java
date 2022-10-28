package ca.mcgill.ecse321.museum.repositories;

import ca.mcgill.ecse321.museum.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
