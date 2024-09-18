package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * MCreditCrew
 */
@Entity
public class MCreditCrew
{

    public Integer id;
    public Person person;
    public Movie movie;
    public String job;
    /* RELATIONS */
    @ManyToOne public Set<Person> person;

}
