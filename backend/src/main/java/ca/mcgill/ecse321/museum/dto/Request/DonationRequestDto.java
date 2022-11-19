package ca.mcgill.ecse321.museum.dto.Request;

public class DonationRequestDto {

    private long id;
    private String description;
    private boolean validated;
    //private String title;
    //private String author;
    //private boolean isAvailable;
    //private String imageLink;
    private Long donorID;
    private Long validatorID;
    private Long artworkID;

   /*  public DonationRequestDto (Donation donation){
        this.id=donation.getId();
        this.description=donation.getDescription();
        this.validated=donation.isValidated();
        this.donor= new VisitorDto(donation.getDonor());


        if(validated){
        if(donation.getValidator().getClass()== Administrator.class){
            this.validator = new OwnerDto((Owner)donation.getValidator());  
          } else {
            this.validator = new EmployeeDto((Employee)donation.getValidator());
            }

        this.artwork = new ArtworkDto(donation.getArtworks());
        }
        
    }
*/
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
