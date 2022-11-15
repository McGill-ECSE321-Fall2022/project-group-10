package ca.mcgill.ecse321.museum.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.tool.schema.internal.StandardTableExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AdministratorRepository administratorRepository;
    
    @Autowired
    ArtworkRepository artworkRepository;

    @Transactional
    public void createLoan(
        float price,
        LoanStatus status,
        Date startDate,
        Date endDate,
        Artwork artwork,
        long customerID
        //long validatorID
    ) {
        Loan loan = new Loan();
        loan.setPrice(price);
        loan.setStatus(status);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setCustomer((Visitor) personRepository.findById(customerID).orElse(null));
        //loan.setValidator(administratorRepository.findById(validatorID).orElse(null));
        loan.setArtwork(artwork);

        loanRepository.save(loan);
    }

    @Transactional
    public Loan getLoan(long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public List<Loan> getValidatedLoansForArtwork(Artwork artwork) {
        return loanRepository.findByArtworkAndStatus(artwork.getId(), LoanStatus.VALIDATED);
    }

    @Transactional
    public void validateLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null || admin == null) return;

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.VALIDATED);
        loanRepository.save(loan);
    }

    @Transactional
    public void rejectLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null || admin == null) return;

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.DENIED);
        loanRepository.save(loan);
    }

    @Transactional
    public void requestLoan(long id) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) return;

        // check if the loan overlaps with any other validated loans
        Artwork artwork = loan.getArtwork();
        List<Loan> validatedLoans = loanRepository.findByArtworkAndStatus(artwork.getId(), LoanStatus.VALIDATED);
        for (Loan validatedLoan : validatedLoans) {
            if (loan.getStartDate().before(validatedLoan.getEndDate()) || loan.getEndDate().after(validatedLoan.getStartDate())) {
                return;
            }
        }

        loan.setStatus(LoanStatus.PENDING);
        loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }
}
