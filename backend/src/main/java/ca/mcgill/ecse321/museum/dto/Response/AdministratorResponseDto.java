/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Administrator;

public class AdministratorResponseDto extends PersonResponseDto {

    public AdministratorResponseDto(
            String firstName, String lastName, String email, String password, Long id) {
        super(firstName, lastName, email, password, id);
    }

    public static AdministratorResponseDto createDto(Administrator administrator) {
        if (administrator == null) return null;

        AdministratorResponseDto administratorDto =
                new AdministratorResponseDto(
                        administrator.getFirstName(),
                        administrator.getLastName(),
                        administrator.getEmail(),
                        administrator.getPassword(),
                        administrator.getId());

        return administratorDto;
    }
}
