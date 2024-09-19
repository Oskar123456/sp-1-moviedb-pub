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
public class OurDBPers extends UniId<OurDBPers, Integer>
{

    public Boolean adult;
    public Integer gender;
    public String name;
    public String original_name;
    public Double popularity;
    public String profile_path;
    /* RELATIONS */
    @OneToMany public Set<OurDBCrew> crewsin;
    @OneToMany public Set<OurDBCast> actsin;

}
