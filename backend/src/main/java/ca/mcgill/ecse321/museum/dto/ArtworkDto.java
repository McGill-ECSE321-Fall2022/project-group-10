package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Room; 
import java.sql.Date;

public class ArtworkDto {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Artwork Attributes
    private long id;
    private String title;
    private String author;
    private Date creationDate;
    private String description;
    private String imageLink;
    private float price;
    private boolean isAvailable;

    private Room storage;

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

    public Room getStorage() {
        return storage;
    }

    public void setStorage(Room storage) {
        this.storage = storage;
    }
}