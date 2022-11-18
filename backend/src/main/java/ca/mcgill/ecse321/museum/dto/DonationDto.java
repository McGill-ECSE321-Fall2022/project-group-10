package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Donation;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Owner;


public class DonationDto {

    private long id;
    private String description;
    private boolean validated;
    //private String title;
    //private String author;
    //private boolean isAvailable;
    //private String imageLink;
    private VisitorDto donor;
    private AdministratorDto validator;
    private ArtworkDto artwork;

    public DonationDto (Donation donation){
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

    public long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public boolean getValidated(){
        return validated;
    }

    public void setValidated(boolean validated){
        this.validated=validated;
    }

    public void setDonor(VisitorDto donor){
        this.donor=donor;
    }

    public VisitorDto getDonor(){
        return donor;
    }

    public void setValidator(AdministratorDto validator){
        this.validator=validator;
    }

    public AdministratorDto getValidator(){
        return validator;
    }

    public void setArtwork(ArtworkDto artwork){
        this.artwork=artwork;
    }

    public ArtworkDto getArtwork() {
        return artwork;
    }
}
