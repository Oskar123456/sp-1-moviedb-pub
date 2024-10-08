package dk.obhnothing.persistence.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dk.obhnothing.persistence.ExtId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class OurDBColl extends ExtId<OurDBColl, Integer>
{
    public String name;
    public String poster_path;
    public String backdrop_path;
    /* RELATIONS */
    @JsonIgnore @Exclude @lombok.EqualsAndHashCode.Exclude @OneToMany(mappedBy = "collection") public Set<OurDBMovie> movies;
}
