/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/

package ca.mcgill.ecse321.museum.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;

// @Entity
// line 18 "../../../../..//MuseumSystem.ump"
public class Calendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Calendar Attributes
  private int id;
  private boolean isMuseumOpen;

  //Calendar Associations
  private MuseumSystem museum;
  private List<ScheduleBlock> scheduleBlocks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Calendar(int aId, boolean aIsMuseumOpen, MuseumSystem aMuseum)
  {
    id = aId;
    isMuseumOpen = aIsMuseumOpen;
    if (aMuseum == null || aMuseum.getCalendar() != null)
    {
      throw new RuntimeException("Unable to create Calendar due to aMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    museum = aMuseum;
    scheduleBlocks = new ArrayList<ScheduleBlock>();
  }

  public Calendar(int aId, boolean aIsMuseumOpen, int aIdForMuseum, String aNameForMuseum)
  {
    id = aId;
    isMuseumOpen = aIsMuseumOpen;
    museum = new MuseumSystem(aIdForMuseum, aNameForMuseum, this);
    scheduleBlocks = new ArrayList<ScheduleBlock>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMuseumOpen(boolean aIsMuseumOpen)
  {
    boolean wasSet = false;
    isMuseumOpen = aIsMuseumOpen;
    wasSet = true;
    return wasSet;
  }

  @Id
  public int getId()
  {
    return id;
  }

  public boolean getIsMuseumOpen()
  {
    return isMuseumOpen;
  }
  /* Code from template association_GetOne */
  public MuseumSystem getMuseum()
  {
    return museum;
  }
  /* Code from template association_GetMany */
  public ScheduleBlock getScheduleBlock(int index)
  {
    ScheduleBlock aScheduleBlock = scheduleBlocks.get(index);
    return aScheduleBlock;
  }

  public List<ScheduleBlock> getScheduleBlocks()
  {
    List<ScheduleBlock> newScheduleBlocks = Collections.unmodifiableList(scheduleBlocks);
    return newScheduleBlocks;
  }

  public int numberOfScheduleBlocks()
  {
    int number = scheduleBlocks.size();
    return number;
  }

  public boolean hasScheduleBlocks()
  {
    boolean has = scheduleBlocks.size() > 0;
    return has;
  }

  public int indexOfScheduleBlock(ScheduleBlock aScheduleBlock)
  {
    int index = scheduleBlocks.indexOf(aScheduleBlock);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfScheduleBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ScheduleBlock addScheduleBlock(int aId, Date aStartDate, Date aEndDate, float aVisitFees, int aVisitCapacity, ScheduleBlock.ScheduleEvent aEvent, Administrator... allAdmins)
  {
    return new ScheduleBlock(aId, aStartDate, aEndDate, aVisitFees, aVisitCapacity, aEvent, this, allAdmins);
  }

  public boolean addScheduleBlock(ScheduleBlock aScheduleBlock)
  {
    boolean wasAdded = false;
    if (scheduleBlocks.contains(aScheduleBlock)) { return false; }
    Calendar existingCalendar = aScheduleBlock.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);
    if (isNewCalendar)
    {
      aScheduleBlock.setCalendar(this);
    }
    else
    {
      scheduleBlocks.add(aScheduleBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeScheduleBlock(ScheduleBlock aScheduleBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aScheduleBlock, as it must always have a calendar
    if (!this.equals(aScheduleBlock.getCalendar()))
    {
      scheduleBlocks.remove(aScheduleBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleBlockAt(ScheduleBlock aScheduleBlock, int index)
  {  
    boolean wasAdded = false;
    if(addScheduleBlock(aScheduleBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduleBlocks()) { index = numberOfScheduleBlocks() - 1; }
      scheduleBlocks.remove(aScheduleBlock);
      scheduleBlocks.add(index, aScheduleBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleBlockAt(ScheduleBlock aScheduleBlock, int index)
  {
    boolean wasAdded = false;
    if(scheduleBlocks.contains(aScheduleBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduleBlocks()) { index = numberOfScheduleBlocks() - 1; }
      scheduleBlocks.remove(aScheduleBlock);
      scheduleBlocks.add(index, aScheduleBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleBlockAt(aScheduleBlock, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    MuseumSystem existingMuseum = museum;
    museum = null;
    if (existingMuseum != null)
    {
      existingMuseum.delete();
    }
    for(int i=scheduleBlocks.size(); i > 0; i--)
    {
      ScheduleBlock aScheduleBlock = scheduleBlocks.get(i - 1);
      aScheduleBlock.delete();
    }
  }


  @OneToOne(mappedBy = "calendar")
  // line 21 "../../../../..//MuseumSystem.ump"
  public MuseumSystem getMuseumJPA(){
    return getMuseum();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "isMuseumOpen" + ":" + getIsMuseumOpen()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
}