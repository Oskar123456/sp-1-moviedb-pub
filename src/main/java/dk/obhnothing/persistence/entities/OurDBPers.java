package dk.obhnothing.persistence.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class OurDBPers
{
    @Id @GeneratedValue public Integer id;
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
