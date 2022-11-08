package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "RoomType")
// line 148 "../../../../..//MuseumSystem.ump"
public abstract class Room {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Room Attributes
    @Id
    @GeneratedValue
    private long id;
    private String name;

    //Room Associations
    @ManyToMany
    private List<Artwork> artworks;

    public long getId() {
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

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}