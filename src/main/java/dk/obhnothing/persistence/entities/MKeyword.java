package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MKeyword
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MKeyword extends UniId<MKeyword, Integer>
{

    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;

}
