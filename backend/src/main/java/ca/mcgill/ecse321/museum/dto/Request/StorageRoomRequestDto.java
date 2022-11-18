package ca.mcgill.ecse321.museum.dto.Request;

public class StorageRoomRequestDto extends RoomRequestDto {
    private long id;

    public StorageRoomRequestDto(long id, String name) {
        super(id, name);
    }

    public long getId() {
        return id;
    }
}