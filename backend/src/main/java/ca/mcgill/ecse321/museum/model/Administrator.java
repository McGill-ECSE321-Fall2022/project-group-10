/* (C)2022 */
package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Administrator")
public abstract class Administrator extends Person {}
