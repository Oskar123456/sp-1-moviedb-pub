package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

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
