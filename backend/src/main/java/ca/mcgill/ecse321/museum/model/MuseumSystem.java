package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
// line 4 "../../../../..//MuseumSystem.ump"
public class MuseumSystem {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //MuseumSystem Attributes
    @Id
    @GeneratedValue
    private long id;
    private String name;

    //MuseumSystem Associations
    @OneToOne
    private Calendar calendar;
    @OneToMany
    private List<Person> users;
    @OneToMany
    private List<Donation> donations;
    @OneToMany
    private List<Loan> loans;
    @OneToMany
    private List<Artwork> artworks;
    @OneToMany
    private List<Room> rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}