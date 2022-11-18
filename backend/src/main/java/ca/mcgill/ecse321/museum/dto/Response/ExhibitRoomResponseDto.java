/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.ExhibitRoom;

public class ExhibitRoomResponseDto extends RoomResponseDto {

    private int capacity;

    public ExhibitRoomResponseDto(long id, String name, int capacity) {
        super(id, name);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static ExhibitRoom createModel(ExhibitRoomResponseDto exhibitRoomDto) {
        ExhibitRoom exhibitRoom = new ExhibitRoom();
        exhibitRoom.setCapacity(exhibitRoomDto.capacity);
        return exhibitRoom;
    }

    public static ExhibitRoomResponseDto createDto(ExhibitRoom exhibitRoom) {
        ExhibitRoomResponseDto exhibitRoomDto =
                new ExhibitRoomResponseDto(
                        exhibitRoom.getId(), exhibitRoom.getName(), exhibitRoom.getCapacity());
        return exhibitRoomDto;
    }
}
