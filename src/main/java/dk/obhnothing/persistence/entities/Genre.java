package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Genre
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Genre
{

    public Integer id;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
