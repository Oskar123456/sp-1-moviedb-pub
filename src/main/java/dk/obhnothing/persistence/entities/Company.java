package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

/**
 * Company
 */
@Entity
public class Company
{
    public Integer id;
    public String logo_path;
    public String name;
    public String origin_country;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;
}
