package dk.obhnothing.persistence.entities;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Country
{
    @Id @NaturalId public String iso_3166_1;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> moviesprodin;
    @OneToMany public Set<Movie> moviesorigin;
}
