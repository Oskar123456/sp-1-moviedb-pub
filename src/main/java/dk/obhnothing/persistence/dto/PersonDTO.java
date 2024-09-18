package dk.obhnothing.persistence.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PersonDTO
 */
@EqualsAndHashCode
@ToString
public class PersonDTO
{

    public String adult;
    public String[] also_known_as;
    public String biography;
    public LocalDate birthday;
    public LocalDate deathday;
    public Integer gender;
    public String homepage;
    public Integer id;
    public String imdb_id;
    public String known_for_department;
    public String name;
    public String place_of_birth;
    public Double popularity;
    public String profile_path;

}
