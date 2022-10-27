/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
// line 4 "../../../../../MuseumSystem.ump"
public class MuseumSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MuseumSystem Attributes
  private String name;

  //MuseumSystem Associations
  private Calendar calendar;
  private List<Person> users;
  private List<Donation> donations;
  private List<Loan> loans;
  private List<Artwork> artworks;
  private List<Room> rooms;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MuseumSystem(String aName, Calendar aCalendar)
  {
    name = aName;
    if (aCalendar == null || aCalendar.getMuseum() != null)
    {
      throw new RuntimeException("Unable to create MuseumSystem due to aCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    calendar = aCalendar;
    users = new ArrayList<Person>();
    donations = new ArrayList<Donation>();
    loans = new ArrayList<Loan>();
    artworks = new ArrayList<Artwork>();
    rooms = new ArrayList<Room>();
  }

  public MuseumSystem(String aName, boolean aIsMuseumOpenForCalendar)
  {
    name = aName;
    calendar = new Calendar(aIsMuseumOpenForCalendar, this);
    users = new ArrayList<Person>();
    donations = new ArrayList<Donation>();
    loans = new ArrayList<Loan>();
    artworks = new ArrayList<Artwork>();
    rooms = new ArrayList<Room>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_GetMany */
  public Person getUser(int index)
  {
    Person aUser = users.get(index);
    return aUser;
  }

  public List<Person> getUsers()
  {
    List<Person> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(Person aUser)
  {
    int index = users.indexOf(aUser);
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
  /* Code from template association_GetMany */
  public Artwork getArtwork(int index)
  {
    Artwork aArtwork = artworks.get(index);
    return aArtwork;
  }

  public List<Artwork> getArtworks()
  {
    List<Artwork> newArtworks = Collections.unmodifiableList(artworks);
    return newArtworks;
  }

  public int numberOfArtworks()
  {
    int number = artworks.size();
    return number;
  }

  public boolean hasArtworks()
  {
    boolean has = artworks.size() > 0;
    return has;
  }

  public int indexOfArtwork(Artwork aArtwork)
  {
    int index = artworks.indexOf(aArtwork);
    return index;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUser(Person aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    MuseumSystem existingMuseum = aUser.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aUser.setMuseum(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(Person aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a museum
    if (!this.equals(aUser.getMuseum()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(Person aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(Person aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Donation addDonation(boolean aValidated, Visitor aDonor, Artwork... allArtworks)
  {
    return new Donation(aValidated, this, aDonor, allArtworks);
  }

  public boolean addDonation(Donation aDonation)
  {
    boolean wasAdded = false;
    if (donations.contains(aDonation)) { return false; }
    MuseumSystem existingMuseum = aDonation.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aDonation.setMuseum(this);
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
    //Unable to remove aDonation, as it must always have a museum
    if (!this.equals(aDonation.getMuseum()))
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
  public Loan addLoan(float aPrice, boolean aValidated, Date aStartDate, Date aEndDate, Visitor aCustomer, Artwork... allArtworks)
  {
    return new Loan(aPrice, aValidated, aStartDate, aEndDate, this, aCustomer, allArtworks);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    MuseumSystem existingMuseum = aLoan.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aLoan.setMuseum(this);
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
    //Unable to remove aLoan, as it must always have a museum
    if (!this.equals(aLoan.getMuseum()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtworks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artwork addArtwork(String aTitle, String aAuthor, Date aCreationDate, String aDescription, String aImageLink, float aPrice, boolean aIsAvailable, Room aStorage)
  {
    return new Artwork(aTitle, aAuthor, aCreationDate, aDescription, aImageLink, aPrice, aIsAvailable, this, aStorage);
  }

  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artworks.contains(aArtwork)) { return false; }
    MuseumSystem existingMuseum = aArtwork.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aArtwork.setMuseum(this);
    }
    else
    {
      artworks.add(aArtwork);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtwork(Artwork aArtwork)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtwork, as it must always have a museum
    if (!this.equals(aArtwork.getMuseum()))
    {
      artworks.remove(aArtwork);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtworkAt(Artwork aArtwork, int index)
  {  
    boolean wasAdded = false;
    if(addArtwork(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtworkAt(Artwork aArtwork, int index)
  {
    boolean wasAdded = false;
    if(artworks.contains(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtworkAt(aArtwork, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    MuseumSystem existingMuseum = aRoom.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aRoom.setMuseum(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a museum
    if (!this.equals(aRoom.getMuseum()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Calendar existingCalendar = calendar;
    calendar = null;
    if (existingCalendar != null)
    {
      existingCalendar.delete();
    }
    while (users.size() > 0)
    {
      Person aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (donations.size() > 0)
    {
      Donation aDonation = donations.get(donations.size() - 1);
      aDonation.delete();
      donations.remove(aDonation);
    }
    
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
    while (artworks.size() > 0)
    {
      Artwork aArtwork = artworks.get(artworks.size() - 1);
      aArtwork.delete();
      artworks.remove(aArtwork);
    }
    
    while (rooms.size() > 0)
    {
      Room aRoom = rooms.get(rooms.size() - 1);
      aRoom.delete();
      rooms.remove(aRoom);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}