/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Request;

import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import java.sql.Date;

public class LoanRequestDto {

    Long id;
    float price;
    LoanStatus status;
    Date startDate;
    Date endDate;

    Long validatorId;
    Long artworkId;

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

    public Long getValidatorId() {
        return validatorId;
    }

    public Long getArtworkId() {
        return artworkId;
    }
}
