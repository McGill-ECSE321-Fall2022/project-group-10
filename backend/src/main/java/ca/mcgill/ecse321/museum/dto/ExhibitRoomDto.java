package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.ExhibitRoom;

public class ExhibitRoomDto extends RoomDto {

    private int capacity;

    public ExhibitRoomDto(long id, String name, int capacity) {
        super(id, name);
        this.capacity = capacity;
    }

    public ExhibitRoomDto(ExhibitRoom exhibitRoom) {
        super(exhibitRoom);
        this.capacity = exhibitRoom.getCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ExhibitRoom toModel() {
        ExhibitRoom exhibitRoom = new ExhibitRoom();
        exhibitRoom.setCapacity(capacity);
        return exhibitRoom;
    }
}