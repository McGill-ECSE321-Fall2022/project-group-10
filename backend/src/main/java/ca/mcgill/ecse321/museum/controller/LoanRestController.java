/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.museum.dto.Request.LoanRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.LoanResponseDto;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.service.LoanService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = "*")
@RestController
public class LoanRestController {
    
    @Autowired
    private LoanService loanService;

    @PostMapping(value = {"/loans"})
    public ResponseEntity<LoanResponseDto> createLoan(@RequestBody LoanRequestDto body) {
        if ( body == null ) { return new ResponseEntity<LoanResponseDto>(HttpStatus.BAD_REQUEST); }
        Loan loan = loanService.createLoan(
            body.getPrice(),
            body.getStartDate(),
            body.getEndDate(),
            body.getArtworkId(),
            body.getCustomerId()
            );
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/loans/{id}"})
    public ResponseEntity<LoanResponseDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loanService.getLoan(id)), HttpStatus.OK);
    }

    @PutMapping(value = {"/loans/request/{loanId}"})
    public ResponseEntity<LoanResponseDto> requestLoan(@PathVariable Long loanId) {
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loanService.requestLoan(loanId)), HttpStatus.OK);
    }

    @PutMapping(value = {"/loans/validate/{loanId}/{validatorId}"})
    public ResponseEntity<LoanResponseDto> validateLoan(@PathVariable Long loanId, @PathVariable Long validatorId) {
        Loan loan = loanService.validateLoan(loanId, validatorId);
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }

    @PutMapping(value = {"/loans/reject/{loanId}/{validatorId}"})
    public ResponseEntity<LoanResponseDto> rejectLoan(@PathVariable Long loanId, @PathVariable Long validatorId) {
        Loan loan = loanService.rejectLoan(loanId, validatorId);
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }
}