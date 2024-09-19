package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Country
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OurDBCountry extends UniId<OurDBCountry, String>
{
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<OurDBMovie> moviesprodin;
    @OneToMany public Set<OurDBMovie> moviesorigin;
}
