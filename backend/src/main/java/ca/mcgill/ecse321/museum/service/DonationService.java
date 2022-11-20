/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.DonationRepository;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    // auto generate
    @Autowired DonationRepository donationRepository;

    @Autowired PersonRepository personRepository;

    @Autowired ArtworkRepository artworkRepository;

    @Autowired VisitorRepository visitorRepository;

    @Autowired AdministratorRepository administratorRepository;

    @Autowired ArtworkService artworkService;

    @Transactional
    public Donation createDonation(String description, Long donorID) {
        Donation donation = new Donation();
        donation.setValidated(false);
        donation.setDescription(description);

        Visitor donor = (Visitor) personRepository.findById(donorID).orElse(null);
        if (personRepository.findById(donorID).orElse(null) == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such customer");
        donation.setDonor(donor);
        return donationRepository.save(donation);
    }

    @Transactional
    public Donation getDonation(long id) {
        Donation donation = donationRepository.findById(id).orElse(null);
        if (donation == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such donation");
        return donation;
    }

    @Transactional
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Transactional
    // set price
    public Donation validateDonation(
            long id,
            long validatorID,
            float price,
            String title,
            String author,
            String imageLink,
            Date creationDate,
            Boolean isAvailable) {
        Donation donation = donationRepository.findById(id).orElse(null);
        if (donation == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such donation");
        String description = donation.getDescription();
        Artwork artwork =
                artworkService.createArtwork(
                        title, author, creationDate, description, imageLink, price, isAvailable);
        Administrator validator = administratorRepository.findById(validatorID).orElse(null);
        if (validator == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such validator");
        donation.setValidated(true);
        donation.setValidator(validator);
        donation.setArtworks(artwork);
        return donationRepository.save(donation);
    }

    @Transactional
    public void deleteDonation(Long donationId) {
        Donation donation = donationRepository.findById(donationId).orElse(null);
        if (donation == null)
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such donation");
        donationRepository.delete(donation);
    }
}
