package ca.mcgill.ecse321.museum.dto.Request;

import java.sql.Date;

public class ArtworkRequestDto {
    String title;
    String author;
    Date creationDate;
    String description;
    String imageLink;
    float price;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public float getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    boolean isAvailable;
}
