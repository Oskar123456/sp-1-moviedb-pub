package dk.obhnothing.persistence.entities;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
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
    @Id @NaturalId public String name;
    /* RELATIONS */
    @JsonIgnore @Exclude @ManyToMany(mappedBy = "genres") public Set<OurDBMovie> movies;
}
