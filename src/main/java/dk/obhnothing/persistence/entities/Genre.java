package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
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
public class Genre extends UniId<Genre, Integer>
{

    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
