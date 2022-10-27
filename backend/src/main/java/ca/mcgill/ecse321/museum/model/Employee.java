/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;

// line 136 "../../../../..//MuseumSystem.ump"
public class Employee extends Administrator
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private boolean isActive;
  private float salary;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aFirstName, String aLastName, String aEmail, String aPassword, MuseumSystem aMuseum, boolean aIsActive, float aSalary)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aMuseum);
    isActive = aIsActive;
    salary = aSalary;
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

  public boolean setSalary(float aSalary)
  {
    boolean wasSet = false;
    salary = aSalary;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsActive()
  {
    return isActive;
  }

  public float getSalary()
  {
    return salary;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isActive" + ":" + getIsActive()+ "," +
            "salary" + ":" + getSalary()+ "]";
  }
}