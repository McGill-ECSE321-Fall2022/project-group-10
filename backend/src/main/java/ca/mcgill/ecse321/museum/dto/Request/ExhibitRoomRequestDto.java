package ca.mcgill.ecse321.museum.dto.Request;

public class ExhibitRoomRequestDto extends RoomRequestDto {

    private int capacity;

    public ExhibitRoomRequestDto(long id, String name, int capacity) {
        super(id, name);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}