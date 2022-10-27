/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
// line 65 "../../../../../MuseumSystem.ump"
public class Visitor extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private boolean isActive;

  //Visitor Associations
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
  public static int minimumNumberOfDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Donation addDonation(boolean aValidated, MuseumSystem aMuseum, Artwork... allArtworks)
  {
    return new Donation(aValidated, aMuseum, this, allArtworks);
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
  public Loan addLoan(float aPrice, boolean aValidated, Date aStartDate, Date aEndDate, MuseumSystem aMuseum, Artwork... allArtworks)
  {
    return new Loan(aPrice, aValidated, aStartDate, aEndDate, aMuseum, this, allArtworks);
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


  public String toString()
  {
    return super.toString() + "["+
            "isActive" + ":" + getIsActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shoppingCart = "+(getShoppingCart()!=null?Integer.toHexString(System.identityHashCode(getShoppingCart())):"null");
  }
}