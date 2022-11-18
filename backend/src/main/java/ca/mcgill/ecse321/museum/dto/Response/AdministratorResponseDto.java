package ca.mcgill.ecse321.museum.dto.Response;

public abstract class AdministratorResponseDto extends PersonResponseDto {

    public AdministratorResponseDto(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}