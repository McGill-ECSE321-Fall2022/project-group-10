/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.ScheduleBlockRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.AdministratorResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.ScheduleBlockResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.VisitorResponseDto;
import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.model.Visitor;
import ca.mcgill.ecse321.museum.service.ScheduleBlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Api(tags = "Schedule Block")
public class ScheduleBlockRestController {

    @Autowired private ScheduleBlockService scheduleBlockService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create schedule block")
    @PostMapping(value = {"/scheduleBlock"})
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
    public ResponseEntity<ScheduleBlockResponseDto> deleteScheduleBlock(@PathVariable long id) {
        // Delete schedule block
        scheduleBlockService.deleteScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}"})
    public ResponseEntity<ScheduleBlockResponseDto> getScheduleBlock(@PathVariable long id) {
        // Get schedule block
        ScheduleBlock scheduleBlock = scheduleBlockService.getScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(
                ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all schedule blocks")
    @GetMapping(value = {"/scheduleBlock"})
    public ResponseEntity<Iterable<ScheduleBlockResponseDto>> getAllScheduleBlocks() {
        // Get all schedule blocks
        Iterable<ScheduleBlock> scheduleBlocks = scheduleBlockService.getAllScheduleBlocks();

        // Convert to response DTOs
        Iterable<ScheduleBlockResponseDto> scheduleBlockResponseDtos =
                new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos)
                    .add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(
                scheduleBlockResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get schedule block between dates")
    @GetMapping(value = {"/scheduleBlock/dates/{startDate}/{endDate}"})
    public ResponseEntity<Iterable<ScheduleBlockResponseDto>> getScheduleBlocksBetweenDates(
            @PathVariable String startDate, @PathVariable String endDate) {
        // Convert string to date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        Date startDateDate = null;
        Date endDateDate = null;

        try {
            startDateDate = new Date(dateFormatter.parse(startDate).getTime());
            endDateDate = new Date(dateFormatter.parse(endDate).getTime());
            if (endDateDate == null || startDateDate == null) {
                return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(HttpStatus.BAD_REQUEST);
        }

        // Get schedule blocks between dates
        Iterable<ScheduleBlock> scheduleBlocks =
                scheduleBlockService.getScheduleBlocksBetweenDates(startDateDate, endDateDate);

        // Convert to response DTOs
        Iterable<ScheduleBlockResponseDto> scheduleBlockResponseDtos =
                new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos)
                    .add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(
                scheduleBlockResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get visitor on schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}/visitors"})
    public ResponseEntity<Iterable<VisitorResponseDto>> getVisitorsOnScheduleBlock(
            @PathVariable long id) {
        // Get visitors for schedule block
        Iterable<Visitor> visitors = scheduleBlockService.getVisitorsOnScheduleBlock(id);

        // Convert to response DTOs
        Iterable<VisitorResponseDto> visitorResponseDtos = new ArrayList<VisitorResponseDto>();
        for (Visitor visitor : visitors) {
            ((ArrayList<VisitorResponseDto>) visitorResponseDtos)
                    .add(VisitorResponseDto.createDto(visitor));
        }

        return new ResponseEntity<Iterable<VisitorResponseDto>>(visitorResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add visitor to schedule block")
    @PostMapping(value = {"/scheduleBlock/{scheduleId}/visitors/{visitorId}"})
    public ResponseEntity<ScheduleBlockResponseDto> addVisitorToScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long visitorId) {
        // Add visitor to schedule block
        try {
            scheduleBlockService.registerVisitorOnScheduleBlock(scheduleId, visitorId);
        } catch (Exception e) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Remove visitor from schedule block")
    @DeleteMapping(value = {"/scheduleBlock/{scheduleId}/visitors/{visitorId}"})
    public ResponseEntity<ScheduleBlockResponseDto> removeVisitorFromScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long visitorId) {
        // Remove visitor from schedule block
        try {
            scheduleBlockService.unregisterVisitorOnScheduleBlock(scheduleId, visitorId);
        } catch (Exception e) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get staff on schedule block")
    @GetMapping(value = {"/scheduleBlock/{id}/staff"})
    public ResponseEntity<Iterable<AdministratorResponseDto>> getStaffOnScheduleBlock(
            @PathVariable long id) {
        // Get staff for schedule block
        Iterable<Administrator> staff = scheduleBlockService.getStaffOnScheduleBlock(id);

        // Convert to response DTOs
        Iterable<AdministratorResponseDto> staffResponseDtos =
                new ArrayList<AdministratorResponseDto>();
        for (Administrator staffMember : staff) {
            ((ArrayList<AdministratorResponseDto>) staffResponseDtos)
                    .add(AdministratorResponseDto.createDto(staffMember));
        }

        return new ResponseEntity<Iterable<AdministratorResponseDto>>(
                staffResponseDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add staff to schedule block")
    @PostMapping(value = {"/scheduleBlock/{scheduleId}/staff/{staffId}"})
    public ResponseEntity<ScheduleBlockResponseDto> addStaffToScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long staffId) {
        // Add staff to schedule block
        try {
            scheduleBlockService.registerStaffOnScheduleBlock(scheduleId, staffId);
        } catch (Exception e) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Remove staff from schedule block")
    @DeleteMapping(value = {"/scheduleBlock/{scheduleId}/staff/{staffId}"})
    public ResponseEntity<ScheduleBlockResponseDto> removeStaffFromScheduleBlock(
            @PathVariable long scheduleId, @PathVariable long staffId) {
        // Remove staff from schedule block
        try {
            scheduleBlockService.unregisterStaffOnScheduleBlock(scheduleId, staffId);
        } catch (Exception e) {
            return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }
}
