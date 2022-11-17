package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Administrator;

public abstract class AdministratorDto extends PersonDto {

    public AdministratorDto(Administrator administrator) {
        super(administrator);
    }
}