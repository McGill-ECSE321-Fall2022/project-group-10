package ca.mcgill.ecse321.museum.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
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
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        return loan;
    }

    @Transactional
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public List<Loan> getValidatedLoansForArtwork(long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        return loanRepository.findByArtworkAndStatus(artwork.getId(), LoanStatus.VALIDATED);
    }

    @Transactional
    public Loan validateLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        if (admin == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such admin");

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.VALIDATED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan rejectLoan(long id, long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        if (admin == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such admin");

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.DENIED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan requestLoan(long id) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");

        List<Loan> validatedLoans = loanRepository.findByArtworkAndStatus(loan.getArtwork().getId(), LoanStatus.VALIDATED);

        for (Loan validatedLoan : validatedLoans)
            if (loan.getStartDate().before(validatedLoan.getEndDate()) && validatedLoan.getStartDate().before(loan.getEndDate()))
                throw new ServiceLayerException(HttpStatus.CONFLICT, "Loan conflicts with another validated loan");

        loan.setStatus(LoanStatus.PENDING);
        return loanRepository.save(loan);
    }

    @Transactional
    public void requestLoanForAllInCart(long customerID) {
        Visitor customer = (Visitor) personRepository.findById(customerID).orElse(null);
        if (customer == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");

        List<Loan> inCartLoans = loanRepository.findByCustomerAndStatus(customerID, LoanStatus.INCART);

        for (Loan inCartLoan : inCartLoans) {
            requestLoan(inCartLoan.getId());
        }
    }

    @Transactional
    public void editLoan(long id, Date startDate, Date endDate) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");

        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }
}
