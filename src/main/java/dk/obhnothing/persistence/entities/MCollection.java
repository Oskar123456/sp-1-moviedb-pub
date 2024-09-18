package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Collection
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MCollection extends UniId<MCollection, Integer>
{
    public String name;
    public String poster_path;
    public String backdrop_path;
    /* RELATIONS */
    @OneToMany public Set<Movie> movies;
}
