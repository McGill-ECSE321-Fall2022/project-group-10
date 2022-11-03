package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
// line 95 "../../../../..//MuseumSystem.ump"
public class VisitorDto extends PersonDto {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Visitor Attributes
    private boolean isActive;

    //Visitor Associations
    @ManyToMany
    private List<ScheduleBlock> tickets;
    @OneToMany
    private List<Donation> donations;
    @OneToMany
    private List<Loan> loans;
    @ManyToOne
    private ShoppingCart shoppingCart;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<ScheduleBlock> getTickets() {
        return tickets;
    }

    public void setTickets(List<ScheduleBlock> tickets) {
        this.tickets = tickets;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}