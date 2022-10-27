/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
// line 95 "../../../../..//MuseumSystem.ump"
public class Visitor extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private boolean isActive;

  //Visitor Associations
  private List<ScheduleBlock> tickets;
  private List<Donation> donations;
  private List<Loan> loans;
  private ShoppingCart shoppingCart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aFirstName, String aLastName, String aEmail, String aPassword, MuseumSystem aMuseum, boolean aIsActive)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aMuseum);
    isActive = aIsActive;
    tickets = new ArrayList<ScheduleBlock>();
    donations = new ArrayList<Donation>();
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsActive(boolean aIsActive)
  {
    boolean wasSet = false;
    isActive = aIsActive;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsActive()
  {
    return isActive;
  }
  /* Code from template association_GetMany */
  public ScheduleBlock getTicket(int index)
  {
    ScheduleBlock aTicket = tickets.get(index);
    return aTicket;
  }

  public List<ScheduleBlock> getTickets()
  {
    List<ScheduleBlock> newTickets = Collections.unmodifiableList(tickets);
    return newTickets;
  }

  public int numberOfTickets()
  {
    int number = tickets.size();
    return number;
  }

  public boolean hasTickets()
  {
    boolean has = tickets.size() > 0;
    return has;
  }

  public int indexOfTicket(ScheduleBlock aTicket)
  {
    int index = tickets.indexOf(aTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public Donation getDonation(int index)
  {
    Donation aDonation = donations.get(index);
    return aDonation;
  }

  public List<Donation> getDonations()
  {
    List<Donation> newDonations = Collections.unmodifiableList(donations);
    return newDonations;
  }

  public int numberOfDonations()
  {
    int number = donations.size();
    return number;
  }

  public boolean hasDonations()
  {
    boolean has = donations.size() > 0;
    return has;
  }

  public int indexOfDonation(Donation aDonation)
  {
    int index = donations.indexOf(aDonation);
    return index;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }
  /* Code from template association_GetOne */
  public ShoppingCart getShoppingCart()
  {
    return shoppingCart;
  }

  public boolean hasShoppingCart()
  {
    boolean has = shoppingCart != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTicket(ScheduleBlock aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    tickets.add(aTicket);
    if (aTicket.indexOfVisitor(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTicket.addVisitor(this);
      if (!wasAdded)
      {
        tickets.remove(aTicket);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTicket(ScheduleBlock aTicket)
  {
    boolean wasRemoved = false;
    if (!tickets.contains(aTicket))
    {
      return wasRemoved;
    }

    int oldIndex = tickets.indexOf(aTicket);
    tickets.remove(oldIndex);
    if (aTicket.indexOfVisitor(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTicket.removeVisitor(this);
      if (!wasRemoved)
      {
        tickets.add(oldIndex,aTicket);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAt(ScheduleBlock aTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTicket(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAt(ScheduleBlock aTicket, int index)
  {
    boolean wasAdded = false;
    if(tickets.contains(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAt(aTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Donation addDonation(int aId, boolean aValidated, MuseumSystem aMuseum, Artwork... allArtworks)
  {
    return new Donation(aId, aValidated, aMuseum, this, allArtworks);
  }

  public boolean addDonation(Donation aDonation)
  {
    boolean wasAdded = false;
    if (donations.contains(aDonation)) { return false; }
    Visitor existingDonor = aDonation.getDonor();
    boolean isNewDonor = existingDonor != null && !this.equals(existingDonor);
    if (isNewDonor)
    {
      aDonation.setDonor(this);
    }
    else
    {
      donations.add(aDonation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDonation(Donation aDonation)
  {
    boolean wasRemoved = false;
    //Unable to remove aDonation, as it must always have a donor
    if (!this.equals(aDonation.getDonor()))
    {
      donations.remove(aDonation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDonationAt(Donation aDonation, int index)
  {  
    boolean wasAdded = false;
    if(addDonation(aDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDonations()) { index = numberOfDonations() - 1; }
      donations.remove(aDonation);
      donations.add(index, aDonation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDonationAt(Donation aDonation, int index)
  {
    boolean wasAdded = false;
    if(donations.contains(aDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDonations()) { index = numberOfDonations() - 1; }
      donations.remove(aDonation);
      donations.add(index, aDonation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDonationAt(aDonation, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(int aId, float aPrice, boolean aValidated, Date aStartDate, Date aEndDate, MuseumSystem aMuseum, Artwork... allArtworks)
  {
    return new Loan(aId, aPrice, aValidated, aStartDate, aEndDate, aMuseum, this, allArtworks);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Visitor existingCustomer = aLoan.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aLoan.setCustomer(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a customer
    if (!this.equals(aLoan.getCustomer()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setShoppingCart(ShoppingCart aShoppingCart)
  {
    boolean wasSet = false;
    ShoppingCart existingShoppingCart = shoppingCart;
    shoppingCart = aShoppingCart;
    if (existingShoppingCart != null && !existingShoppingCart.equals(aShoppingCart))
    {
      existingShoppingCart.removeCustomer(this);
    }
    if (aShoppingCart != null)
    {
      aShoppingCart.addCustomer(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<ScheduleBlock> copyOfTickets = new ArrayList<ScheduleBlock>(tickets);
    tickets.clear();
    for(ScheduleBlock aTicket : copyOfTickets)
    {
      aTicket.removeVisitor(this);
    }
    for(int i=donations.size(); i > 0; i--)
    {
      Donation aDonation = donations.get(i - 1);
      aDonation.delete();
    }
    for(int i=loans.size(); i > 0; i--)
    {
      Loan aLoan = loans.get(i - 1);
      aLoan.delete();
    }
    if (shoppingCart != null)
    {
      ShoppingCart placeholderShoppingCart = shoppingCart;
      this.shoppingCart = null;
      placeholderShoppingCart.removeCustomer(this);
    }
    super.delete();
  }


  @ManyToMany(mappedBy="visitors")
  // line 97 "../../../../..//MuseumSystem.ump"
  public List<ScheduleBlock> getTicketsJPA(){
    return getTickets();
  }


  @OneToMany(mappedBy="customer")
  // line 98 "../../../../..//MuseumSystem.ump"
  public List<Loan> getLoansJPA(){
    return getLoans();
  }


  @ManyToOne(optional=false)
  // line 99 "../../../../..//MuseumSystem.ump"
  public ShoppingCart getShoppingCartJPA(){
    return getShoppingCart();
  }


  @OneToMany(mappedBy="donor")
  // line 100 "../../../../..//MuseumSystem.ump"
  public List<Donation> getDonationsJPA(){
    return getDonations();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isActive" + ":" + getIsActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shoppingCart = "+(getShoppingCart()!=null?Integer.toHexString(System.identityHashCode(getShoppingCart())):"null");
  }
}