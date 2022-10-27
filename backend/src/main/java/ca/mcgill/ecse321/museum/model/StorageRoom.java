/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;

@Entity
// line 121 "../../../../../MuseumSystem.ump"
public class StorageRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StorageRoom(String aName, MuseumSystem aMuseum)
  {
    super(aName, aMuseum);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}