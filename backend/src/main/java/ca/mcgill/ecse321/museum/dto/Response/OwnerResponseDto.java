/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Owner;

public class OwnerResponseDto extends AdministratorResponseDto {

    public OwnerResponseDto(
            String firstName, String lastName, String email, String password, Long id) {
        super(firstName, lastName, email, password, id);
    }

    public static OwnerResponseDto createDto(Owner owner) {
        if (owner == null) return null;
        OwnerResponseDto ownerDto =
                new OwnerResponseDto(
                        owner.getFirstName(),
                        owner.getLastName(),
                        owner.getEmail(),
                        owner.getPassword(),
                        owner.getId());
        return ownerDto;
    }

    public static Owner createModel(OwnerResponseDto ownerDto) {
        if (ownerDto == null) return null;
        Owner owner = new Owner();
        return owner;
    }
}
