package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * MCredit
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MCreditActor
{

    public Integer id;
    public Integer order;
    public String character;
    /* RELATIONS */
    @ManyToOne public Person person;
    @ManyToOne public Movie movie;

}
