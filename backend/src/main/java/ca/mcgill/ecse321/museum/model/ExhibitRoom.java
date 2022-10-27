/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;

@Entity
// line 159 "../../../../..//MuseumSystem.ump"
public class ExhibitRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ExhibitRoom Attributes
  private int capacity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ExhibitRoom(int aId, String aName, MuseumSystem aMuseum, int aCapacity)
  {
    super(aId, aName, aMuseum);
    capacity = aCapacity;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCapacity(int aCapacity)
  {
    boolean wasSet = false;
    capacity = aCapacity;
    wasSet = true;
    return wasSet;
  }

  public int getCapacity()
  {
    return capacity;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "capacity" + ":" + getCapacity()+ "]";
  }
}