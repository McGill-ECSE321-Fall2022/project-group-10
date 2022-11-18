package ca.mcgill.ecse321.museum.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.controller.body.CreateArtworkRequestBody;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.DonationRepository;
import ca.mcgill.ecse321.museum.repository.LoanRepository;
import ca.mcgill.ecse321.museum.service.ArtworkService;

@Service
public class DonationService {
//auto generate 
@Autowired
DonationRepository donationRepository;

@Autowired
PersonRepository personRepository;

@Autowired
ArtworkRepository artworkRepository;

@Autowired
VisitorRepository visitorRepository;

@Autowired
AdministratorRepository administratorRepository;

@Autowired ArtworkService artworkService;

@Transactional
public Donation createDonation(
  //  float price,
    //LoanStatus status,
   // Date startDate,
   // Date endDate,
   // Artwork artwork,
   // Visitor customer
    //long validatorID
    long id,
    boolean validated,
    String description,
    Visitor donor
   // String title,
   // String author,
   // Date creationdate,
   // String imagelink
    //Administrator validator

){



    Donation donation = new Donation();
    donation.setDescription(description);
    donation.setDonor(donor);
    donation.setId(id);
    donation.setValidated(false);
    //donation.setArtworks(artwork);
   // donation.setValidator(validator);
    return donationRepository.save(donation);
}


@Transactional
public Donation getDonation(long id){
    return donationRepository.findById(id).orElse(null);
}

@Transactional
public List<Donation> getALLDonations() {
    return donationRepository.findAll();
}

@Transactional
//set price
public Donation validateDonation(long id, long validatorID, float price,String title,String author,String imageLink,Date creationDate, Boolean isAvailable)
{
    Donation donation = donationRepository.findById(id).orElse(null);
 //   String title=donation.getArtworks().getTitle();
   // String author=donation.getArtworks().getAuthor();
    //Date creationDate = donation.getArtworks().getCreationDate();
    String description = donation.getDescription();
    //String imageLink = donation.getArtworks().getImageLink();
    Artwork artwork = artworkService.createArtwork(title,author,creationDate,description,imageLink,price,isAvailable);
    Administrator validator = administratorRepository.findById(validatorID).orElse(null);
    if(donation == null || validator==null) return null;
    donation.setValidated(true);
    donation.setValidator(validator);
    donation.setArtworks(artwork);
    return donationRepository.save(donation);

}


@Transactional 
public void deleteDonation(Donation donation){
    donationRepository.delete(donation);
}
}