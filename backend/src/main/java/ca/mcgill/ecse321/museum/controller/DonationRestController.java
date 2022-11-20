package ca.mcgill.ecse321.museum.controller;

import java.sql.Date;

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
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    public ResponseEntity<DonationResponseDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<DonationResponseDto>(
                DonationResponseDto.createDto(donationService.getDonation(id)), HttpStatus.OK);
    }

    @PutMapping(value = {"/donations/validate/{donationId}/{validatorId}"})
    public ResponseEntity<DonationResponseDto> validateDonation(
            @PathVariable Long loanId, @PathVariable Long validatorId,@PathVariable float price,@PathVariable String title,@PathVariable String author,@PathVariable String imageLink,@PathVariable Date creationDate, @PathVariable boolean isAvailable) {
        Donation donation = donationService.validateDonation(loanId, validatorId, price, title, author, imageLink, creationDate, isAvailable);
        return new ResponseEntity<DonationResponseDto>(DonationResponseDto.createDto(donation), HttpStatus.OK);
    }

    
    

}