/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import java.sql.Date;
import javax.persistence.*;
import org.springframework.lang.NonNull;

@Entity
// line 79 "../../../../..//MuseumSystem.ump"
public class Loan {

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------

    // Loan Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float price;
    private LoanStatus status;
    @NonNull private Date startDate = new Date(0);
    @NonNull private Date endDate = new Date(0);

    // Loan Associations
    @ManyToOne private Visitor customer;
    @ManyToOne private Administrator validator;
    @ManyToOne private Artwork artwork;

    public enum LoanStatus {
        INCART,
        PENDING,
        VALIDATED,
        DENIED
    }

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

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        } else {
            this.startDate = new Date(0);
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        } else {
            this.endDate = new Date(0);
        }
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

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }
}
