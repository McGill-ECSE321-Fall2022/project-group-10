/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    @Autowired LoanRepository loanRepository;

    @Autowired PersonRepository personRepository;

    @Autowired AdministratorRepository administratorRepository;

    @Autowired ArtworkRepository artworkRepository;

    @Transactional
    public Loan createLoan(
            float price, Date startDate, Date endDate, Long artworkId, Long customerId) {
        Loan loan = new Loan();
        loan.setPrice(price);
        loan.setStatus(LoanStatus.INCART);

        if (startDate == null)
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Start date cannot be null");
        loan.setStartDate(startDate);

        if (endDate == null)
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "End date cannot be null");
        loan.setEndDate(endDate);

        Visitor customer = (Visitor) personRepository.findById(customerId).orElse(null);
        if (personRepository.findById(customerId).orElse(null) == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");
        loan.setCustomer(customer);

        Artwork artwork = (Artwork) artworkRepository.findById(artworkId).orElse(null);
        if (artwork == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        loan.setArtwork(artwork);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan getLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        return loan;
    }

    @Transactional
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public List<Loan> getValidatedLoansForArtwork(Long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId).orElse(null);
        if (artwork == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such artwork");
        return loanRepository.findByArtworkIdAndStatus(artwork.getId(), LoanStatus.VALIDATED);
    }

    @Transactional
    public List<Loan> getLoansByCustomerEmailAndStatus(String customerEmail, LoanStatus status) {
        Visitor customer = (Visitor) personRepository.findByEmail(customerEmail);
        if (customer == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");
        return loanRepository.findByCustomerEmailAndStatus(customer.getEmail(), status);
    }

    @Transactional
    public Loan validateLoan(Long id, Long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        if (admin == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such admin");
        loan.setValidator(admin);
        loan.setStatus(LoanStatus.VALIDATED);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan rejectLoan(Long id, Long validatorID) {
        Loan loan = loanRepository.findById(id).orElse(null);
        Administrator admin = administratorRepository.findById(validatorID).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        if (admin == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such admin");

        loan.setValidator(admin);
        loan.setStatus(LoanStatus.DENIED);
        return loanRepository.save(loan);
    }

    @Transactional
    public List<Loan> requestAllLoanInCartByCustomer(Long customerId) {
        Visitor customer = (Visitor) personRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");
        List<Loan> loansInCart =
                loanRepository.findByCustomerEmailAndStatus(customer.getEmail(), LoanStatus.INCART);
        for (Loan loan : loansInCart) {
            loan.setStatus(LoanStatus.PENDING);
        }
        return loansInCart;
    }

    @Transactional
    public Loan requestLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");

        Artwork artwork = loan.getArtwork();
        if (artwork == null)
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "No passed artwork");

        if (!loan.getArtwork().isAvailable())
            throw new ServiceLayerException(
                    HttpStatus.BAD_REQUEST, "Artwork is not available for loan");

        List<Loan> validatedLoans =
                loanRepository.findByArtworkIdAndStatus(
                        loan.getArtwork().getId(), LoanStatus.VALIDATED);

        for (Loan validatedLoan : validatedLoans)
            if (loan.getStartDate().before(validatedLoan.getEndDate())
                    && validatedLoan.getStartDate().before(loan.getEndDate()))
                throw new ServiceLayerException(
                        HttpStatus.CONFLICT, "Loan conflicts with another validated loan");

        loan.setStatus(LoanStatus.PENDING);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan editLoan(Long id, Date startDate, Date endDate) {
        Loan loan = loanRepository.findById(id).orElse(null);

        if (startDate == null)
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Start date cannot be null");

        if (endDate == null)
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "End date cannot be null");

        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");

        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        return loanRepository.save(loan);
    }

    @Transactional
    public void deleteAllLoansInCart(long customerId) {
        Visitor customer = (Visitor) personRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");
        List<Loan> loansInCart =
                loanRepository.findByCustomerEmailAndStatus(customer.getEmail(), LoanStatus.INCART);
        for (Loan loan : loansInCart) {
            loanRepository.delete(loan);
        }
    }

    @Transactional
    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such loan");
        loanRepository.delete(loan);
    }
}
