/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.ScheduleBlockRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.AdministratorResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.ScheduleBlockResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.VisitorResponseDto;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Person;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.repository.PersonRepository;
import ca.mcgill.ecse321.museum.service.ScheduleBlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Api(tags = "Schedule Block")
public class ScheduleBlockRestController {

    @Autowired private ScheduleBlockService scheduleBlockService;

    @Autowired private PersonRepository personRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create schedule block")
    @PostMapping(value = {"/scheduleBlock"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ScheduleBlockResponseDto> createScheduleBlock(
            @RequestBody ScheduleBlockRequestDto body) {
        // Check if the schedule block is valid
        if (body == null) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        // Create schedule block
        ScheduleBlock scheduleBlock =
                scheduleBlockService.createScheduleBlock(
                        body.getStartDate(),
                        body.getEndDate(),
                        body.getEvent(),
                        body.getVisitFees(),
                        body.getVisitCapacity());

        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update schedule block")
    @PutMapping(value = {"/scheduleBlock/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ScheduleBlockResponseDto> updateScheduleBlock(
            @RequestBody ScheduleBlockRequestDto body, @PathVariable long id) {
        // Check if the schedule block is valid
        if (body == null) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        // Update schedule block
        ScheduleBlock scheduleBlock =
                scheduleBlockService.updateScheduleBlock(
                        id,
                        body.getStartDate(),
                        body.getEndDate(),
                        body.getEvent(),
                        body.getVisitFees(),
                        body.getVisitCapacity());

        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete schedule block")
    @DeleteMapping(value = {"/scheduleBlock/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ScheduleBlockResponseDto> deleteScheduleBlock(@PathVariable long id) {
        // Delete schedule block
        scheduleBlockService.deleteScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ScheduleBlockResponseDto> getScheduleBlock(@PathVariable long id) {
        // Get schedule block
        ScheduleBlock scheduleBlock = scheduleBlockService.getScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all schedule blocks")
    @GetMapping(value = {"/scheduleBlock"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ScheduleBlockResponseDto>> getAllScheduleBlocks() {
        // Get all schedule blocks
        List<ScheduleBlock> scheduleBlocks = scheduleBlockService.getAllScheduleBlocks();

        // Convert to response DTOs
        List<ScheduleBlockResponseDto> scheduleBlockResponseDtos =
                new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos)
                    .add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<List<ScheduleBlockResponseDto>>(
                scheduleBlockResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get schedule block between dates")
    @GetMapping(value = {"/scheduleBlock/dates/{startDate}/{endDate}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ScheduleBlockResponseDto>> getScheduleBlocksBetweenDates(
            @PathVariable String startDate, @PathVariable String endDate) {
        // Convert string to date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        Date startDateDate = null;
        Date endDateDate = null;

        try {
            startDateDate = new Date(dateFormatter.parse(startDate).getTime());
            endDateDate = new Date(dateFormatter.parse(endDate).getTime());
            if (endDateDate == null || startDateDate == null) {
                return new ResponseEntity<List<ScheduleBlockResponseDto>>(
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<List<ScheduleBlockResponseDto>>(HttpStatus.BAD_REQUEST);
        }

        // Get schedule blocks between dates
        List<ScheduleBlock> scheduleBlocks =
                scheduleBlockService.getScheduleBlocksBetweenDates(startDateDate, endDateDate);

        // Convert to response DTOs
        List<ScheduleBlockResponseDto> scheduleBlockResponseDtos =
                new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos)
                    .add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<List<ScheduleBlockResponseDto>>(
                scheduleBlockResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get visitor on schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}/visitors"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<VisitorResponseDto>> getVisitorsOnScheduleBlock(
            @PathVariable long id) {
        // Get visitors for schedule block
        List<Visitor> visitors = scheduleBlockService.getVisitorsOnScheduleBlock(id);

        // Convert to response DTOs
        List<VisitorResponseDto> visitorResponseDtos = new ArrayList<VisitorResponseDto>();
        for (Visitor visitor : visitors) {
            ((ArrayList<VisitorResponseDto>) visitorResponseDtos)
                    .add(VisitorResponseDto.createDto(visitor));
        }

        return new ResponseEntity<List<VisitorResponseDto>>(visitorResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add visitor to schedule block")
    @PostMapping(value = {"/scheduleBlock/{scheduleId}/add/self"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<ScheduleBlockResponseDto> addVisitorToScheduleBlock(
            @PathVariable long scheduleId) {

        // Check if the authentication user is the same as the visitor
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the user id of the authenticated user
        Person person = personRepository.findByEmail(authEmail);

        // Add visitor to schedule block
        var scheduleBlock =
                scheduleBlockService.registerVisitorOnScheduleBlock(scheduleId, person.getId() );
        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Remove visitor from schedule block")
    @DeleteMapping(value = {"/scheduleBlock/{scheduleId}/visitors/{visitorId}"})
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<ScheduleBlockResponseDto> removeVisitorFromScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long visitorId) {

        // Check if the authentication user is the same as the visitor
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String authEmail;
        if (principal instanceof UserDetails) {
            authEmail = ((UserDetails) principal).getUsername();
        } else {
            authEmail = principal.toString();
        }

        // Get the user id of the authenticated user
        Person person = personRepository.findByEmail(authEmail);
        if (person == null || person.getId() != visitorId) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        // Check if the visitor id is the same as the authenticated user
        if (person.getId() != visitorId) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.UNAUTHORIZED);
        }

        // Remove visitor from schedule block
        var scheduleBlock =
                scheduleBlockService.unregisterVisitorOnScheduleBlock(scheduleId, visitorId);

        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get staff on schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}/staff"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<AdministratorResponseDto>> getStaffOnScheduleBlock(
            @PathVariable long id) {
        // Get staff for schedule block
        List<Administrator> staff = scheduleBlockService.getStaffOnScheduleBlock(id);

        // Convert to response DTOs
        List<AdministratorResponseDto> staffResponseDtos =
                new ArrayList<AdministratorResponseDto>();
        for (Administrator staffMember : staff) {
            ((ArrayList<AdministratorResponseDto>) staffResponseDtos)
                    .add(AdministratorResponseDto.createDto(staffMember));
        }

        return new ResponseEntity<List<AdministratorResponseDto>>(
                staffResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add staff to schedule block")
    @PostMapping(value = {"/scheduleBlock/{scheduleId}/staff/{staffId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ScheduleBlockResponseDto> addStaffToScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long staffId) {
        // Add staff to schedule block
        var scheduleBlock = scheduleBlockService.registerStaffOnScheduleBlock(scheduleId, staffId);
        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Remove staff from schedule block")
    @DeleteMapping(value = {"/scheduleBlock/{scheduleId}/staff/{staffId}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ScheduleBlockResponseDto> removeStaffFromScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long staffId) {
        var scheduleBlock =
                scheduleBlockService.unregisterStaffOnScheduleBlock(scheduleId, staffId);
        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }
}
