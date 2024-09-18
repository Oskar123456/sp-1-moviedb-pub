package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Company
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company
{
    public Integer id;
    public String logo_path;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;
    @ManyToOne public Country origin_country;
}
