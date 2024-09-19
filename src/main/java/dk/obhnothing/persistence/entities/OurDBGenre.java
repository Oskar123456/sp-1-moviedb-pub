package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Genre
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBGenre
{
    @Id @GeneratedValue public Integer id;
    public String name;
    /* RELATIONS */
    @Exclude @ManyToMany public Set<OurDBMovie> movies;
}
