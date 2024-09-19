package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Genre
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Genre extends UniId<Genre, Integer>
{

    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
