package ca.mcgill.ecse321.museum.dto;

import org.springframework.lang.NonNull;
import ca.mcgill.ecse321.museum.model.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

// line 79 "../../../../..//MuseumSystem.ump"
public class LoanDto {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Loan Attributes

    private long id;
    private float price;
    private boolean validated;
    private Date startDate;
    private Date endDate;

    //Loan Associations

    private Visitor customer;

    private Administrator validator;

    private Artwork artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Visitor getCustomer() {
        return customer;
    }

    public void setCustomer(Visitor customer) {
        this.customer = customer;
    }

    public Administrator getValidator() {
        return validator;
    }

    public void setValidator(Administrator validator) {
        this.validator = validator;
    }

    public Artwork getArtworks() {
        return artwork;
    }
}