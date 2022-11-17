package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.StorageRoom;

public class StorageRoomDto extends RoomDto {
    private long id;

    public StorageRoomDto(StorageRoom storageRoom) {
        super(storageRoom);
        this.id = storageRoom.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StorageRoom toModel() {
        StorageRoom storageRoom = new StorageRoom();
        storageRoom.setId(id);
        return storageRoom;
    }
}