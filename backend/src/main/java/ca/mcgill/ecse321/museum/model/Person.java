/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;

// line 29 "../../../../../MuseumSystem.ump"
public abstract class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  //Person Associations
  private MuseumSystem museum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aFirstName, String aLastName, String aEmail, String aPassword, MuseumSystem aMuseum)
  {
    firstName = aFirstName;
    lastName = aLastName;
    email = aEmail;
    password = aPassword;
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create user due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public MuseumSystem getMuseum()
  {
    return museum;
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
      existingMuseum.removeUser(this);
    }
    museum.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MuseumSystem placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
}