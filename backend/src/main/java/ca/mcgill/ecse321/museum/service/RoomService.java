package ca.mcgill.ecse321.museum.service;

import java.util.List;

import javax.transaction.Transactional;

import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ArtworkRepository artworkRepository;
    
    @Transactional
    public ExhibitRoom createExhibitRoom(String name, int capacity) {
        ExhibitRoom room = new ExhibitRoom();
        room.setName(name);
        room.setCapacity(capacity);

        return roomRepository.save(room);
    }

    @Transactional
    public StorageRoom createStorageRoom(String name) {
        StorageRoom room = new StorageRoom();
        room.setName(name);

        return roomRepository.save(room);
    }

    @Transactional
    public ExhibitRoom updateExhibitRoom(long id, String name, int capacity) {
        ExhibitRoom room = (ExhibitRoom) roomRepository.findById(id).orElse(null);

        if (room == null) { throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such exhibit rooms"); }

        room.setName(name);
        room.setCapacity(capacity);

        return roomRepository.save(room);
    }

    @Transactional
    public StorageRoom updateStorageRoom(long id, String name) {
        StorageRoom room = (StorageRoom) roomRepository.findById(id).orElse(null);

        if (room == null) { throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such rooms"); }

        room.setName(name);
        return roomRepository.save(room);
    }

    @Transactional
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room getRoom(long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    @Transactional public long countArtworksInExhibitRoom(ExhibitRoom room) {
        return artworkRepository.countArtworksByStorage(room);
    }
}
