package dk.obhnothing.persistence.entities;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import dk.obhnothing.persistence.ExtId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Country
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBCountry extends ExtId<OurDBCountry, String>
{
    @Id @NaturalId public String iso_3166_1;
    public String name;
    /* RELATIONS */
    @Exclude @ManyToMany public Set<OurDBMovie> moviesprodin;
    @Exclude @OneToMany public Set<OurDBMovie> moviesorigin;
}
