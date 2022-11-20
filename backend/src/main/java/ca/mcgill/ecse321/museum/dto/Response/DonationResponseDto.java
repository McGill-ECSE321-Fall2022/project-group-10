/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Donation;

public class DonationResponseDto {

    private Long id;
    private String description;
    private boolean validated;

    private VisitorResponseDto donor;
    private AdministratorResponseDto validator;
    private ArtworkResponseDto artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public VisitorResponseDto getDonor() {
        return donor;
    }

    public void setDonor(VisitorResponseDto donor) {
        this.donor = donor;
    }

    public AdministratorResponseDto getValidator() {
        return validator;
    }

    public void setValidator(AdministratorResponseDto validator) {
        this.validator = validator;
    }

    public ArtworkResponseDto getArtwork() {
        return artwork;
    }

    public static DonationResponseDto createDto(Donation donation) {

        if (donation == null) return null;

        DonationResponseDto donationDto = new DonationResponseDto();
        donationDto.id = donation.getId();
        donationDto.description = donation.getDescription();
        donationDto.validated = donation.isValidated();
        donationDto.donor = VisitorResponseDto.createDto(donation.getDonor());
        donationDto.id = donation.getId();
        if (donation.isValidated()) {
            if (donation.getValidator() == null) donationDto.validator = null;
            else if (donation.getValidator().getClass() == Administrator.class)
                donationDto.validator =
                        OwnerResponseDto.createDto(donation.getValidator()); // remove case to owner
            else donationDto.validator = EmployeeResponseDto.createDto(donation.getValidator());
        }
        if (donation.isValidated())
            donationDto.artwork = ArtworkResponseDto.createDto(donation.getArtworks());
        else donationDto.artwork = null;
        return donationDto;
    }

    public static Donation createModel(DonationResponseDto donationDto) {
        if (donationDto == null) return null;

        Donation donation = new Donation();
        donation.setId(donationDto.id);
        donation.setDescription(donationDto.description);
        donation.setValidated(donationDto.validated);
        donation.setDonor(VisitorResponseDto.createModel(donationDto.donor));

        if (donationDto.validator.getClass() == OwnerResponseDto.class)
            donation.setValidator(
                    OwnerResponseDto.createModel((OwnerResponseDto) donationDto.validator));
        else
            donation.setValidator(
                    EmployeeResponseDto.createModel((EmployeeResponseDto) donationDto.validator));

        if (donation.isValidated())
            donation.setArtworks(ArtworkResponseDto.createModel(donationDto.artwork));
        else donation.setArtworks(null);
        return donation;
    }
}
