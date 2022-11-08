package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 66 "../../../../..//MuseumSystem.ump"
public class Donation {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Donation Attributes
    @Id
    @GeneratedValue
    private long id;
    private boolean validated;
    private String description;

    //Donation Associations
    @ManyToOne
    private Visitor donor;
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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Visitor getDonor() {
        return donor;
    }

    public void setDonor(Visitor donor) {
        this.donor = donor;
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