package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Collection
 */
@Entity
public class MCollection
{
    public Integer id;
    public String name;
    public String poster_path;
    public String backdrop_path;
    /* RELATIONS */
    @OneToMany public Set<Movie> movies;
}
