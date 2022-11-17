package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Owner;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;

import java.sql.Date;

public class LoanDto {

    private long id;
    private float price;
    private LoanStatus status;
    private Date startDate;
    private Date endDate;

    private VisitorDto customer;
    private AdministratorDto validator;
    private ArtworkDto artwork;

    public LoanDto(Loan loan) {
        this.id = loan.getId();
        this.price = loan.getPrice();
        this.status = loan.getStatus();
        this.startDate = loan.getStartDate();
        this.endDate = loan.getEndDate();
        this.customer = new VisitorDto(loan.getCustomer());


        if (loan.getValidator().getClass() == Administrator.class) {
            this.validator = new OwnerDto((Owner) loan.getValidator());
        } else {
            this.validator = new EmployeeDto((Employee) loan.getValidator());
        }
        
        this.artwork = new ArtworkDto(loan.getArtwork());
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
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public VisitorDto getCustomer() {
        return customer;
    }

    public void setCustomer(VisitorDto customer) {
        this.customer = customer;
    }

    public AdministratorDto getValidator() {
        return validator;
    }

    public void setValidator(AdministratorDto validator) {
        this.validator = validator;
    }

    public ArtworkDto getArtwork() {
        return artwork;
    }
}