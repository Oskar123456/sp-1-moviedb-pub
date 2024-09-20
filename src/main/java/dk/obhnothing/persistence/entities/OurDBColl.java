package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Collection
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBColl
{
    @Id @GeneratedValue public Integer id;
    public Integer tmdb_id;
    public String name;
    public String poster_path;
    public String backdrop_path;
    /* RELATIONS */
    @Exclude @OneToMany public Set<OurDBMovie> movies;
}
