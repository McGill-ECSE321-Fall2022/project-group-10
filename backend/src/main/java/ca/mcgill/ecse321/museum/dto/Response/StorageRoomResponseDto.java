/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.StorageRoom;

public class StorageRoomResponseDto extends RoomResponseDto {

    public StorageRoomResponseDto(long id, String name) {
        super(id, name);
    }

    public static StorageRoom createModel(StorageRoomResponseDto storageRoomDto) {
        StorageRoom storageRoom = new StorageRoom();
        storageRoom.setId(storageRoomDto.getId());
        return storageRoom;
    }

    public static StorageRoomResponseDto createDto(StorageRoom storageRoom) {
        StorageRoomResponseDto storageRoomDto =
                new StorageRoomResponseDto(storageRoom.getId(), storageRoom.getName());
        return storageRoomDto;
    }
}
