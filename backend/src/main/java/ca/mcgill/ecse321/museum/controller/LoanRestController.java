/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.LoanRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.LoanResponseDto;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Loan")
public class LoanRestController {

    @Autowired private LoanService loanService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create loan")
    @PostMapping(value = {"/loans"})
    public ResponseEntity<LoanResponseDto> createLoan(@RequestBody LoanRequestDto body) {
        if (body == null) {
            return new ResponseEntity<LoanResponseDto>(HttpStatus.BAD_REQUEST);
        }
        Loan loan =
                loanService.createLoan(
                        body.getPrice(),
                        body.getStartDate(),
                        body.getEndDate(),
                        body.getArtworkId(),
                        body.getCustomerId());
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loan), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get loan")
    @GetMapping(value = {"/loans/{id}"})
    public ResponseEntity<LoanResponseDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loanService.getLoan(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all loan")
    @GetMapping(value = {"/loans"})
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        var loans = loanService.getAllLoans();
        var LoanResponseDtos =
        loans.stream().map(loan -> LoanResponseDto.createDto(loan));
        return new ResponseEntity<List<LoanResponseDto>>(
            LoanResponseDtos.toList(),
            HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Request loan")
    @PutMapping(value = {"/loans/request/{loanId}"})
    public ResponseEntity<LoanResponseDto> requestLoan(@PathVariable Long loanId) {
        return new ResponseEntity<LoanResponseDto>(
                LoanResponseDto.createDto(loanService.requestLoan(loanId)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Validate loan")
    @PutMapping(value = {"/loans/validate/{loanId}/{validatorId}"})
    public ResponseEntity<LoanResponseDto> validateLoan(
            @PathVariable Long loanId, @PathVariable Long validatorId) {
        Loan loan = loanService.validateLoan(loanId, validatorId);
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Reject loan")
    @PutMapping(value = {"/loans/reject/{loanId}/{validatorId}"})
    public ResponseEntity<LoanResponseDto> rejectLoan(
            @PathVariable Long loanId, @PathVariable Long validatorId) {
        Loan loan = loanService.rejectLoan(loanId, validatorId);
        return new ResponseEntity<LoanResponseDto>(LoanResponseDto.createDto(loan), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete loean")
    @DeleteMapping(value = {"/loans/{loanId}"})
    public ResponseEntity<LoanResponseDto> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return new ResponseEntity<LoanResponseDto>(HttpStatus.OK);
    }
}
