package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Visitor;

public class VisitorDto extends PersonDto {

    private boolean isActive;

    public VisitorDto(Visitor visitor) {
        super(visitor);
        this.isActive = visitor.isActive();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Visitor toModel() {
        Visitor visitor = new Visitor();
        visitor.setActive(isActive);
        return visitor;
    }
}