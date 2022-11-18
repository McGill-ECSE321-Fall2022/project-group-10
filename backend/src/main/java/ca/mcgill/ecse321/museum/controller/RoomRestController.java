/* (C)2022 */
package ca.mcgill.ecse321.museum.controller;

import ca.mcgill.ecse321.museum.dto.Response.ExhibitRoomResponseDto;
import ca.mcgill.ecse321.museum.dto.Response.StorageRoomResponseDto;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Room")
public class RoomRestController {

    @Autowired private RoomService roomService;

    @PostMapping(value = {"/rooms/exhibitRoom"})
    public ResponseEntity<ExhibitRoomResponseDto> createExhibitRoom(
            @RequestBody ExhibitRoomResponseDto body) {
        ExhibitRoom exhibitRoom = roomService.createExhibitRoom(body.getName(), body.getCapacity());
        return new ResponseEntity<ExhibitRoomResponseDto>(
                ExhibitRoomResponseDto.createDto(exhibitRoom), HttpStatus.CREATED);
    }

    @PostMapping(value = {"/rooms/storageRoom"})
    public ResponseEntity<StorageRoomResponseDto> createStorageRoom(
            @RequestBody StorageRoomResponseDto body) {
        StorageRoom storageRoom = roomService.createStorageRoom(body.getName());
        return new ResponseEntity<StorageRoomResponseDto>(
                StorageRoomResponseDto.createDto(storageRoom), HttpStatus.CREATED);
    }

    @PutMapping(value = {"/rooms/exhibitRoom/{id}/{newName}/{newCapacity}"})
    public ResponseEntity<ExhibitRoomResponseDto> updateExhibitRoom(
            @PathVariable long id, @PathVariable String newName, @PathVariable int newCapacity) {
        return new ResponseEntity<ExhibitRoomResponseDto>(
                ExhibitRoomResponseDto.createDto(
                        roomService.updateExhibitRoom(id, newName, newCapacity)),
                HttpStatus.OK);
    }

    @PutMapping(value = {"/rooms/storageRoom/{id}/{newName}"})
    public ResponseEntity<StorageRoomResponseDto> updateStorageRoom(
            @PathVariable long id, @PathVariable String newName) {
        return new ResponseEntity<StorageRoomResponseDto>(
                StorageRoomResponseDto.createDto(roomService.updateStorageRoom(id, newName)),
                HttpStatus.OK);
    }
}