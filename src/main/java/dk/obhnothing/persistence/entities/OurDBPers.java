package dk.obhnothing.persistence.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dk.obhnothing.persistence.ExtId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Person
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class OurDBPers extends ExtId<OurDBPers, Integer>
{
    public String adult;
    public String[] also_known_as;
    @Column(columnDefinition="TEXT") public String biography;
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
    @JsonIgnore @lombok.EqualsAndHashCode.Exclude @OneToMany(mappedBy = "person") public Set<OurDBCast> acts_in;
    @JsonIgnore @lombok.EqualsAndHashCode.Exclude @OneToMany(mappedBy = "person") public Set<OurDBCrew> crews_in;
}
