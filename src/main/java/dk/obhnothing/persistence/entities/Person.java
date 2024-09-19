package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Person
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Person extends UniId<Person, Integer>
{

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
