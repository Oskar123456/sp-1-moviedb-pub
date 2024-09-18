package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

/**
 * MKeyword
 */
@Entity
public class MKeyword
{

    public Integer id;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
