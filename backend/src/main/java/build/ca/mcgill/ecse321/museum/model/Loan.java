/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 80 "../../../../../../MuseumSystem.ump"
public class Loan
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private int id;
  private float price;
  private boolean validated;
  private Date startDate;
  private Date endDate;

  //Loan Associations
  private MuseumSystem museum;
  private Visitor customer;
  private Administrator validator;
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(int aId, float aPrice, boolean aValidated, Date aStartDate, Date aEndDate, MuseumSystem aMuseum, Visitor aCustomer, Artwork... allArtworks)
  {
    id = aId;
    price = aPrice;
    validated = aValidated;
    startDate = aStartDate;
    endDate = aEndDate;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create loan due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create loan due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artworks = new ArrayList<Artwork>();
    boolean didAddArtworks = setArtworks(allArtworks);
    if (!didAddArtworks)
    {
      throw new RuntimeException("Unable to create Loan, must have at least 1 artworks. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(float aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setValidated(boolean aValidated)
  {
    boolean wasSet = false;
    validated = aValidated;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  @Id
  public int getId()
  {
    return id;
  }

  public float getPrice()
  {
    return price;
  }

  public boolean getValidated()
  {
    return validated;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  /* Code from template association_GetOne */
  public MuseumSystem getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public Visitor getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Administrator getValidator()
  {
    return validator;
  }

  public boolean hasValidator()
  {
    boolean has = validator != null;
    return has;
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
      existingMuseum.removeLoan(this);
    }
    museum.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Visitor aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Visitor existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeLoan(this);
    }
    customer.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setValidator(Administrator aValidator)
  {
    boolean wasSet = false;
    Administrator existingValidator = validator;
    validator = aValidator;
    if (existingValidator != null && !existingValidator.equals(aValidator))
    {
      existingValidator.removeAssignedLoan(this);
    }
    if (aValidator != null)
    {
      aValidator.addAssignedLoan(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfArtworksValid()
  {
    boolean isValid = numberOfArtworks() >= minimumNumberOfArtworks();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtworks()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artworks.contains(aArtwork)) { return false; }
    artworks.add(aArtwork);
    if (aArtwork.indexOfLoan(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aArtwork.addLoan(this);
      if (!wasAdded)
      {
        artworks.remove(aArtwork);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeArtwork(Artwork aArtwork)
  {
    boolean wasRemoved = false;
    if (!artworks.contains(aArtwork))
    {
      return wasRemoved;
    }

    if (numberOfArtworks() <= minimumNumberOfArtworks())
    {
      return wasRemoved;
    }

    int oldIndex = artworks.indexOf(aArtwork);
    artworks.remove(oldIndex);
    if (aArtwork.indexOfLoan(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aArtwork.removeLoan(this);
      if (!wasRemoved)
      {
        artworks.add(oldIndex,aArtwork);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setArtworks(Artwork... newArtworks)
  {
    boolean wasSet = false;
    ArrayList<Artwork> verifiedArtworks = new ArrayList<Artwork>();
    for (Artwork aArtwork : newArtworks)
    {
      if (verifiedArtworks.contains(aArtwork))
      {
        continue;
      }
      verifiedArtworks.add(aArtwork);
    }

    if (verifiedArtworks.size() != newArtworks.length || verifiedArtworks.size() < minimumNumberOfArtworks())
    {
      return wasSet;
    }

    ArrayList<Artwork> oldArtworks = new ArrayList<Artwork>(artworks);
    artworks.clear();
    for (Artwork aNewArtwork : verifiedArtworks)
    {
      artworks.add(aNewArtwork);
      if (oldArtworks.contains(aNewArtwork))
      {
        oldArtworks.remove(aNewArtwork);
      }
      else
      {
        aNewArtwork.addLoan(this);
      }
    }

    for (Artwork anOldArtwork : oldArtworks)
    {
      anOldArtwork.removeLoan(this);
    }
    wasSet = true;
    return wasSet;
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

  public void delete()
  {
    MuseumSystem placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeLoan(this);
    }
    Visitor placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeLoan(this);
    }
    if (validator != null)
    {
      Administrator placeholderValidator = validator;
      this.validator = null;
      placeholderValidator.removeAssignedLoan(this);
    }
    ArrayList<Artwork> copyOfArtworks = new ArrayList<Artwork>(artworks);
    artworks.clear();
    for(Artwork aArtwork : copyOfArtworks)
    {
      aArtwork.removeLoan(this);
    }
  }


  @ManyToOne(optional=false)
  // line 85 "../../../../../../MuseumSystem.ump"
  public MuseumSystem getMuseumJPA(){
    return getMuseum();
  }


  @ManyToOne(optional=false)
  // line 86 "../../../../../../MuseumSystem.ump"
  public Visitor getCustomerJPA(){
    return getCustomer();
  }


  @ManyToMany(mappedBy="loans")
  // line 87 "../../../../../../MuseumSystem.ump"
  public List<Artwork> getArtworksJPA(){
    return getArtworks();
  }


  @ManyToOne(optional=false)
  // line 88 "../../../../../../MuseumSystem.ump"
  public Administrator getValidatorJPA(){
    return getValidator();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "price" + ":" + getPrice()+ "," +
            "validated" + ":" + getValidated()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "validator = "+(getValidator()!=null?Integer.toHexString(System.identityHashCode(getValidator())):"null");
  }
}