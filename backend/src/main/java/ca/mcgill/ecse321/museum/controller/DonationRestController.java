/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.DonationRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.DonationResponseDto;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import ca.mcgill.ecse321.museum.service.DonationService;
import io.swagger.annotations.Api;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Donation")
public class DonationRestController {
    @Autowired private DonationService donationService;
    @Autowired private VisitorRepository visitorRepository;
    @Autowired private AdministratorRepository administratorRepository;

    @PostMapping(value = {"/donations"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<DonationResponseDto> createDonation(
            @RequestBody DonationRequestDto body) {
        if (body == null) {
            return new ResponseEntity<DonationResponseDto>(HttpStatus.BAD_REQUEST);
        }

        // Check if the authenticated user is the donor
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the id of the authenticated user
        List<Visitor> visitors = visitorRepository.findByEmail(authEmail);

        Donation donation =
                donationService.createDonation(body.getDescription(), visitors.get(0).getId());
        return new ResponseEntity<DonationResponseDto>(
                DonationResponseDto.createDto(donation), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/donations/{id}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DonationResponseDto> getDonation(@PathVariable Long id) {
        return new ResponseEntity<DonationResponseDto>(
                DonationResponseDto.createDto(donationService.getDonation(id)), HttpStatus.OK);
    }

    @GetMapping(value = {"/donations"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<DonationResponseDto>> getAllDonations() {
        var donations = donationService.getAllDonations();
        var DonationResponseDtos = donations.stream().map(donation -> DonationResponseDto.createDto(donation));
        return new ResponseEntity<List<DonationResponseDto>>((DonationResponseDtos.toList()), HttpStatus.OK);
    }

    @PutMapping(value = {"/donations/validate/{donationId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<DonationResponseDto> validateDonation(
            @PathVariable Long donationId,
            @RequestBody DonationRequestDto body) {

        float price = body.getPrice();
        String title = body.getTitle();
        String author = body.getAuthor();
        String imageLink = body.getImageLink();
        Date creationDate = body.getDate();
        Boolean isAvailable = body.getisAvailable();

        // Check if the authenticated user is the donor
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the id of the authenticated user
        List<Administrator> admins = administratorRepository.findByEmail(authEmail);

        Donation donation =
                donationService.validateDonation(
                        donationId,
                        admins.get(0).getId(),
                        price,
                        title,
                        author,
                        imageLink,
                        creationDate,
                        isAvailable);
        return new ResponseEntity<DonationResponseDto>(
                DonationResponseDto.createDto(donation), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/donations/{donationId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<DonationResponseDto> deleteDonation(@PathVariable Long donationId) {
        donationService.deleteDonation(donationId);
        return new ResponseEntity<DonationResponseDto>(HttpStatus.OK);
    }
}
