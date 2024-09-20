package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Company
 */
@Entity
@ToString
@AllArgsConstructor
public class OurDBCmp
{
    @Id @GeneratedValue public Integer id;
    public Integer tmdb_id;
    public String logo_path;
    public String name;
    public String origin_country_iso_3166_1;
    /* RELATIONS */
    @Exclude @ManyToMany public Set<OurDBMovie> movies;
}















