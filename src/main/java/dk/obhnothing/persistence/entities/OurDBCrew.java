package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * MCreditCrew
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBCrew
{
    @Id @GeneratedValue public Integer id;
    public String job;
    /* RELATIONS */
    @Exclude @ManyToOne public OurDBPers person;
    @Exclude @ManyToOne public OurDBMovie movie;
}
