package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * MKeyword
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MKeyword
{

    public Integer id;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
