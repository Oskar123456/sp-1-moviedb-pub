package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
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
public class Language extends UniId<Language, String>
{
    public String english_name;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> moviesspoken;
    @OneToMany public Movie moviesorig;
}
