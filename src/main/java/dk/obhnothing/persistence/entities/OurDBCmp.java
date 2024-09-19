package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

/**
 * Company
 */
@Entity
@ToString(callSuper = true)
public class OurDBCmp extends UniId<OurDBCmp, Integer>
{
    public String logo_path;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<OurDBMovie> movies;
    @ManyToOne public OurDBCountry origin_country;
}















