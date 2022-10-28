package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 116 "../../../../..//MuseumSystem.ump"
public class Artwork {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Artwork Attributes
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String author;
    private Date creationDate;
    private String description;
    private String imageLink;
    private float price;
    private boolean isAvailable;

    //Artwork Associations
    @ManyToOne
    private MuseumSystem museum;
    @ManyToMany
    private List<Loan> loans;
    @ManyToMany
    private List<Donation> donations;
    @ManyToOne
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

    public MuseumSystem getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumSystem museum) {
        this.museum = museum;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public Room getStorage() {
        return storage;
    }

    public void setStorage(Room storage) {
        this.storage = storage;
    }
}