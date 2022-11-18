package ca.mcgill.ecse321.museum.dto.Request;

import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;

import java.sql.Date;

public class LoanRequestDto {

    private Long id;
    private float price;
    private LoanStatus status;
    private Date startDate;
    private Date endDate;

    private Long customerId;
    private Long validatorId;
    private Long artworkId;

    public Long getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getValidatorId() {
        return validatorId;
    }

    public Long getArtworkId() {
        return artworkId;
    }
}