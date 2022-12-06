/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.LoanRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.LoanResponseDto;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Person;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.repository.AdministratorRepository;
import ca.mcgill.ecse321.museum.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Loan")
public class LoanRestController {

    @Autowired private LoanService loanService;

    @Autowired private PersonRepository personRepository;
    @Autowired private AdministratorRepository administratorRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create loan")
    @PostMapping(value = {"/loans"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<LoanResponseDto> createLoan(@RequestBody LoanRequestDto body) {
        if (body == null) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.BAD_REQUEST);
        }

        // Check if the authenticated user is the borrower
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the user id of the authenticated user
        Person person = personRepository.findByEmail(authEmail);
        if (person == null || !(person instanceof Visitor)) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        Loan loan =
                loanService.createLoan(
                        body.getPrice(),
                        body.getStartDate(),
                        body.getEndDate(),
                        body.getArtworkId(),
                        person.getId());
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loan), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get loan")
    @GetMapping(value = {"/loans/{id}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoanResponseDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loanService.getLoan(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all loan")
    @GetMapping(value = {"/loans"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        var loans = loanService.getAllLoans();
        var LoanResponseDtos = loans.stream().map(loan -> LoanResponseDto.createDto(loan));
        return new ResponseEntity<List<LoanResponseDto>>(LoanResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all loan with self user id and status")
    @GetMapping(value = {"/loans/self/withStatus/{status}"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<List<LoanResponseDto>> getLoansByCustomerAndStatus(
            @PathVariable Loan.LoanStatus status) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        var loans = loanService.getLoansByCustomerEmailAndStatus(authEmail, status);
        var LoanResponseDtos = loans.stream().map(loan -> LoanResponseDto.createDto(loan));
        return new ResponseEntity<List<LoanResponseDto>>(LoanResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Request loan")
    @PutMapping(value = {"/loans/request/{loanId}"})
    public ResponseEntity<LoanResponseDto> requestLoan(@PathVariable Long loanId) {
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loanService.requestLoan(loanId)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Request all loans")
    @PutMapping(value = {"/loans/self/requestall"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<List<LoanResponseDto>> requestAllLoansInCart() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        Person person = personRepository.findByEmail(authEmail);
        if (person == null || !(person instanceof Visitor)) {
            return new ResponseEntity<List<LoanResponseDto>>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<List<LoanResponseDto>>(
                loanService.requestAllLoanInCartByCustomer(person.getId()).stream()
                        .map(loan -> LoanResponseDto.createDto(loan))
                        .toList(),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Validate loan")
    @PutMapping(value = {"/loans/validate/{loanId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LoanResponseDto> validateLoan(
            @PathVariable Long loanId) {

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

        Loan loan = loanService.validateLoan(loanId, admins.get(0).getId());
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Reject loan")
    @PutMapping(value = {"/loans/reject/{loanId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LoanResponseDto> rejectLoan(
            @PathVariable Long loanId) {

        // Check if the authenticated user is the validator
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

        Loan loan = loanService.rejectLoan(loanId, admins.get(0).getId());
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete loan")
    @DeleteMapping(value = {"/loans/{loanId}"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<LoanResponseDto> deleteLoan(@PathVariable Long loanId) {

        // Check if the authenticated user is the borrower
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the user id of the authenticated user
        Person person = personRepository.findByEmail(authEmail);
        if (person == null || !(person instanceof Visitor)) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        // Get the loan owner id
        Loan loan = loanService.getLoan(loanId);
        if (loan == null) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.NOT_FOUND);
        }

        // Check if the authenticated user is the borrower
        if (loan.getCustomer().getId() != person.getId()) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        loanService.deleteLoan(loanId);
        return new ResponseEntity<LoanResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete loan")
    @DeleteMapping(value = {"/loans/self/deleteAllInCart"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<LoanResponseDto> deleteAllLoansInCart() {

        // Check if the authenticated user is the borrower
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the user id of the authenticated user
        Person person = personRepository.findByEmail(authEmail);
        if (person == null || !(person instanceof Visitor)) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        // Get the loan owner id
        loanService.deleteAllLoansInCart(person.getId());

        return new ResponseEntity<LoanResponseDto>(HttpStatus.OK);
    }
}
