package ca.mcgill.ecse321.museum.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.museum.service.DonationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ca.mcgill.ecse321.museum.dto.DonationDto;
import ca.mcgill.ecse321.museum.model.Donation;


@CrossOrigin(origins = "*")
@RestController

public class DonationRestController {
    @Autowired
    private DonationService donationService;

    @PostMapping(value = {"/donations"})
    public ResponseEntity<DonationDto> createDonation(@RequestBody DonationDto body){
        if (body == null) { return new ResponseEntity<DonationDto>(HttpStatus.BAD_REQUEST); };
        Donation donation = donationService.createDonation(
            body.getId(),
            body.getValidated(),
            body.getDescription(),
            body.getDonor().toModel()
        );
        return new ResponseEntity<DonationDto>(new DonationDto(donation), HttpStatus.CREATED); 
    }
    
    @GetMapping(value = {"/Donations/{id}"})
    public ResponseEntity<DonationDto> getDonation(@PathVariable long id){
        Donation donation = donationService.getDonation(id);
        if (donation == null) { return new ResponseEntity<DonationDto>(HttpStatus.NOT_FOUND); };
        return new ResponseEntity<DonationDto>(new DonationDto(donation),HttpStatus.OK);
        
    }

    @PutMapping(value = {"/loans/validate/{id}"})
    public ResponseEntity<DonationDto> validateLoan(@PathVariable long donationId,@PathVariable long validatorID,@PathVariable float price,@PathVariable String title,@PathVariable String author,@PathVariable String imageLink,@PathVariable Date creationDate,@PathVariable Boolean isAvailable) {
        Donation donation = donationService.validateDonation(donationId, validatorID, price, title, author, imageLink, creationDate, isAvailable );


        if (donation == null) { return new ResponseEntity<DonationDto>(HttpStatus.NOT_FOUND); };
        return new ResponseEntity<DonationDto>(new DonationDto(donation), HttpStatus.OK);


    }

    
    

}