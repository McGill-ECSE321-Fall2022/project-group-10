/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.VisitorRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.VisitorResponseDto;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.VisitorRepository;
import ca.mcgill.ecse321.museum.service.VisitorService;
import io.swagger.annotations.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Visitor")
public class VisitorRestController {

    @Autowired private VisitorService visitorService;
    @Autowired private VisitorRepository visitorRepository;

    /*
     * Post - Create
     * Put - Edit, Deactivate, Reactivate
     * Get - Get, GetAll
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create Visitor")
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "Visitor successfully created"),
                @ApiResponse(code = 404, message = "No such visitor")
            })
    @PostMapping(value = {"/visitor"})
    // Anyone can create a visitor account
    public ResponseEntity<VisitorResponseDto> createVisitor(@RequestBody VisitorRequestDto body) {
        Visitor visitor =
                visitorService.createVisitor(
                        body.getFirstName(),
                        body.getLastName(),
                        body.getEmail(),
                        body.getPassword());
        return new ResponseEntity<VisitorResponseDto>(
                VisitorResponseDto.createDto(visitor), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Edit visitor")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Visitor edited"),
                @ApiResponse(code = 404, message = "No such visitor")
            })
    @PutMapping(value = {"/visitor"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<VisitorResponseDto> editVisitor(@RequestBody VisitorRequestDto body)
            throws IllegalArgumentException {

        // Get the visitor from the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the email of the authenticated user
        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the visitor from the database
        List<Visitor> visitor = visitorRepository.findByEmail(authEmail);

        // Get the visitor id
        long visitorId = visitor.get(0).getId();

        visitorService.editVisitor(
                visitorId,
                body.getFirstName(),
                body.getLastName(),
                body.getEmail(),
                body.getPassword());
        return new ResponseEntity<VisitorResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get visitor")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Visitor returned"),
                @ApiResponse(code = 404, message = "No such visitor")
            })
    @GetMapping(value = {"/visitor/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<VisitorResponseDto> getVisitor(@PathVariable long id)
            throws IllegalArgumentException {
        var visitor = visitorService.getVisitor(id);
        return new ResponseEntity<>(VisitorResponseDto.createDto(visitor), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all visitors")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Visitors returned")})
    @GetMapping(value = {"/visitor"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<VisitorResponseDto>> getAllVisitors()
            throws IllegalArgumentException {
        var visitors = visitorService.getAllVisitors();
        var VisitorResponseDtos =
                visitors.stream().map(visitor -> VisitorResponseDto.createDto(visitor));
        return new ResponseEntity<List<VisitorResponseDto>>(
                VisitorResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Deactivate visitor")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Visitor deactivated"),
                @ApiResponse(code = 404, message = "No such visitor")
            })
    @PutMapping(value = {"/visitor/deactivate/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<VisitorResponseDto> deactivateVisitor(@PathVariable long id)
            throws IllegalArgumentException {
        visitorService.deactivateVisitor(id);
        return new ResponseEntity<VisitorResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Reactivate visitor")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Visitor reactivated"),
                @ApiResponse(code = 404, message = "No such visitor")
            })
    @PutMapping(value = {"/visitor/reactivate/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<VisitorResponseDto> reactivateVisitor(@PathVariable long id)
            throws IllegalArgumentException {
        visitorService.reactivateVisitor(id);
        return new ResponseEntity<VisitorResponseDto>(HttpStatus.OK);
    }
}
