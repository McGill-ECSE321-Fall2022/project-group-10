/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import java.sql.Date;
import javax.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
// line 116 "../../../../..//MuseumSystem.ump"
public class Artwork {

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------

    // Artwork Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull private String title = "defaultTitle";
    @NonNull private String author = "defaultAuthor";
    @NonNull private Date creationDate = new Date(0);
    @NonNull private String description = "defaultDescription";
    @Nullable // We may have artworks without links
    private String imageLink;
    private float price;
    private boolean isAvailable;

    @ManyToOne private Room storage;

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
        if (title != null) {
            this.title = title;
        } else {
            this.title = "defaultTitle";
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author != null) {
            this.author = author;
        } else {
            this.author = "defaultAuthor";
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate != null) {
            this.creationDate = creationDate;
        } else {
            this.creationDate = new Date(0);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        } else {
            this.description = "defaultDescription";
        }
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
