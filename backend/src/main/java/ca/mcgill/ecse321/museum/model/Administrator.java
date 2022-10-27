/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import java.util.*;

// line 38 "../../../../../MuseumSystem.ump"
public abstract class Administrator extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Administrator Associations
  private List<Donation> assignedDonations;
  private List<Loan> assignedLoans;
  private List<ScheduleBlock> shifts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator(String aFirstName, String aLastName, String aEmail, String aPassword, MuseumSystem aMuseum)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aMuseum);
    assignedDonations = new ArrayList<Donation>();
    assignedLoans = new ArrayList<Loan>();
    shifts = new ArrayList<ScheduleBlock>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Donation getAssignedDonation(int index)
  {
    Donation aAssignedDonation = assignedDonations.get(index);
    return aAssignedDonation;
  }

  public List<Donation> getAssignedDonations()
  {
    List<Donation> newAssignedDonations = Collections.unmodifiableList(assignedDonations);
    return newAssignedDonations;
  }

  public int numberOfAssignedDonations()
  {
    int number = assignedDonations.size();
    return number;
  }

  public boolean hasAssignedDonations()
  {
    boolean has = assignedDonations.size() > 0;
    return has;
  }

  public int indexOfAssignedDonation(Donation aAssignedDonation)
  {
    int index = assignedDonations.indexOf(aAssignedDonation);
    return index;
  }
  /* Code from template association_GetMany */
  public Loan getAssignedLoan(int index)
  {
    Loan aAssignedLoan = assignedLoans.get(index);
    return aAssignedLoan;
  }

  public List<Loan> getAssignedLoans()
  {
    List<Loan> newAssignedLoans = Collections.unmodifiableList(assignedLoans);
    return newAssignedLoans;
  }

  public int numberOfAssignedLoans()
  {
    int number = assignedLoans.size();
    return number;
  }

  public boolean hasAssignedLoans()
  {
    boolean has = assignedLoans.size() > 0;
    return has;
  }

  public int indexOfAssignedLoan(Loan aAssignedLoan)
  {
    int index = assignedLoans.indexOf(aAssignedLoan);
    return index;
  }
  /* Code from template association_GetMany */
  public ScheduleBlock getShift(int index)
  {
    ScheduleBlock aShift = shifts.get(index);
    return aShift;
  }

  public List<ScheduleBlock> getShifts()
  {
    List<ScheduleBlock> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
  }

  public int numberOfShifts()
  {
    int number = shifts.size();
    return number;
  }

  public boolean hasShifts()
  {
    boolean has = shifts.size() > 0;
    return has;
  }

  public int indexOfShift(ScheduleBlock aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignedDonations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addAssignedDonation(Donation aAssignedDonation)
  {
    boolean wasAdded = false;
    if (assignedDonations.contains(aAssignedDonation)) { return false; }
    Administrator existingValidator = aAssignedDonation.getValidator();
    if (existingValidator == null)
    {
      aAssignedDonation.setValidator(this);
    }
    else if (!this.equals(existingValidator))
    {
      existingValidator.removeAssignedDonation(aAssignedDonation);
      addAssignedDonation(aAssignedDonation);
    }
    else
    {
      assignedDonations.add(aAssignedDonation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignedDonation(Donation aAssignedDonation)
  {
    boolean wasRemoved = false;
    if (assignedDonations.contains(aAssignedDonation))
    {
      assignedDonations.remove(aAssignedDonation);
      aAssignedDonation.setValidator(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignedDonationAt(Donation aAssignedDonation, int index)
  {  
    boolean wasAdded = false;
    if(addAssignedDonation(aAssignedDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedDonations()) { index = numberOfAssignedDonations() - 1; }
      assignedDonations.remove(aAssignedDonation);
      assignedDonations.add(index, aAssignedDonation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignedDonationAt(Donation aAssignedDonation, int index)
  {
    boolean wasAdded = false;
    if(assignedDonations.contains(aAssignedDonation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedDonations()) { index = numberOfAssignedDonations() - 1; }
      assignedDonations.remove(aAssignedDonation);
      assignedDonations.add(index, aAssignedDonation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignedDonationAt(aAssignedDonation, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignedLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addAssignedLoan(Loan aAssignedLoan)
  {
    boolean wasAdded = false;
    if (assignedLoans.contains(aAssignedLoan)) { return false; }
    Administrator existingValidator = aAssignedLoan.getValidator();
    if (existingValidator == null)
    {
      aAssignedLoan.setValidator(this);
    }
    else if (!this.equals(existingValidator))
    {
      existingValidator.removeAssignedLoan(aAssignedLoan);
      addAssignedLoan(aAssignedLoan);
    }
    else
    {
      assignedLoans.add(aAssignedLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignedLoan(Loan aAssignedLoan)
  {
    boolean wasRemoved = false;
    if (assignedLoans.contains(aAssignedLoan))
    {
      assignedLoans.remove(aAssignedLoan);
      aAssignedLoan.setValidator(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignedLoanAt(Loan aAssignedLoan, int index)
  {  
    boolean wasAdded = false;
    if(addAssignedLoan(aAssignedLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedLoans()) { index = numberOfAssignedLoans() - 1; }
      assignedLoans.remove(aAssignedLoan);
      assignedLoans.add(index, aAssignedLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignedLoanAt(Loan aAssignedLoan, int index)
  {
    boolean wasAdded = false;
    if(assignedLoans.contains(aAssignedLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedLoans()) { index = numberOfAssignedLoans() - 1; }
      assignedLoans.remove(aAssignedLoan);
      assignedLoans.add(index, aAssignedLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignedLoanAt(aAssignedLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addShift(ScheduleBlock aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    shifts.add(aShift);
    if (aShift.indexOfAdmin(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aShift.addAdmin(this);
      if (!wasAdded)
      {
        shifts.remove(aShift);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeShift(ScheduleBlock aShift)
  {
    boolean wasRemoved = false;
    if (!shifts.contains(aShift))
    {
      return wasRemoved;
    }

    int oldIndex = shifts.indexOf(aShift);
    shifts.remove(oldIndex);
    if (aShift.indexOfAdmin(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aShift.removeAdmin(this);
      if (!wasRemoved)
      {
        shifts.add(oldIndex,aShift);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(ScheduleBlock aShift, int index)
  {  
    boolean wasAdded = false;
    if(addShift(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(ScheduleBlock aShift, int index)
  {
    boolean wasAdded = false;
    if(shifts.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShiftAt(aShift, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while( !assignedDonations.isEmpty() )
    {
      assignedDonations.get(0).setValidator(null);
    }
    while( !assignedLoans.isEmpty() )
    {
      assignedLoans.get(0).setValidator(null);
    }
    ArrayList<ScheduleBlock> copyOfShifts = new ArrayList<ScheduleBlock>(shifts);
    shifts.clear();
    for(ScheduleBlock aShift : copyOfShifts)
    {
      if (aShift.numberOfAdmins() <= ScheduleBlock.minimumNumberOfAdmins())
      {
        aShift.delete();
      }
      else
      {
        aShift.removeAdmin(this);
      }
    }
    super.delete();
  }

}