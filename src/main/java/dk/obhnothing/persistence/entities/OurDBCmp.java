package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Company
 */
@Entity
@ToString
public class OurDBCmp
{
    @Id @GeneratedValue public Integer id;
    public String logo_path;
    public String name;
    /* RELATIONS */
    @Exclude @ManyToMany public Set<OurDBMovie> movies;
    @Exclude @ManyToOne public OurDBCountry origin_country;
}















