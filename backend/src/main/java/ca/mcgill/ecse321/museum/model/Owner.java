package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

// line 143 "../../../../..//MuseumSystem.ump"
@Entity
@DiscriminatorValue("Owner")
public class Owner extends Administrator {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Owner() {
    }


}