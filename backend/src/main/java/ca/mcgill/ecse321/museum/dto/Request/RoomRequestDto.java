/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Request;

public abstract class RoomRequestDto {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
