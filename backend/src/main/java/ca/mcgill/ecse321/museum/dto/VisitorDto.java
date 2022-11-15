/* (C)2022 */
package ca.mcgill.ecse321.museum.dto;

import ca.mcgill.ecse321.museum.model.*;
import java.util.List;

// line 95 "../../../../..//MuseumSystem.ump"
public class VisitorDto extends PersonDto {

    // ------------------------
    // MEMBER VARIABLES
    // ------------------------

    // Visitor Attributes
    private boolean isActive;

    // Visitor Associations
    private List<ScheduleBlock> tickets;
    private List<Donation> donations;
    private List<Loan> loans;

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
}
