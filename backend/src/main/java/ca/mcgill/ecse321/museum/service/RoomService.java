/* (C)2022 */
package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.exception.ServiceLayerException;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.Room;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import ca.mcgill.ecse321.museum.repository.ArtworkRepository;
import ca.mcgill.ecse321.museum.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired RoomRepository roomRepository;
    @Autowired ArtworkRepository artworkRepository;

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

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such exhibit rooms");
        }

        room.setName(name);
        room.setCapacity(capacity);

        return roomRepository.save(room);
    }

    @Transactional
    public ExhibitRoom getExhibitRoom(long id) {
        ExhibitRoom room = (ExhibitRoom) roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such exhibit rooms");
        }

        return room;
    }

    @Transactional
    public StorageRoom getStorageRoom(long id) {
        StorageRoom room = (StorageRoom) roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such storage rooms");
        }

        return room;
    }

    @Transactional
    public StorageRoom updateStorageRoom(long id, String name) {
        StorageRoom room = (StorageRoom) roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such rooms");
        }

        room.setName(name);
        return roomRepository.save(room);
    }

    @Transactional
    public List<ExhibitRoom> getAllExhibitRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<ExhibitRoom> exhibitRooms = new ArrayList<ExhibitRoom>();
        for (Room room : rooms) {
            if (room instanceof ExhibitRoom) exhibitRooms.add((ExhibitRoom) room);
        }
        return exhibitRooms;
    }

    @Transactional
    public List<StorageRoom> getAllStorageRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<StorageRoom> storageRooms = new ArrayList<StorageRoom>();
        for (Room room : rooms) {
            if (room instanceof StorageRoom) storageRooms.add((StorageRoom) room);
        }
        return storageRooms;
    }

    @Transactional
    public void deleteRoom(long id) {
        Room room = roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such rooms");
        }

        roomRepository.delete(room);
    }

    @Transactional
    public long countArtworksInExhibitRoom(long id) {
        ExhibitRoom room = (ExhibitRoom) roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, "No such exhibit rooms");
        }

        return artworkRepository.countArtworksByStorage(room);
    }
}
