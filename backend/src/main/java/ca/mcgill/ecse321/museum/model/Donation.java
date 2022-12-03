/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

@Entity
// line 66 "../../../../..//MuseumSystem.ump"
public class Donation {

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------

    // Donation Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean validated;

    private String description;

    // Donation Associations
    @ManyToOne private Visitor donor;
    @ManyToOne private Administrator validator;
    @ManyToOne private Artwork artwork;

    public Long getId() {
        return id;
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

    public Artwork getArtworks() {
        return artwork;
    }

    public void setArtworks(Artwork artwork) {
        this.artwork = artwork;
    }
}
