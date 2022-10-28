/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

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
    @OneToMany
    private List<Visitor> customers;
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

    public List<Visitor> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Visitor> customers) {
        this.customers = customers;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}