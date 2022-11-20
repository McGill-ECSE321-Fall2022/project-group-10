package ca.mcgill.ecse321.museum.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.museum.service.DonationService;
import io.swagger.annotations.Api;
import ca.mcgill.ecse321.museum.dto.Request.DonationRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.DonationResponseDto;
import ca.mcgill.ecse321.museum.model.Donation;


@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Donation")
public class DonationRestController {
    @Autowired
    private DonationService donationService;

    @PostMapping(value = {"/donations"})
    public ResponseEntity<DonationResponseDto> createDonation(@RequestBody DonationRequestDto body){
        if (body == null) { return new ResponseEntity<DonationResponseDto>(HttpStatus.BAD_REQUEST); };
        Donation donation = donationService.createDonation(
            body.getDescription(),
            body.getDonorID()
        );
        return new ResponseEntity<DonationResponseDto>(
            DonationResponseDto.createDto(donation), HttpStatus.CREATED);
    }
    
    @GetMapping(value = {"/donations/{id}"})
    public ResponseEntity<DonationResponseDto> getDonation(@PathVariable Long id) {
        return new ResponseEntity<DonationResponseDto>(
                DonationResponseDto.createDto(donationService.getDonation(id)), HttpStatus.OK);
    }

    @PutMapping(value = {"/donations/validate/{donationId}/{validatorId}"})
    public ResponseEntity<DonationResponseDto> validateDonation(
            @PathVariable Long donationId, @PathVariable Long validatorId,@RequestBody DonationRequestDto body) {

        float price = body.getPrice();
        String title = body.getTitle();
        String author = body.getAuthor();
        String imageLink = body.getimageLink();
        Date creationDate = body.getDate();
        Boolean isAvailable = body.getisAvailable();

        Donation donation = donationService.validateDonation(donationId, validatorId, price, title, author, imageLink, creationDate, isAvailable);
        return new ResponseEntity<DonationResponseDto>(DonationResponseDto.createDto(donation), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/donations/{donationId}"})
    public ResponseEntity<DonationResponseDto> deleteDonation(@PathVariable Long donationId) {
        donationService.deleteDonation(donationId);
        return new ResponseEntity<DonationResponseDto>(HttpStatus.OK);
    }



    
    

}