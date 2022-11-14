package ca.mcgill.ecse321.museum.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    
    @Transactional
    public void createExhibitRoom(String name, int capacity) {
        ExhibitRoom room = new ExhibitRoom();
        room.setName(name);
        room.setCapacity(capacity);

        roomRepository.save(room);
    }

    @Transactional
    public void createStorageRoom(String name) {
        StorageRoom room = new StorageRoom();
        room.setName(name);

        roomRepository.save(room);
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
}
