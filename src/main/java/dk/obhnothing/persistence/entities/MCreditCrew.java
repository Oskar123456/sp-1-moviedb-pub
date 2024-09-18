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
    public String job;
    /* RELATIONS */
    @ManyToOne public Person person;
    @ManyToOne public Movie movie;

}
