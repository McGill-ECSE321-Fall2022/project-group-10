/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Request;

import java.sql.Date;

public class DonationRequestDto {

    Long id;
    String description;
    boolean validated;
    // private String title;
    // private String author;
    // private boolean isAvailable;
    // private String imageLink;
    Long artworkID;

    Long price;
    String title;
    String author;
    String imageLink;
    Date creationDate;
    boolean isAvailable;

    public boolean getisAvailable() {
        return isAvailable;
    }

    public Date getDate() {
        return creationDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Long getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getValidated() {
        return validated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArtworkID() {
        return artworkID;
    }

    /*
    public void setDescription(String description){
        this.description=description;
    }



    public void setValidated(boolean validated){
        this.validated=validated;
    }

    public void setDonor(VisitorDto donor){
        this.donor=donor;
    }



    public void setValidator(AdministratorDto validator){
        this.validator=validator;
    }



    public void setArtwork(ArtworkDto artwork){
        this.artwork=artwork;
    }*/

}
