/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Request.ExhibitRoomRequestDto;
import ca.mcgill.ecse321.museum.dto.Request.StorageRoomRequestDto;
import ca.mcgill.ecse321.museum.dto.Response.ExhibitRoomResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.StorageRoomResponseDto;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "Room")
public class RoomRestController {

    @Autowired private RoomService roomService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create exhibit room")
    @PostMapping(value = {"/rooms/exhibitRoom"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ExhibitRoomResponseDto> createExhibitRoom(
            @RequestBody ExhibitRoomRequestDto body) throws IllegalArgumentException {
        ExhibitRoom exhibitRoom = roomService.createExhibitRoom(body.getName(), body.getCapacity());
        return new ResponseEntity<ExhibitRoomResponseDto>(
                ExhibitRoomResponseDto.createDto(exhibitRoom), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create storage room")
    @PostMapping(value = {"/rooms/storageRoom"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StorageRoomResponseDto> createStorageRoom(
            @RequestBody StorageRoomRequestDto body) throws IllegalArgumentException {
        StorageRoom storageRoom = roomService.createStorageRoom(body.getName());
        return new ResponseEntity<StorageRoomResponseDto>(
                StorageRoomResponseDto.createDto(storageRoom), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update exhibit room")
    @PutMapping(value = {"/rooms/exhibitRoom/{id}/{newName}/{newCapacity}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ExhibitRoomResponseDto> updateExhibitRoom(
            @PathVariable long id, @PathVariable String newName, @PathVariable int newCapacity)
            throws IllegalArgumentException {
        return new ResponseEntity<ExhibitRoomResponseDto>(
                ExhibitRoomResponseDto.createDto(
                        roomService.updateExhibitRoom(id, newName, newCapacity)),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update storage room")
    @PutMapping(value = {"/rooms/storageRoom/{id}/{newName}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StorageRoomResponseDto> updateStorageRoom(
            @PathVariable long id, @PathVariable String newName) throws IllegalArgumentException {
        return new ResponseEntity<StorageRoomResponseDto>(
                StorageRoomResponseDto.createDto(roomService.updateStorageRoom(id, newName)),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get exhibit room")
    @GetMapping(value = {"/rooms/exhibitRoom/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ExhibitRoomResponseDto> getExhibitRoom(@PathVariable long id)
            throws IllegalArgumentException {
        return new ResponseEntity<ExhibitRoomResponseDto>(
                ExhibitRoomResponseDto.createDto(roomService.getExhibitRoom(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get storage room")
    @GetMapping(value = {"/rooms/storageRoom/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<StorageRoomResponseDto> getStorageRoom(@PathVariable long id)
            throws IllegalArgumentException {
        return new ResponseEntity<StorageRoomResponseDto>(
                StorageRoomResponseDto.createDto(roomService.getStorageRoom(id)), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all exhibit rooms")
    @GetMapping(value = {"/rooms/exhibitRoom"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ExhibitRoomResponseDto>> getAllExhibitRooms()
            throws IllegalArgumentException {
        var rooms = roomService.getAllExhibitRooms();
        var ExhibitRoomResponseDtos =
                rooms.stream().map(exhibitRoom -> ExhibitRoomResponseDto.createDto(exhibitRoom));
        return new ResponseEntity<List<ExhibitRoomResponseDto>>(
                ExhibitRoomResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get all storage rooms")
    @GetMapping(value = {"/rooms/storageRoom"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<StorageRoomResponseDto>> getAllStorageRooms()
            throws IllegalArgumentException {
        var rooms = roomService.getAllStorageRooms();
        var StorageRoomResponseDtos =
                rooms.stream().map(storageRoom -> StorageRoomResponseDto.createDto(storageRoom));
        return new ResponseEntity<List<StorageRoomResponseDto>>(
                StorageRoomResponseDtos.toList(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get count of artworks in exhibit room")
    @GetMapping(value = {"/rooms/exhibitRoom/{id}/count"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getCountArtworksInExhibitRoom(@PathVariable long id)
            throws IllegalArgumentException {
        return new ResponseEntity<Long>(
                (Long) roomService.countArtworksInExhibitRoom(id), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete room")
    @DeleteMapping(value = {"/rooms/{id}"})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteRoom(@PathVariable long id)
            throws IllegalArgumentException {
        roomService.deleteRoom(id);
        return new ResponseEntity<String>("Room successfully deleted", HttpStatus.OK);
    }
}
