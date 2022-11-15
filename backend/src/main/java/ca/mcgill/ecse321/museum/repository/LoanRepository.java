package ca.mcgill.ecse321.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.mcgill.ecse321.museum.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    public List<Loan> findByArtworkAndStatus(Long artworkId, Loan.LoanStatus status);
}
