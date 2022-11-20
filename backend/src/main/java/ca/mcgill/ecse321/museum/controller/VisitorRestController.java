/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.VisitorRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.VisitorResponseDto;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.service.VisitorService;
import io.swagger.annotations.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Visitor")
public class VisitorRestController {

    @Autowired private VisitorService visitorService;

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
    @PutMapping(value = {"/visitor/edit/{id}"})
    public ResponseEntity<VisitorResponseDto> editVisitor(@RequestBody VisitorRequestDto body)
            throws IllegalArgumentException {
        visitorService.editVisitor(
                body.getId(),
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
    public ResponseEntity<VisitorResponseDto> getVisitor(@PathVariable long id)
            throws IllegalArgumentException {
        var visitor = visitorService.getVisitor(id);
        return new ResponseEntity<>(VisitorResponseDto.createDto(visitor), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all visitors")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Visitors returned")})
    @GetMapping(value = {"/visitor"})
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
    public ResponseEntity<VisitorResponseDto> reactivateVisitor(@PathVariable long id)
            throws IllegalArgumentException {
        visitorService.reactivateVisitor(id);
        return new ResponseEntity<VisitorResponseDto>(HttpStatus.OK);
    }
}
