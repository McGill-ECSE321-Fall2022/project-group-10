package ca.mcgill.ecse321.museum.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

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
    public Loan createLoan(
        float price,
        LoanStatus status,
        Date startDate,
        Date endDate,
        Artwork artwork,
        Visitor customer
        //long validatorID
    ) {
        Loan loan = new Loan();
        loan.setPrice(price);
        loan.setStatus(status);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setCustomer(customer);
        //loan.setValidator(administratorRepository.findById(validatorID).orElse(null));
        loan.setArtwork(artwork);

        return loanRepository.save(loan);
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
    public Loan validateLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null || admin == null) return null;

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.VALIDATED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan rejectLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null || admin == null) return null;

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.DENIED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan requestLoan(long id) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) return null;

        loan.setStatus(LoanStatus.PENDING);
        return loanRepository.save(loan);
    }

    @Transactional
    public void requestLoanForAllInCart(long customerID) {
        Visitor customer = (Visitor) personRepository.findById(customerID).orElse(null);
        if (customer == null) return;

        List<Loan> inCartLoans = loanRepository.findByCustomerAndStatus(customerID, LoanStatus.INCART);

        for (Loan inCartLoan : inCartLoans) {
            requestLoan(inCartLoan.getId());
        }
    }

    @Transactional
    public void editLoan(long id, Date startDate, Date endDate) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) return;

        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }
}
