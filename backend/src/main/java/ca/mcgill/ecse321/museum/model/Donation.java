/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;

@Entity
// line 46 "../../../../../MuseumSystem.ump"
public class Donation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Donation Attributes
  private boolean validated;

  //Donation Associations
  private MuseumSystem museum;
  private Visitor donor;
  private Administrator validator;
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Donation(boolean aValidated, MuseumSystem aMuseum, Visitor aDonor, Artwork... allArtworks)
  {
    validated = aValidated;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create donation due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddDonor = setDonor(aDonor);
    if (!didAddDonor)
    {
      throw new RuntimeException("Unable to create donation due to donor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artworks = new ArrayList<Artwork>();
    boolean didAddArtworks = setArtworks(allArtworks);
    if (!didAddArtworks)
    {
      throw new RuntimeException("Unable to create Donation, must have at least 1 artworks. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setValidated(boolean aValidated)
  {
    boolean wasSet = false;
    validated = aValidated;
    wasSet = true;
    return wasSet;
  }

  public boolean getValidated()
  {
    return validated;
  }
  /* Code from template association_GetOne */
  public MuseumSystem getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetOne */
  public Visitor getDonor()
  {
    return donor;
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
      existingMuseum.removeDonation(this);
    }
    museum.addDonation(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDonor(Visitor aDonor)
  {
    boolean wasSet = false;
    if (aDonor == null)
    {
      return wasSet;
    }

    Visitor existingDonor = donor;
    donor = aDonor;
    if (existingDonor != null && !existingDonor.equals(aDonor))
    {
      existingDonor.removeDonation(this);
    }
    donor.addDonation(this);
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
      existingValidator.removeAssignedDonation(this);
    }
    if (aValidator != null)
    {
      aValidator.addAssignedDonation(this);
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
    if (aArtwork.indexOfDonation(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aArtwork.addDonation(this);
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
    if (aArtwork.indexOfDonation(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aArtwork.removeDonation(this);
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
        aNewArtwork.addDonation(this);
      }
    }

    for (Artwork anOldArtwork : oldArtworks)
    {
      anOldArtwork.removeDonation(this);
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
      placeholderMuseum.removeDonation(this);
    }
    Visitor placeholderDonor = donor;
    this.donor = null;
    if(placeholderDonor != null)
    {
      placeholderDonor.removeDonation(this);
    }
    if (validator != null)
    {
      Administrator placeholderValidator = validator;
      this.validator = null;
      placeholderValidator.removeAssignedDonation(this);
    }
    ArrayList<Artwork> copyOfArtworks = new ArrayList<Artwork>(artworks);
    artworks.clear();
    for(Artwork aArtwork : copyOfArtworks)
    {
      aArtwork.removeDonation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "validated" + ":" + getValidated()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "donor = "+(getDonor()!=null?Integer.toHexString(System.identityHashCode(getDonor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "validator = "+(getValidator()!=null?Integer.toHexString(System.identityHashCode(getValidator())):"null");
  }
}