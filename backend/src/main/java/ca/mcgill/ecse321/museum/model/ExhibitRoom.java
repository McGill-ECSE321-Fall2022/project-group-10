/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;

@Entity
// line 114 "../../../../../MuseumSystem.ump"
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

  public ExhibitRoom(String aName, MuseumSystem aMuseum, int aCapacity)
  {
    super(aName, aMuseum);
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