package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * Country
 */
@Entity
public class Country
{
    public Integer id;
    public String iso_3166_1;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> moviesprodin;
    @OneToMany public Set<Movie> moviesorigin;
}
