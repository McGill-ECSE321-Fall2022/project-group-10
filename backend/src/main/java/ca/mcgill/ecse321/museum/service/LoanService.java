package ca.mcgill.ecse321.museum.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        long customerID,
        long validatorID
    ) {
        Loan loan = new Loan();
        loan.setPrice(price);
        loan.setStatus(status);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setCustomer((Visitor) personRepository.findById(customerID).orElse(null));
        loan.setValidator(administratorRepository.findById(validatorID).orElse(null));
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
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }
}
