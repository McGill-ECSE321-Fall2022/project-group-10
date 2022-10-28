/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
// line 28 "../../../../../../MuseumSystem.ump"
public class ScheduleBlock
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ScheduleEvent { MUSEUM_OPEN, MUSEUM_RESTORATION, MUSEUM_MEETING }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ScheduleBlock Attributes
  private int id;
  private Date startDate;
  private Date endDate;
  private float visitFees;
  private int visitCapacity;
  private ScheduleEvent event;

  //ScheduleBlock Associations
  private List<Administrator> admins;
  private List<Visitor> visitors;
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ScheduleBlock(int aId, Date aStartDate, Date aEndDate, float aVisitFees, int aVisitCapacity, ScheduleEvent aEvent, Calendar aCalendar, Administrator... allAdmins)
  {
    id = aId;
    startDate = aStartDate;
    endDate = aEndDate;
    visitFees = aVisitFees;
    visitCapacity = aVisitCapacity;
    event = aEvent;
    admins = new ArrayList<Administrator>();
    boolean didAddAdmins = setAdmins(allAdmins);
    if (!didAddAdmins)
    {
      throw new RuntimeException("Unable to create ScheduleBlock, must have at least 1 admins. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    visitors = new ArrayList<Visitor>();
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create scheduleBlock due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setVisitFees(float aVisitFees)
  {
    boolean wasSet = false;
    visitFees = aVisitFees;
    wasSet = true;
    return wasSet;
  }

  public boolean setVisitCapacity(int aVisitCapacity)
  {
    boolean wasSet = false;
    visitCapacity = aVisitCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setEvent(ScheduleEvent aEvent)
  {
    boolean wasSet = false;
    event = aEvent;
    wasSet = true;
    return wasSet;
  }

  @Id
  public int getId()
  {
    return id;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public float getVisitFees()
  {
    return visitFees;
  }

  public int getVisitCapacity()
  {
    return visitCapacity;
  }

  public ScheduleEvent getEvent()
  {
    return event;
  }
  /* Code from template association_GetMany */
  public Administrator getAdmin(int index)
  {
    Administrator aAdmin = admins.get(index);
    return aAdmin;
  }

  public List<Administrator> getAdmins()
  {
    List<Administrator> newAdmins = Collections.unmodifiableList(admins);
    return newAdmins;
  }

  public int numberOfAdmins()
  {
    int number = admins.size();
    return number;
  }

  public boolean hasAdmins()
  {
    boolean has = admins.size() > 0;
    return has;
  }

  public int indexOfAdmin(Administrator aAdmin)
  {
    int index = admins.indexOf(aAdmin);
    return index;
  }
  /* Code from template association_GetMany */
  public Visitor getVisitor(int index)
  {
    Visitor aVisitor = visitors.get(index);
    return aVisitor;
  }

  public List<Visitor> getVisitors()
  {
    List<Visitor> newVisitors = Collections.unmodifiableList(visitors);
    return newVisitors;
  }

  public int numberOfVisitors()
  {
    int number = visitors.size();
    return number;
  }

  public boolean hasVisitors()
  {
    boolean has = visitors.size() > 0;
    return has;
  }

  public int indexOfVisitor(Visitor aVisitor)
  {
    int index = visitors.indexOf(aVisitor);
    return index;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfAdminsValid()
  {
    boolean isValid = numberOfAdmins() >= minimumNumberOfAdmins();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAdmins()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAdmin(Administrator aAdmin)
  {
    boolean wasAdded = false;
    if (admins.contains(aAdmin)) { return false; }
    admins.add(aAdmin);
    if (aAdmin.indexOfShift(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAdmin.addShift(this);
      if (!wasAdded)
      {
        admins.remove(aAdmin);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeAdmin(Administrator aAdmin)
  {
    boolean wasRemoved = false;
    if (!admins.contains(aAdmin))
    {
      return wasRemoved;
    }

    if (numberOfAdmins() <= minimumNumberOfAdmins())
    {
      return wasRemoved;
    }

    int oldIndex = admins.indexOf(aAdmin);
    admins.remove(oldIndex);
    if (aAdmin.indexOfShift(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAdmin.removeShift(this);
      if (!wasRemoved)
      {
        admins.add(oldIndex,aAdmin);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setAdmins(Administrator... newAdmins)
  {
    boolean wasSet = false;
    ArrayList<Administrator> verifiedAdmins = new ArrayList<Administrator>();
    for (Administrator aAdmin : newAdmins)
    {
      if (verifiedAdmins.contains(aAdmin))
      {
        continue;
      }
      verifiedAdmins.add(aAdmin);
    }

    if (verifiedAdmins.size() != newAdmins.length || verifiedAdmins.size() < minimumNumberOfAdmins())
    {
      return wasSet;
    }

    ArrayList<Administrator> oldAdmins = new ArrayList<Administrator>(admins);
    admins.clear();
    for (Administrator aNewAdmin : verifiedAdmins)
    {
      admins.add(aNewAdmin);
      if (oldAdmins.contains(aNewAdmin))
      {
        oldAdmins.remove(aNewAdmin);
      }
      else
      {
        aNewAdmin.addShift(this);
      }
    }

    for (Administrator anOldAdmin : oldAdmins)
    {
      anOldAdmin.removeShift(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAdminAt(Administrator aAdmin, int index)
  {  
    boolean wasAdded = false;
    if(addAdmin(aAdmin))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
      admins.remove(aAdmin);
      admins.add(index, aAdmin);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAdminAt(Administrator aAdmin, int index)
  {
    boolean wasAdded = false;
    if(admins.contains(aAdmin))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
      admins.remove(aAdmin);
      admins.add(index, aAdmin);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAdminAt(aAdmin, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitors()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addVisitor(Visitor aVisitor)
  {
    boolean wasAdded = false;
    if (visitors.contains(aVisitor)) { return false; }
    visitors.add(aVisitor);
    if (aVisitor.indexOfTicket(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aVisitor.addTicket(this);
      if (!wasAdded)
      {
        visitors.remove(aVisitor);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeVisitor(Visitor aVisitor)
  {
    boolean wasRemoved = false;
    if (!visitors.contains(aVisitor))
    {
      return wasRemoved;
    }

    int oldIndex = visitors.indexOf(aVisitor);
    visitors.remove(oldIndex);
    if (aVisitor.indexOfTicket(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aVisitor.removeTicket(this);
      if (!wasRemoved)
      {
        visitors.add(oldIndex,aVisitor);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVisitorAt(Visitor aVisitor, int index)
  {  
    boolean wasAdded = false;
    if(addVisitor(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVisitorAt(Visitor aVisitor, int index)
  {
    boolean wasAdded = false;
    if(visitors.contains(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVisitorAt(aVisitor, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCalendar(Calendar aCalendar)
  {
    boolean wasSet = false;
    if (aCalendar == null)
    {
      return wasSet;
    }

    Calendar existingCalendar = calendar;
    calendar = aCalendar;
    if (existingCalendar != null && !existingCalendar.equals(aCalendar))
    {
      existingCalendar.removeScheduleBlock(this);
    }
    calendar.addScheduleBlock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Administrator> copyOfAdmins = new ArrayList<Administrator>(admins);
    admins.clear();
    for(Administrator aAdmin : copyOfAdmins)
    {
      aAdmin.removeShift(this);
    }
    ArrayList<Visitor> copyOfVisitors = new ArrayList<Visitor>(visitors);
    visitors.clear();
    for(Visitor aVisitor : copyOfVisitors)
    {
      aVisitor.removeTicket(this);
    }
    Calendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeScheduleBlock(this);
    }
  }


  @ManyToOne(optional=false)
  // line 31 "../../../../../../MuseumSystem.ump"
  public Calendar getCalendarJPA(){
    return getCalendar();
  }


  @ManyToMany(mappedBy="shifts")
  // line 32 "../../../../../../MuseumSystem.ump"
  public List<Administrator> getAdminsJPA(){
    return getAdmins();
  }


  @ManyToMany(mappedBy="tickets")
  // line 33 "../../../../../../MuseumSystem.ump"
  public List<Visitor> getVisitorsJPA(){
    return getVisitors();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "visitFees" + ":" + getVisitFees()+ "," +
            "visitCapacity" + ":" + getVisitCapacity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "event" + "=" + (getEvent() != null ? !getEvent().equals(this)  ? getEvent().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}