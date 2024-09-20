package dk.obhnothing.persistence.entities;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Language
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBLang
{
    @Id @NaturalId public String iso_639_1;
    public String english_name;
    public String name;
    /* RELATIONS */
    @Exclude @ManyToMany public Set<OurDBMovie> movies_spoken;
    @Exclude @OneToMany public Set<OurDBMovie> movies_orig;
}
