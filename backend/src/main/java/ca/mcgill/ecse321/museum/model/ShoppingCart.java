package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 107 "../../../../..//MuseumSystem.ump"
public class ShoppingCart {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //ShoppingCart Attributes
    @Id
    @GeneratedValue
    private long id;

    //ShoppingCart Associations
    @OneToOne
    private Visitor customer;
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

    public Visitor getCustomer() {
        return customer;
    }

    public void setCustomer(Visitor customer) {
        this.customer = customer;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}