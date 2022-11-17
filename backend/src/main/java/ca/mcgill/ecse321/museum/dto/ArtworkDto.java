package ca.mcgill.ecse321.museum.dto;

import java.sql.Date;

import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.ExhibitRoom;
import ca.mcgill.ecse321.museum.model.StorageRoom;

public class ArtworkDto {

    private long id;
    private String title;
    private String author;
    private Date creationDate;
    private String description;
    private String imageLink;
    private float price;
    private boolean isAvailable;

    private RoomDto storage;

    public ArtworkDto(Artwork artwork) {
        this.id = artwork.getId();
        this.title = artwork.getTitle();
        this.author = artwork.getAuthor();
        this.creationDate = artwork.getCreationDate();
        this.description = artwork.getDescription();
        this.imageLink = artwork.getImageLink();
        this.price = artwork.getPrice();    
        this.isAvailable = artwork.isAvailable();

        if (artwork.getStorage().getClass() == ExhibitRoom.class) {
            this.storage = new ExhibitRoomDto((ExhibitRoom) artwork.getStorage());
        } else {
            this.storage = new StorageRoomDto((StorageRoom) artwork.getStorage());
        }
    }

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

    public RoomDto getStorage() {
        return storage;
    }

    public void setStorage(RoomDto storage) {
        this.storage = storage;
    }

    public Artwork toModel() {
        Artwork artwork = new Artwork();
        artwork.setId(this.id);
        artwork.setTitle(this.title);
        artwork.setAuthor(this.author);
        artwork.setCreationDate(this.creationDate);
        artwork.setDescription(this.description);
        artwork.setImageLink(this.imageLink);
        artwork.setPrice(this.price);
        artwork.setAvailable(this.isAvailable);
        artwork.setStorage(this.storage.toModel());
        return artwork;
    }
}