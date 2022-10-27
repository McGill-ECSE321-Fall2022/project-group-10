/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 79 "../../../../../MuseumSystem.ump"
public class Artwork
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artwork Attributes
  private String title;
  private String author;
  private Date creationDate;
  private String description;
  private String imageLink;
  private float price;
  private boolean isAvailable;

  //Artwork Associations
  private MuseumSystem museum;
  private List<Loan> loans;
  private List<Donation> donations;
  private Room storage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artwork(String aTitle, String aAuthor, Date aCreationDate, String aDescription, String aImageLink, float aPrice, boolean aIsAvailable, MuseumSystem aMuseum, Room aStorage)
  {
    title = aTitle;
    author = aAuthor;
    creationDate = aCreationDate;
    description = aDescription;
    imageLink = aImageLink;
    price = aPrice;
    isAvailable = aIsAvailable;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create artwork due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    loans = new ArrayList<Loan>();
    donations = new ArrayList<Donation>();
    boolean didAddStorage = setStorage(aStorage);
    if (!didAddStorage)
    {
      throw new RuntimeException("Unable to create artwork due to storage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthor(String aAuthor)
  {
    boolean wasSet = false;
    author = aAuthor;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setImageLink(String aImageLink)
  {
    boolean wasSet = false;
    imageLink = aImageLink;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(float aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsAvailable(boolean aIsAvailable)
  {
    boolean wasSet = false;
    isAvailable = aIsAvailable;
    wasSet = true;
    return wasSet;
  }

  public String getTitle()
  {
    return title;
  }

  public String getAuthor()
  {
    return author;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public String getDescription()
  {
    return description;
  }

  public String getImageLink()
  {
    return imageLink;
  }

  public float getPrice()
  {
    return price;
  }

  public boolean getIsAvailable()
  {
    return isAvailable;
  }
  /* Code from template association_GetOne */
  public MuseumSystem getMuseum()
  {
    return museum;
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
  /* Code from template association_GetOne */
  public Room getStorage()
  {
    return storage;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseum(MuseumSystem aMuseum)
  {
    boolean wasSet = false;
    if (aMuseum == null)
    {
      return wasSet;
    }

    MuseumSystem existingMuseum = museum;
    museum = aMuseum;
    if (existingMuseum != null && !existingMuseum.equals(aMuseum))
    {
      existingMuseum.removeArtwork(this);
    }
    museum.addArtwork(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    loans.add(aLoan);
    if (aLoan.indexOfArtwork(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aLoan.addArtwork(this);
      if (!wasAdded)
      {
        loans.remove(aLoan);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    if (!loans.contains(aLoan))
    {
      return wasRemoved;
    }

    int oldIndex = loans.indexOf(aLoan);
    loans.remove(oldIndex);
    if (aLoan.indexOfArtwork(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aLoan.removeArtwork(this);
      if (!wasRemoved)
      {
        loans.add(oldIndex,aLoan);
      }
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addDonation(Donation aDonation)
  {
    boolean wasAdded = false;
    if (donations.contains(aDonation)) { return false; }
    donations.add(aDonation);
    if (aDonation.indexOfArtwork(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aDonation.addArtwork(this);
      if (!wasAdded)
      {
        donations.remove(aDonation);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeDonation(Donation aDonation)
  {
    boolean wasRemoved = false;
    if (!donations.contains(aDonation))
    {
      return wasRemoved;
    }

    int oldIndex = donations.indexOf(aDonation);
    donations.remove(oldIndex);
    if (aDonation.indexOfArtwork(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aDonation.removeArtwork(this);
      if (!wasRemoved)
      {
        donations.add(oldIndex,aDonation);
      }
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
  /* Code from template association_SetOneToMany */
  public boolean setStorage(Room aStorage)
  {
    boolean wasSet = false;
    if (aStorage == null)
    {
      return wasSet;
    }

    Room existingStorage = storage;
    storage = aStorage;
    if (existingStorage != null && !existingStorage.equals(aStorage))
    {
      existingStorage.removeArtwork(this);
    }
    storage.addArtwork(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MuseumSystem placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeArtwork(this);
    }
    ArrayList<Loan> copyOfLoans = new ArrayList<Loan>(loans);
    loans.clear();
    for(Loan aLoan : copyOfLoans)
    {
      if (aLoan.numberOfArtworks() <= Loan.minimumNumberOfArtworks())
      {
        aLoan.delete();
      }
      else
      {
        aLoan.removeArtwork(this);
      }
    }
    ArrayList<Donation> copyOfDonations = new ArrayList<Donation>(donations);
    donations.clear();
    for(Donation aDonation : copyOfDonations)
    {
      if (aDonation.numberOfArtworks() <= Donation.minimumNumberOfArtworks())
      {
        aDonation.delete();
      }
      else
      {
        aDonation.removeArtwork(this);
      }
    }
    Room placeholderStorage = storage;
    this.storage = null;
    if(placeholderStorage != null)
    {
      placeholderStorage.removeArtwork(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "title" + ":" + getTitle()+ "," +
            "author" + ":" + getAuthor()+ "," +
            "description" + ":" + getDescription()+ "," +
            "imageLink" + ":" + getImageLink()+ "," +
            "price" + ":" + getPrice()+ "," +
            "isAvailable" + ":" + getIsAvailable()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "storage = "+(getStorage()!=null?Integer.toHexString(System.identityHashCode(getStorage())):"null");
  }
}