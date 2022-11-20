/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Visitor;

public class VisitorResponseDto extends PersonResponseDto {

    private boolean isActive;

    public VisitorResponseDto(
            boolean isActive,
            String firstName,
            String lastName,
            String email,
            String password,
            Long id) {
        super(firstName, lastName, email, password, id);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static Visitor createModel(VisitorResponseDto visitorDto) {
        if (visitorDto == null) return null;
        Visitor visitor = new Visitor();
        visitor.setActive(visitorDto.isActive);
        return visitor;
    }

    public static VisitorResponseDto createDto(Visitor visitor) {
        if (visitor == null) return null;
        VisitorResponseDto visitorDto =
                new VisitorResponseDto(
                        visitor.isActive(),
                        visitor.getFirstName(),
                        visitor.getLastName(),
                        visitor.getEmail(),
                        visitor.getPassword(),
                        visitor.getId());

        return visitorDto;
    }
}
