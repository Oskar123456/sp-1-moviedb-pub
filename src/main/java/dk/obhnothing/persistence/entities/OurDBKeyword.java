package dk.obhnothing.persistence.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * MKeyword
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBKeyword
{
    @Id @GeneratedValue public Integer id;
    public String name;
    /* RELATIONS */
    @JsonIgnore @Exclude @ManyToMany(mappedBy = "keywords") public Set<OurDBMovie> movies;
}
