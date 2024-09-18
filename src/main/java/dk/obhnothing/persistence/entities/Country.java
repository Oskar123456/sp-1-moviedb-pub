package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Country
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Country extends UniId<Country, String>
{
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> moviesprodin;
    @OneToMany public Set<Movie> moviesorigin;
}
