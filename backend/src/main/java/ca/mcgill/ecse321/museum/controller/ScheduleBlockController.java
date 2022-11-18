package ca.mcgill.ecse321.museum.controller;

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
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.museum.dto.Request.ScheduleBlockRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.ScheduleBlockResponseDto;
import ca.mcgill.ecse321.museum.model.ScheduleBlock;
import ca.mcgill.ecse321.museum.service.ScheduleBlockService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Schedule Block")
public class ScheduleBlockController {
    
    @Autowired
    private ScheduleBlockService scheduleBlockService;

    @PostMapping(value = {"/scheduleBlock"})
    public ResponseEntity<ScheduleBlockResponseDto> createScheduleBlock(@RequestBody ScheduleBlockRequestDto body) {
        // Check if the schedule block is valid
        if ( body == null ) { return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST); }

        // Create schedule block
        ScheduleBlock scheduleBlock = scheduleBlockService.createScheduleBlock(
            body.getStartDate(),
            body.getEndDate(),
            body.getEvent(),
            body.getVisitFees(),
            body.getVisitCapacity()
        );

        return new ResponseEntity<ScheduleBlockResponseDto>(ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.CREATED);
    }

    @PostMapping(value = {"/scheduleBlock/{id}"})
    public ResponseEntity<ScheduleBlockResponseDto> updateScheduleBlock(@RequestBody ScheduleBlockRequestDto body, @PathVariable long id) {
        // Check if the schedule block is valid
        if ( body == null ) { return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.BAD_REQUEST); }

        // Update schedule block
        ScheduleBlock scheduleBlock = scheduleBlockService.updateScheduleBlock(
            id,
            body.getStartDate(),
            body.getEndDate(),
            body.getEvent(),
            body.getVisitFees(),
            body.getVisitCapacity()
        );

        return new ResponseEntity<ScheduleBlockResponseDto>(ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/scheduleBlock/{id}"})
    public ResponseEntity<ScheduleBlockResponseDto> deleteScheduleBlock(@PathVariable long id) {
        // Delete schedule block
        scheduleBlockService.deleteScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(HttpStatus.OK);
    }

    @GetMapping(value = {"/scheduleBlock/{id}"})
    public ResponseEntity<ScheduleBlockResponseDto> getScheduleBlock(@PathVariable long id) {
        // Get schedule block
        ScheduleBlock scheduleBlock = scheduleBlockService.getScheduleBlock(id);

        return new ResponseEntity<ScheduleBlockResponseDto>(ScheduleBlockResponseDto.createDto(scheduleBlock), HttpStatus.OK);
    }

    @GetMapping(value = {"/scheduleBlock"})
    public ResponseEntity<Iterable<ScheduleBlockResponseDto>> getAllScheduleBlocks() {
        // Get all schedule blocks
        Iterable<ScheduleBlock> scheduleBlocks = scheduleBlockService.getAllScheduleBlocks();

        // Convert to response DTOs
        Iterable<ScheduleBlockResponseDto> scheduleBlockResponseDtos = new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos).add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(scheduleBlockResponseDtos, HttpStatus.OK);
    }

    @GetMapping(value = {"/scheduleBlock/dates/{startDate}/{endDate}"})
    public ResponseEntity<Iterable<ScheduleBlockResponseDto>> getScheduleBlocksBetweenDates(@PathVariable String startDate, @PathVariable String endDate) {
        // Convert string to date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        Date startDateDate = null;
        Date endDateDate = null;

        try {
            startDateDate = new Date(dateFormatter.parse(startDate).getTime());
            endDateDate = new Date(dateFormatter.parse(endDate).getTime());
            if (endDateDate == null || startDateDate == null) { return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(HttpStatus.BAD_REQUEST); }
        } catch (Exception e) {
            return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(HttpStatus.BAD_REQUEST);
        }
        
        // Get schedule blocks between dates
        Iterable<ScheduleBlock> scheduleBlocks = scheduleBlockService.getScheduleBlocksBetweenDates(startDateDate, endDateDate);

        // Convert to response DTOs
        Iterable<ScheduleBlockResponseDto> scheduleBlockResponseDtos = new ArrayList<ScheduleBlockResponseDto>();
        for (ScheduleBlock scheduleBlock : scheduleBlocks) {
            ((ArrayList<ScheduleBlockResponseDto>) scheduleBlockResponseDtos).add(ScheduleBlockResponseDto.createDto(scheduleBlock));
        }

        return new ResponseEntity<Iterable<ScheduleBlockResponseDto>>(scheduleBlockResponseDtos, HttpStatus.OK);
    }
}
