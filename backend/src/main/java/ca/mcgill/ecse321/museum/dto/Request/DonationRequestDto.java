package ca.mcgill.ecse321.museum.dto.Request;

public class DonationRequestDto {

    Long id;
    String description;
    boolean validated;
    //private String title;
    //private String author;
    //private boolean isAvailable;
    //private String imageLink;
    Long donorID;
    Long validatorID;
    Long artworkID;

    public DonationRequestDto(Long id, String description, boolean validated, Long donorID, Long validatorID, Long artworkID){

        this.id=id;
        this.description=description;
        this.validated=validated;
        this.donorID=donorID;
        this.validatorID=validatorID;
        this.artworkID=artworkID;

    }

    public Long getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public boolean getValidated(){
        return validated;
    }

    public Long getDonorID(){
        return donorID;
    }

    public Long getValidatorID(){
        return validatorID;
    }

    public void setId(Long id){
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
