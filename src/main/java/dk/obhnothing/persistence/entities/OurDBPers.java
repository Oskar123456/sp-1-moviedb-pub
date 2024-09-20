package dk.obhnothing.persistence.entities;

import java.time.LocalDate;
import java.util.Set;

import dk.obhnothing.persistence.ExtId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Person
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBPers extends ExtId<OurDBPers, Integer>
{
    @Id @GeneratedValue public Integer id;
    public String adult;
    public String[] also_known_as;
    public String biography;
    public LocalDate birthday;
    public LocalDate deathday;
    public Integer gender;
    public String homepage;
    public String imdb_id;
    public String known_for_department;
    public String name;
    public String place_of_birth;
    public Double popularity;
    public String profile_path;
    /* RELATIONS */
    @Exclude @OneToMany public Set<OurDBCast> acts_in;
    @Exclude @OneToMany public Set<OurDBCrew> crews_in;
}
