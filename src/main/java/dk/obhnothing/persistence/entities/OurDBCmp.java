package dk.obhnothing.persistence.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dk.obhnothing.persistence.ExtId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Company
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OurDBCmp extends ExtId<OurDBCmp, Integer>
{
    public String logo_path;
    public String name;
    public String origin_country_iso_3166_1;
    /* RELATIONS */
    @JsonIgnore @Exclude @lombok.EqualsAndHashCode.Exclude @ManyToMany public Set<OurDBMovie> movies;
}















