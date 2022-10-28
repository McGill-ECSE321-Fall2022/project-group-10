package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 79 "../../../../..//MuseumSystem.ump"
public class Loan {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Loan Attributes
    @Id
    @GeneratedValue
    private long id;
    private float price;
    private boolean validated;
    private Date startDate;
    private Date endDate;

    //Loan Associations
    @ManyToOne
    private MuseumSystem museum;
    @ManyToOne
    private Visitor customer;
    @ManyToOne
    private Administrator validator;
    @ManyToMany
    private List<Artwork> artworks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MuseumSystem getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumSystem museum) {
        this.museum = museum;
    }

    public Visitor getCustomer() {
        return customer;
    }

    public void setCustomer(Visitor customer) {
        this.customer = customer;
    }

    public Administrator getValidator() {
        return validator;
    }

    public void setValidator(Administrator validator) {
        this.validator = validator;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}