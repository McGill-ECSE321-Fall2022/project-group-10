/* (C)2022 */
package ca.mcgill.ecse321.museum.dto.Response;

import ca.mcgill.ecse321.museum.model.Administrator;
import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Loan.LoanStatus;
import ca.mcgill.ecse321.museum.model.Owner;
import java.sql.Date;

public class LoanResponseDto {

    private long id;
    private float price;
    private LoanStatus status;
    private Date startDate;
    private Date endDate;

    private VisitorResponseDto customer;
    private AdministratorResponseDto validator;
    private ArtworkResponseDto artwork;

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

    public VisitorResponseDto getCustomer() {
        return customer;
    }

    public void setCustomer(VisitorResponseDto customer) {
        this.customer = customer;
    }

    public AdministratorResponseDto getValidator() {
        return validator;
    }

    public void setValidator(AdministratorResponseDto validator) {
        this.validator = validator;
    }

    public ArtworkResponseDto getArtwork() {
        return artwork;
    }

    public static LoanResponseDto createDto(Loan loan) {
        if (loan == null) return null;

        LoanResponseDto loanDto = new LoanResponseDto();
        loanDto.id = loan.getId();
        loanDto.price = loan.getPrice();
        loanDto.status = loan.getStatus();
        loanDto.startDate = loan.getStartDate();
        loanDto.endDate = loan.getEndDate();
        loanDto.customer = VisitorResponseDto.createDto(loan.getCustomer());

        if (loan.getValidator() == null) loanDto.validator = null;
        else if (loan.getValidator().getClass() == Owner.class)
            loanDto.validator = OwnerResponseDto.createDto((Owner) loan.getValidator());
        else loanDto.validator = EmployeeResponseDto.createDto((Employee) loan.getValidator());

        loanDto.artwork = ArtworkResponseDto.createDto(loan.getArtwork());
        return loanDto;
    }

    public static Loan createModel(LoanResponseDto loanDto) {
        if (loanDto == null) return null;

        Loan loan = new Loan();
        loan.setId(loanDto.id);
        loan.setPrice(loanDto.price);
        loan.setStatus(loanDto.status);
        loan.setStartDate(loanDto.startDate);
        loan.setEndDate(loanDto.endDate);
        loan.setCustomer(VisitorResponseDto.createModel(loanDto.customer));

        if (loanDto.validator.getClass() == OwnerResponseDto.class)
            loan.setValidator(OwnerResponseDto.createModel((OwnerResponseDto) loanDto.validator));
        else
            loan.setValidator(
                    EmployeeResponseDto.createModel((EmployeeResponseDto) loanDto.validator));

        loan.setArtwork(ArtworkResponseDto.createModel(loanDto.artwork));
        return loan;
    }
}
