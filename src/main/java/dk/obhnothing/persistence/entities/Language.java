package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

/**
 * Language
 */
@Entity
public class Language
{
    public Integer id;
    public String english_name;
    public String iso_639_1;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<Movie> movies;
}
