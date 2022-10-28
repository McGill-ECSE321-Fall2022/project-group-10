package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// line 45 "../../../../..//MuseumSystem.ump"
public abstract class Person {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Person Attributes
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //Person Associations
    @ManyToOne
    private MuseumSystem museum;
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MuseumSystem getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumSystem museum) {
        this.museum = museum;
    }
}