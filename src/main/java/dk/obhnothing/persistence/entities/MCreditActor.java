package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * MCredit
 */
@Entity
public class MCreditActor
{

    public Integer id;
    public Person person;
    public Movie movie;
    public Integer order;
    public String character;
    /* RELATIONS */
    @ManyToOne public Set<Person> person;

}
