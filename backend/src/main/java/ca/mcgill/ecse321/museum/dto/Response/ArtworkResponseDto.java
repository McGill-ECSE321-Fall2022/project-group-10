/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;
import java.sql.Date;

public class ArtworkResponseDto {

    private long id;
    private String title;
    private String author;
    private Date creationDate;
    private String description;
    private String imageLink;
    private float price;
    private boolean isAvailable;

    private RoomResponseDto storage;

    public ArtworkResponseDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public RoomResponseDto getStorage() {
        return storage;
    }

    public void setStorage(RoomResponseDto storage) {
        this.storage = storage;
    }

    public static ArtworkResponseDto createDto(Artwork artwork) {
        if (artwork == null) return null;
        ArtworkResponseDto artworkDto = new ArtworkResponseDto();
        artworkDto.setId(artwork.getId());
        artworkDto.setTitle(artwork.getTitle());
        artworkDto.setAuthor(artwork.getAuthor());
        artworkDto.setCreationDate(artwork.getCreationDate());
        artworkDto.setDescription(artwork.getDescription());
        artworkDto.setImageLink(artwork.getImageLink());
        artworkDto.setPrice(artwork.getPrice());
        artworkDto.setAvailable(artwork.isAvailable());

        if (artwork.getStorage() == null) artworkDto.setStorage(null);
        else if (artwork.getStorage().getClass() == ExhibitRoom.class)
            artworkDto.setStorage(
                    ExhibitRoomResponseDto.createDto((ExhibitRoom) artwork.getStorage()));
        else
            artworkDto.setStorage(
                    StorageRoomResponseDto.createDto((StorageRoom) artwork.getStorage()));
        return artworkDto;
    }

    public static Artwork createModel(ArtworkResponseDto artworkDto) {
        if (artworkDto == null) return null;
        Artwork artwork = new Artwork();
        artwork.setId(artworkDto.getId());
        artwork.setTitle(artworkDto.getTitle());
        artwork.setAuthor(artworkDto.getAuthor());
        artwork.setCreationDate(artworkDto.getCreationDate());
        artwork.setDescription(artworkDto.getDescription());
        artwork.setImageLink(artworkDto.getImageLink());
        artwork.setPrice(artworkDto.getPrice());
        artwork.setAvailable(artworkDto.isAvailable());
        if (artworkDto.getStorage() == null) artwork.setStorage(null);
        else if (artworkDto.getStorage().getClass() == ExhibitRoomResponseDto.class)
            artwork.setStorage(
                    ExhibitRoomResponseDto.createModel(
                            (ExhibitRoomResponseDto) artworkDto.getStorage()));
        else
            artwork.setStorage(
                    StorageRoomResponseDto.createModel(
                            (StorageRoomResponseDto) artworkDto.getStorage()));
        return artwork;
    }
}
