/* (C)2022 */
package ca.mcgill.ecse321.museum.repository;

import ca.mcgill.ecse321.museum.model.Loan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    public List<Loan> findByArtworkIdAndStatus(Long artworkId, Loan.LoanStatus status);

    public List<Loan> findByCustomerEmailAndStatus(String email, Loan.LoanStatus status);
}
