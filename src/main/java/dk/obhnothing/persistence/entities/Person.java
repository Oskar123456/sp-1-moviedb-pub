package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Person
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person
{

    public Integer id;
    public Boolean adult;
    public Integer gender;
    public String name;
    public String original_name;
    public Double popularity;
    public String profile_path;
    /* RELATIONS */
    @OneToMany public Set<MCreditCrew> crewsin;
    @OneToMany public Set<MCreditActor> actsin;

}
