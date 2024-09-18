package dk.obhnothing.persistence.entities;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Language
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Language
{
    @Id @NaturalId public String iso_639_1;
    public String english_name;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> moviesspoken;
    @OneToMany public Movie moviesorig;
}
