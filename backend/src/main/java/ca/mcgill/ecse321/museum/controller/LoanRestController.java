/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

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

import ca.mcgill.ecse321.museum.dto.LoanDto;
import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.service.LoanService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = "*")
@RestController
public class LoanRestController {
    
    @Autowired
    private LoanService loanService;

    @PostMapping(value = {"/loans"})
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto body) {
        if ( body == null ) { return new ResponseEntity<LoanDto>(HttpStatus.BAD_REQUEST); }
        Loan loan = loanService.createLoan(
            body.getPrice(),
            body.getStatus(),
            body.getStartDate(),
            body.getEndDate(),
            body.getArtwork().toModel(),
            body.getCustomer().toModel()
        );
        return new ResponseEntity<LoanDto>(new LoanDto(loan), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/loans/{id}"})
    public ResponseEntity<LoanDto> getLoan(@PathVariable long id) {
        try {
            return new ResponseEntity<LoanDto>(new LoanDto(loanService.getLoan(id)), HttpStatus.OK);
        } catch (ServiceLayerException e) {
            return new ResponseEntity<LoanDto>(HttpStatus.OK);
        }
    }

    @PutMapping(value = {"/loans/request/{id}"})
    public ResponseEntity<LoanDto> requestLoan(@PathVariable long loanId) {
        try {
            return new ResponseEntity<LoanDto>(new LoanDto(loanService.requestLoan(loanId)), HttpStatus.OK);
        } catch (ServiceLayerException e) {
            return new ResponseEntity<LoanDto>(HttpStatus.OK);
        }
    }

    @PutMapping(value = {"/loans/validate/{id}/{validatorId}"})
    public ResponseEntity<LoanDto> validateLoan(@PathVariable long loanId, @PathVariable long validatorId) {
        try {
            Loan loan = loanService.validateLoan(loanId, validatorId);
            return new ResponseEntity<LoanDto>(new LoanDto(loan), HttpStatus.OK);
        } catch (ServiceLayerException e) {
            return new ResponseEntity<LoanDto>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = {"/loans/reject/{id}/{validatorId}"})
    public ResponseEntity<LoanDto> rejectLoan(@PathVariable long loanId, @PathVariable long validatorId) {
        try {
            Loan loan = loanService.rejectLoan(loanId, validatorId);
            return new ResponseEntity<LoanDto>(new LoanDto(loan), HttpStatus.OK);
        } catch (ServiceLayerException e) {
            return new ResponseEntity<LoanDto>(HttpStatus.NOT_FOUND);
        }
    }
}