package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Room;

public abstract class RoomDto {

    private long id;
    private String name;

    public RoomDto(long id, String name) {
        this.id = id;
        this.name = name;
    } 

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
    } 

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room toModel() {
        return null;
    }
}