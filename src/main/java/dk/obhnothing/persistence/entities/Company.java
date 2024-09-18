package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Company
 */
@Entity
@ToString(callSuper = true)
public class Company extends UniId<Company, Integer>
{
    public String logo_path;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;
    @ManyToOne public Country origin_country;
}















