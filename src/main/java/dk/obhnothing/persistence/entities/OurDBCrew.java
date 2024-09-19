package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MCreditCrew
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OurDBCrew
{

    @Id @GeneratedValue public Integer id;
    public String job;
    /* RELATIONS */
    @ManyToOne public OurDBPers person;
    @ManyToOne public OurDBMovie movie;

}
